package org.timerdar.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.timerdar.ChartMaker;
import org.timerdar.integrations.OpenMeteoApi;
import org.timerdar.redis.RedisCache;
import org.timerdar.responses.TimeAndTempList;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class WeatherHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getRawQuery();
        String city = parseCity(query);

        try{
            RedisCache cache = new RedisCache();
            TimeAndTempList temps = cache.getTemperatures(city);
            if(temps == null){
                System.out.println("Пустой кэш");
                OpenMeteoApi api = new OpenMeteoApi();
                temps = api.getTemperatureOfNextDay(city);
                cache.setTemperatures(city, temps);
            }

            System.out.println("Выводим график температуры для города " + city);

            byte[] png = new ChartMaker().createChart(city, temps);
            exchange.getResponseHeaders().add("Content-Type", "image/png");
            exchange.sendResponseHeaders(200, png.length);

            exchange.getResponseBody().write(png);
        }catch (Exception e){
            String json = "{\"error\" : \"" + e.getMessage() + "\"}";
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(404, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
        }

    }

    private String parseCity(String query){
        String city = query.substring(query.indexOf("=") + 1);
        if (city.isEmpty()){
            throw new IllegalArgumentException("City must not be empty");
        }
        return city;
    }
}
