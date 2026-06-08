package de.tha.prog2.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class CSVReader implements ICSVReader {
	public static void main(String[] args) {
		try {
			InputStream in = new FileInputStream("stations.txt");
			CSVReader reader = new CSVReader();
			List<IWeatherStation> list = reader.readWeatherStations(in);
			in.close();
			
//			for (IWeatherStation iWeatherStation : list) {
//				System.out.println(iWeatherStation.toString());
//			}
			
			InputStream in2 = new FileInputStream("wetterdaten-klein.gz");
			List<IWeatherEntry> list2 = reader.readWeatherEntries(in2);
			in2.close();

			
//			for (IWeatherEntry iWeatherEntry : list2) {
//				System.out.println(iWeatherEntry.toString());
//			}

			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
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

	@Override
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

}
