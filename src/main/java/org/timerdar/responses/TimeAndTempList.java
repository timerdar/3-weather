package org.timerdar.responses;

import java.time.LocalDateTime;
import java.util.List;

public class TimeAndTempList {
    public List<String> getTime() {
        return time;
    }

    public List<Double> getTemperature_2m() {
        return temperature_2m;
    }

    private List<String> time;
    private List<Double> temperature_2m;
}
