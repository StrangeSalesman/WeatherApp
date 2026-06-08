package de.tha.prog2.model;

/**
 * Repräsentiert eine Wetterstation.
 *
 * Eine Wetterstation besitzt eine eindeutige ID, einen Ort und ein Bundesland.
 * Die ID wird in den Wetterdaten verwendet, um Messwerte einer Station
 * zuzuordnen.
 */
public interface IWeatherStation {

    /**
     * Gibt die eindeutige ID der Wetterstation zurück.
     *
     * Diese ID wird in {@link IWeatherEntry} verwendet, um Wetterdaten
     * einer Station zuzuordnen.
     *
     * @return eindeutige Stations-ID
     */
    int getID();

    /**
     * Gibt den Ort der Wetterstation zurück.
     *
     * Der Ort entspricht der nächstgelegenen Stadt, Gemeinde oder Ortschaft,
     * in der sich die Wetterstation befindet.
     *
     * @return Ortsname der Wetterstation
     */
    String getCity();

    /**
     * Gibt das Bundesland zurück, in dem sich die Wetterstation befindet.
     *
     * @return Name des Bundeslands
     */
    String getState();

}