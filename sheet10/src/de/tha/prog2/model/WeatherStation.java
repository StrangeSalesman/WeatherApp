package de.tha.prog2.model;

import java.util.Objects;

public class WeatherStation implements IWeatherStation{
	private int stationID;
	private String city;
	private String state;
	
	@Override
	public int getID() {
		return stationID;
	}

	@Override
	public String toString() {
		return "WeatherStation [stationID=" + stationID + ", city=" + city + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, state, stationID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeatherStation other = (WeatherStation) obj;
		return Objects.equals(city, other.city) && Objects.equals(state, other.state) && stationID == other.stationID;
	}

	@Override
	public String getCity() {
		return city;
	}

	public int getStationID() {
		return stationID;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getState() {
		return state;
	}

}
