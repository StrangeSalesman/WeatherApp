package de.tha.prog2.model;

import java.util.List;
import java.util.Map;

public interface IWeatherAnalyzer {
	/**
	 * Searches for the highest temperature in the weather data and returns 
	 * the temperature.
	 * 
	 * @return highest overall temperature seen in the data set
	 */
	public double getHighestTemperature();
		
	/**
	 * The highest temperature has been observed by multiple weather stations.
	 * Return a list of all weatherstations that have observed this highest
	 * temperature at one point in time.
	 * @return
	 */
	public List<IWeatherStation> getStationsWithHighestTemperature();
		
	/**
	 * Returns a map of the number of weather stations per state in ascending 
	 * number of weather stations, i.e. the state with the fewest weather stations
	 * should be the first entry in the map. The map is therefore sorted by the
	 * value field. 
	 * 
	 * Hint: 
	 *   - The simplest way to achieve the ordering is to select a Map
	 *     that preserves the order of insertion and to insert the elements
	 *     in the correct order
	 *   - Your approach probably needs three steps:
	 *   	  1.) Generate the mapping from states to number of weather stations in a simple map
	 *        2.) Sort map into list
	 *        3.) Collect sorted list into a order-preserving Map implementation
	 * @return Map that contains state names as keys, and number of weather stations
	 *         values, e.g. 
	 *             ("Bremen", 5),
	 *             ("Hamburg", 12),
	 *             ...
	 */
	public Map<String, Long> getStationsPerState();
}
