package org.timerdar;

import com.sun.net.httpserver.HttpServer;
import org.timerdar.handlers.WeatherHandler;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/weather", new WeatherHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Сервер запущен");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}