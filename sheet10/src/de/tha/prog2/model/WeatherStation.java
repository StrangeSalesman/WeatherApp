package de.tha.prog2.model;


import java.util.Objects;

public class WeatherStation implements IWeatherStation {
	
	private int id;
	private String city;
	private String state;
	
	public WeatherStation(int id, String city, String state) {
		this.id = id;
		this.city = city;
		this.state = state;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String getCity() {
		return this.city;
	}

	@Override
	public String getState() {
		return this.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return id == other.id;
	}

	@Override
	public String toString() {
		return "WeatherStation [id=" + id + ", city=" + city + ", state=" + state + "]";
	}
	
	

}

