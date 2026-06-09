package de.tha.prog2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.tha.prog2.model.IWeatherEntry;
import de.tha.prog2.model.IWeatherStation;

public class WeatherStationCtrl {

	@FXML
	private LineChart<String, Number> plot;
	
	@FXML
	private CheckBox minTemp; // <-- NEU

	@FXML
	private CheckBox rain;    // <-- NEU

	@FXML
	private Button myButton;

	@FXML
	private Text status;

	@FXML
	private CheckBox maxTemp;

	@FXML
	private MenuItem loadWeatherStation;

	@FXML
	private MenuItem loadWeatherEntry;

	@FXML
	private ComboBox<String> station;
	
	@FXML
	private Spinner<Integer> measurementSpinner;

	private FileChooser fileChooser = new FileChooser();

	private WeatherStationModel model;

	private boolean dataIsVisible = false;
	

	@FXML
	public void initialize() {
		model = new WeatherStationModel();

		plot.setAnimated(false);
		plot.getXAxis().setLabel("Datum");
		plot.getYAxis().setLabel("Wert");
		
		// 1. ValueFactory erstellen (Min, Max, Standardwert)
	    SpinnerValueFactory<Integer> valueFactory = 
	            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5000, 100);
	    
	    // 2. Dem Spinner die Factory zuweisen
	    measurementSpinner.setValueFactory(valueFactory);

	    // 3. Listener für die ComboBox (Station)
	    station.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (newValue != null && !model.getWeatherEntries().isEmpty()) {
	                updateChart();
	            }
	        }
	    });

	    // 4. Listener für den Spinner
	    measurementSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
	        @Override
	        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
	            // Nur updaten, wenn auch wirklich eine Station ausgewählt ist
	            if (station.getValue() != null) {
	                updateChart();
	            }
	        }
	    });
	    
	    // 5. Listener für die Checkboxen
	    maxTemp.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
	            updateChart();
	        }
	    });

	    minTemp.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
	            updateChart();
	        }
	    });

	    rain.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
	            updateChart();
	        }
	    });
	}


	@FXML
    private void updateChart() {
        plot.getData().clear();
        String selectedStationString = station.getValue();

        if (selectedStationString == null || model.getWeatherEntries().isEmpty() || model.getWeatherStations().isEmpty()) {
            return;
        }

        int targetStationID = -1;
        for (IWeatherStation ws : model.getWeatherStations()) {
            String wsName = ws.getCity() + ", " + ws.getState();
            if (wsName.equals(selectedStationString)) {
                targetStationID = ws.getID();
                break;
            }
        }

        if (targetStationID == -1) {
            return;
        }

        // --- 1. Serien vorbereiten ---
        XYChart.Series<String, Number> seriesMax = new XYChart.Series<>();
        seriesMax.setName("Max. Temp.");

        XYChart.Series<String, Number> seriesMin = new XYChart.Series<>();
        seriesMin.setName("Min. Temp.");

        XYChart.Series<String, Number> seriesRain = new XYChart.Series<>();
        seriesRain.setName("Niederschlag");

        int maxDataPoints = measurementSpinner.getValue();

        // --- 2. Daten befüllen ---
        int count = 0;
        for (IWeatherEntry entry : model.getWeatherEntries()) {
            if (entry.getID() == targetStationID) {
                
                // Wir fügen die Daten nur zur Serie hinzu, wenn die jeweilige Checkbox angehakt ist
                if (maxTemp.isSelected()) {
                    seriesMax.getData().add(new XYChart.Data<>(entry.getDate(), entry.getMaxTemp()));
                }
                
                if (minTemp.isSelected()) {
                    // HINWEIS: Ersetze getMinTemp() durch die Methode aus deinem IWeatherEntry!
                    seriesMin.getData().add(new XYChart.Data<>(entry.getDate(), entry.getMinTemp())); 
                }
                
                if (rain.isSelected()) {
                    // HINWEIS: Ersetze getRain() durch die Methode aus deinem IWeatherEntry!
                    seriesRain.getData().add(new XYChart.Data<>(entry.getDate(), entry.getRain()));
                }
                
                count++;
                if (count >= maxDataPoints) {
                    break; 
                }
            }
        }

        // --- 3. Graphen in das Chart einfügen ---
        // Auch hier: Nur anzeigen, wenn die Checkbox aktiv ist
        if (maxTemp.isSelected()) {
            plot.getData().add(seriesMax);
        }
        if (minTemp.isSelected()) {
            plot.getData().add(seriesMin);
        }
        if (rain.isSelected()) {
            plot.getData().add(seriesRain);
        }

        // 5. Daten einzeichnen
        dataIsVisible = true;
    }
	

	private void clearChart() {
		plot.getData().clear();
		myButton.setText("Daten anzeigen");
	}

	@FXML
	private void checkMaxTemp() {
		if (maxTemp.isSelected()) {
			status.setText("Checkbox is selected");
		} else {
			status.setText("Bereit");
		}
	}

	@FXML
	private void loadWeatherStation() {
		Stage stage = (Stage) plot.getScene().getWindow();
		fileChooser.setTitle("Wetterstation laden");
		File selectedFile = fileChooser.showOpenDialog(stage);
		try {
			InputStream in = new FileInputStream(selectedFile);
			List<IWeatherStation> list = WeatherStationModel.readWeatherStations(in);
			in.close();

			List<String> stationStrings = list.stream().map(s -> s.getCity() + ", " + s.getState()).distinct().sorted()
					.toList();

			station.setItems(FXCollections.observableArrayList(stationStrings));
			model.setWeatherStations(list); 
			status.setText(list.size() + " Stationen geladen");
			station.setPromptText("Wähle eine Station aus");
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void loadWeatherEntry() {
		Stage stage = (Stage) plot.getScene().getWindow();
		fileChooser.setTitle("Wetterdaten laden");
		File selectedFile = fileChooser.showOpenDialog(stage);
		try {
			InputStream in = new FileInputStream(selectedFile);
			List<IWeatherEntry> list = WeatherStationModel.readWeatherEntries(in);
			in.close();
			
			model.setWeatherEntries(list);
			status.setText(list.size() + " Daten geladen");
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}