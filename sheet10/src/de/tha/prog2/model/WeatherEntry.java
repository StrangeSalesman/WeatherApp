package de.tha.prog2.model;

import java.util.Objects;

public class WeatherEntry implements IWeatherEntry {
	
	private int stationID;
	private String date;
	private double rain;
	private double maxTemp;
	private double minTemp;
	
	
	public WeatherEntry(int stationID, String date, double rain, double maxTemp, double minTemp) {
		this.stationID = stationID;
		this.date = date;
		this.rain = rain;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
	}

	@Override
	public int getID() {
		return this.stationID;
	}

	@Override
	public String getDate() {
		return this.date;
	}

	@Override
	public double getRain() {
		return this.rain;
	}

	@Override
	public double getMaxTemp() {
		return this.maxTemp;
	}

	@Override
	public double getMinTemp() {
		return this.minTemp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, stationID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeatherEntry other = (WeatherEntry) obj;
		return Objects.equals(date, other.date) && stationID == other.stationID;
	}

	@Override
	public String toString() {
		return "WeatherEntry [stationID=" + stationID + ", date=" + date + ", rain=" + rain + ", maxTemp=" + maxTemp
				+ ", minTemp=" + minTemp + "]";
	}
	
	

}
