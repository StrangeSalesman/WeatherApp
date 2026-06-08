package de.tha.prog2;

import java.util.ArrayList;
import java.util.List;

public class WeatherStationModel {

    public List<DemoWeatherEntry> getDemoData() {
        List<DemoWeatherEntry> data = new ArrayList<>();

        data.add(new DemoWeatherEntry("20240101", 6.2, -1.0));
        data.add(new DemoWeatherEntry("20240102", 7.5, 0.5));
        data.add(new DemoWeatherEntry("20240103", 5.1, -2.2));
        data.add(new DemoWeatherEntry("20240104", 8.0, 1.3));
        data.add(new DemoWeatherEntry("20240105", 4.8, -3.0));

        return data;
    }

    public static class DemoWeatherEntry {

        private final String date;
        private final double maxTemperature;
        private final double minTemperature;

        public DemoWeatherEntry(String date, double maxTemperature, double minTemperature) {
            this.date = date;
            this.maxTemperature = maxTemperature;
            this.minTemperature = minTemperature;
        }

        public String getDate() {
            return date;
        }

        public double getMaxTemperature() {
            return maxTemperature;
        }

        public double getMinTemperature() {
            return minTemperature;
        }
    }
}