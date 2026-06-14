package de.tha.prog2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import de.tha.prog2.model.*;

public class WeatherStationModel {

    private List<IWeatherStation> weatherStations = new ArrayList<>();
    private List<IWeatherEntry> weatherEntries = new ArrayList<>();

    public List<IWeatherStation> getWeatherStations() {
        return weatherStations;
    }

    public void setWeatherStations(List<IWeatherStation> weatherStations) {
        this.weatherStations = weatherStations;
    }

    public List<IWeatherEntry> getWeatherEntries() {
        return weatherEntries;
    }

    public void setWeatherEntries(List<IWeatherEntry> weatherEntries) {
        this.weatherEntries = weatherEntries;
    }
    
    public static List<IWeatherEntry> readWeatherEntries(InputStream in) throws IOException {
        InputStream gin = new GZIPInputStream(in);
        List<IWeatherEntry> list = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(gin);
        BufferedReader reader = new BufferedReader(isr);

        String line = reader.readLine();
        
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(";");

            WeatherEntry ws = new WeatherEntry(
            	    Integer.parseInt(fields[0]),
            	    fields[1],                  
            	    Double.parseDouble(fields[2]),
            	    Double.parseDouble(fields[3]),
            	    Double.parseDouble(fields[4]) 
            	);
            list.add(ws);
        }

        return list;
    }

    public static List<IWeatherStation> readWeatherStations(InputStream in) throws IOException {
        List<IWeatherStation> list = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(isr);

        String line = reader.readLine();
        
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(";");

            WeatherStation ws = new WeatherStation(
                Integer.parseInt(fields[0]),
                fields[1],
                fields[2]
            );
            list.add(ws);
        }

        return list;
    }
    
    public List<IWeatherEntry> readWeatherEntriesForStation(InputStream in, int stationID, int max) throws IOException {
        if (in == null) return new ArrayList<>();
        
        InputStream gin = new GZIPInputStream(in);
        List<IWeatherEntry> list = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(gin);
        BufferedReader reader = new BufferedReader(isr);

        String line = reader.readLine();

        int count = 0;
        while ((line = reader.readLine()) != null && count != max) {
            String[] fields = line.split(";");

            if(Integer.parseInt(fields[0]) == stationID) {
                WeatherEntry ws = new WeatherEntry(
                    Integer.parseInt(fields[0]),
                    fields[1],
                    (int) Math.round(Double.parseDouble(fields[2])),
                    (int) Math.round(Double.parseDouble(fields[3])),
                    (int) Math.round(Double.parseDouble(fields[4]))
                );
                count++;
                list.add(ws);
            }
        }

        return list;
    } 

}