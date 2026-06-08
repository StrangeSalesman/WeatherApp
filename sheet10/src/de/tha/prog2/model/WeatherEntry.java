package de.tha.prog2.model;

import java.util.Objects;

public class WeatherEntry implements IWeatherEntry {
	private int stationID;
	private String date;
	private int rain;
	private int dailyMaxTemp;
	private int dailyMinTemp;
	
	@Override
	public int getID() {
		return stationID;
	}

	@Override
	public String getDate() {
		return date;
	}

	public int getStationID() {
		return stationID;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	public int getDailyMaxTemp() {
		return dailyMaxTemp;
	}

	public void setDailyMaxTemp(int dailyMaxTemp) {
		this.dailyMaxTemp = dailyMaxTemp;
	}

	public int getDailyMinTemp() {
		return dailyMinTemp;
	}

	public void setDailyMinTemp(int dailyMinTemp) {
		this.dailyMinTemp = dailyMinTemp;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setRain(int rain) {
		this.rain = rain;
	}

	@Override
	public String toString() {
		return "WeatherEntry [stationID=" + stationID + ", date=" + date + ", rain=" + rain + ", dailyMaxTemp="
				+ dailyMaxTemp + ", dailyMinTemp=" + dailyMinTemp + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dailyMaxTemp, dailyMinTemp, date, rain, stationID);
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
		return dailyMaxTemp == other.dailyMaxTemp && dailyMinTemp == other.dailyMinTemp
				&& Objects.equals(date, other.date) && rain == other.rain && stationID == other.stationID;
	}

	@Override
	public double getRain() {
		return rain;
	}

	@Override
	public double getMaxTemp() {
		return dailyMaxTemp;
	}

	@Override
	public double getMinTemp() {
		return dailyMinTemp;
	}

}
