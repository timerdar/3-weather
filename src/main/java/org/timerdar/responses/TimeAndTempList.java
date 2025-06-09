package org.timerdar.responses;

import java.time.LocalDateTime;
import java.util.List;

public class TimeAndTempList {
    public TimeAndTempList(List<String> time, List<Double> temperature_2m) {
        this.time = time;
        this.temperature_2m = temperature_2m;
    }

    public List<String> getTime() {
        return time;
    }

    public List<Double> getTemperature_2m() {
        return temperature_2m;
    }

    private List<String> time;
    private List<Double> temperature_2m;
}
