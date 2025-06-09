package org.timerdar.responses;

import java.util.Map;

public class TemperaturesResponse {
    public TimeAndTempList getHourly() {
        return hourly;
    }

    private TimeAndTempList hourly;

}
