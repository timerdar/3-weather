package org.timerdar.integrations;

import com.google.gson.Gson;
import org.timerdar.responses.Coordinates;
import org.timerdar.responses.CoordinatesResponse;
import org.timerdar.responses.TemperaturesResponse;
import org.timerdar.responses.TimeAndTempList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

public class OpenMeteoApi {

    public Coordinates getCityCoordinates(String city) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        URI uri = URI.create("https://geocoding-api.open-meteo.com/v1/search?name=" + city);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        CoordinatesResponse coordinatesResponse = gson.fromJson(response.body(), CoordinatesResponse.class);
        if (coordinatesResponse.getResults().length == 0) {
            throw new IllegalArgumentException("City don`t exists");
        } else {
            System.out.println("Получили из API координаты города " + city);
            return coordinatesResponse.getResults()[0];
        }
    }

    public TimeAndTempList getTemperatureOfNextDay(String city) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Coordinates coordinates = getCityCoordinates(city);

        URI uri = URI.create("https://api.open-meteo.com/v1/forecast?latitude=" + coordinates.getLatitude() + "&longitude=" + coordinates.getLongitude() + "&hourly=temperature_2m");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        TemperaturesResponse temperaturesResponse = gson.fromJson(response.body(), TemperaturesResponse.class);
        int offset = 0;
        for (String time: temperaturesResponse.getHourly().getTime()){
            if (LocalDateTime.now().isAfter(LocalDateTime.parse(time))){
                offset++;
            }else{
                break;
            }
        }
        List<String> newDates = temperaturesResponse.getHourly().getTime().subList(offset, offset + 24);
        List<Double> newTemps = temperaturesResponse.getHourly().getTemperature_2m().subList(offset, offset + 24);
        System.out.println("Получили новые данные о температуре из API");
        return new TimeAndTempList(newDates, newTemps);
    }

}
