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
    
	public List<IWeatherEntry> readWeatherEntries(InputStream in) throws IOException {
		InputStream gin = new GZIPInputStream(in);
		List<IWeatherEntry> list = new ArrayList<>();
		InputStreamReader isr = new InputStreamReader(gin);
		BufferedReader reader = new BufferedReader(isr);

		// skip first line
		String line = reader.readLine();

		
		while ((line = reader.readLine()) != null) {
			
			String[] fields = line.split(";");

			WeatherEntry ws = new WeatherEntry();
			ws.setStationID(Integer.parseInt(fields[0]));
			ws.setDate(fields[1]);
			ws.setRain((int) Math.round(Double.parseDouble(fields[2])));
			ws.setDailyMaxTemp((int) Math.round(Double.parseDouble(fields[3])));
			ws.setDailyMinTemp((int) Math.round(Double.parseDouble(fields[4])));


			list.add(ws);
		}

		return list;
	}

	
	public List<IWeatherStation> readWeatherStations(InputStream in) throws IOException {
			List<IWeatherStation> list = new ArrayList<>();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(isr);

			// skip first line
			String line = reader.readLine();

			
			while ((line = reader.readLine()) != null) {
				
				String[] fields = line.split(";");

				WeatherStation ws = new WeatherStation();
				ws.setStationID(Integer.parseInt(fields[0]));
				ws.setCity(fields[1]);
				ws.setState(fields[2]);

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

		// skip first line
		String line = reader.readLine();

		int count = 0;
		while ((line = reader.readLine()) != null && count != max) {
			
			String[] fields = line.split(";");

			WeatherEntry ws = new WeatherEntry();
			if(Integer.parseInt(fields[0]) == stationID) {
				ws.setStationID(Integer.parseInt(fields[0]));
				ws.setDate(fields[1]);
				ws.setRain((int) Math.round(Double.parseDouble(fields[2])));
				ws.setDailyMaxTemp((int) Math.round(Double.parseDouble(fields[3])));
				ws.setDailyMinTemp((int) Math.round(Double.parseDouble(fields[4])));
				count++;
				list.add(ws);

			}
			
		}

		return list;
	} 

}