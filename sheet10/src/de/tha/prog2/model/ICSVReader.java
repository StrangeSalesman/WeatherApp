package de.tha.prog2.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Liest Wetterstationen und Wetterdaten aus Eingabeströmen ein.
 *
 * Die Implementierung soll einfache CSV-Daten verarbeiten. Die einzelnen Felder
 * sind durch Semikolon getrennt. Die erste Zeile enthält jeweils die
 * Spaltenüberschriften und soll nicht als Datensatz interpretiert werden.
 */
public interface ICSVReader {

    /**
     * Liest Wetterdaten aus einem gzip-komprimierten Eingabestrom ein.
     *
     * Der Eingabestrom enthält CSV-Daten mit folgendem Aufbau:
     *
     * <pre>
     * STATIONS_ID;MESS_DATUM;NIEDERSCHLAG;TAGES_MAX_TEMPERATUR;TAGES_MIN_TEMPERATUR
     * </pre>
     *
     * Die Methode soll den Eingabestrom mit {@code GZIPInputStream} entpacken,
     * die enthaltenen Zeilen parsen und für jede gültige Datenzeile ein
     * {@link IWeatherEntry}-Objekt erzeugen.
     *
     * @param in Eingabestrom der gzip-komprimierten Wetterdatendatei
     * @return Liste der eingelesenen Wetterdaten
     * @throws IOException wenn der Eingabestrom nicht gelesen oder entpackt werden kann
     */
    public List<IWeatherEntry> readWeatherEntries(InputStream in) throws IOException;

    /**
     * Liest Wetterstationen aus einem CSV-Eingabestrom ein.
     *
     * Der Eingabestrom enthält CSV-Daten mit folgendem Aufbau:
     *
     * <pre>
     * StationsID;Ort;Bundesland
     * </pre>
     *
     * Die Methode soll die enthaltenen Zeilen parsen und für jede gültige
     * Datenzeile ein {@link IWeatherStation}-Objekt erzeugen.
     *
     * @param in Eingabestrom der CSV-Datei mit Wetterstationen
     * @return Liste der eingelesenen Wetterstationen
     * @throws IOException wenn der Eingabestrom nicht gelesen werden kann
     */
    public List<IWeatherStation> readWeatherStations(InputStream in) throws IOException;
}