package org.timerdar.responses;

import java.time.LocalDateTime;
import java.util.Date;

public class TimeAndTempList {
    public LocalDateTime[] getTime() {
        return time;
    }

    public double[] getTemperature_2m() {
        return temperature_2m;
    }

    private LocalDateTime[] time;
    private double[] temperature_2m;
}
