package de.tha.prog2.model;

/**
 * Repräsentiert einen einzelnen Wetterdatensatz einer Wetterstation.
 *
 * Ein Wetterdatensatz enthält die Messwerte einer Station für einen bestimmten Tag.
 * Die Stations-ID verweist auf eine Wetterstation aus den eingelesenen Stationsdaten.
 */
public interface IWeatherEntry {

    /**
     * Gibt die eindeutige ID der Wetterstation zurück, zu der dieser Messwert gehört.
     *
     * @return Stations-ID
     */
    int getID();

    /**
     * Gibt das Messdatum zurück.
     *
     * Das Datum ist als Zeichenkette im Format {@code YYYYMMDD} gespeichert,
     * wie es auch in den ursprünglichen Wetterdaten vorkommt.
     *
     * @return Messdatum im Format {@code YYYYMMDD}
     */
    String getDate();

    /**
     * Gibt die Niederschlagsmenge zurück.
     *
     * @return Niederschlag
     */
    double getRain();

    /**
     * Gibt die Tageshöchsttemperatur zurück.
     *
     * @return Tageshöchsttemperatur
     */
    double getMaxTemp();

    /**
     * Gibt die Tagestiefsttemperatur zurück.
     *
     * @return Tagestiefsttemperatur
     */
    double getMinTemp();

}