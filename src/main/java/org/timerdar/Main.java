package org.timerdar;

import com.sun.net.httpserver.HttpServer;
import org.timerdar.handlers.WeatherHandler;
import org.timerdar.integrations.OpenMeteoApi;
import org.timerdar.responses.Coordinates;

import java.net.InetSocketAddress;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/weather", new WeatherHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Сервер запущен");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}