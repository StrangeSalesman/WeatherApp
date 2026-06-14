package de.tha.prog2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
import java.util.ArrayList;
import java.util.List;

import de.tha.prog2.model.IWeatherEntry;
import de.tha.prog2.model.IWeatherStation;

public class WeatherStationCtrl {

	@FXML
	private LineChart<String, Number> plot;

	@FXML
	private CheckBox minTemp, rain, maxTemp;

	@FXML
	private Text status;

	@FXML
	private MenuItem loadWeatherStation, loadWeatherEntry;

	@FXML
	private ComboBox<String> station;

	@FXML
	private Spinner<Integer> measurementSpinner;

	private FileChooser fileChooser = new FileChooser();

	private WeatherStationModel model;

	private boolean dataIsVisible = false;

	public void initialize() {
		model = new WeatherStationModel();

		plot.setAnimated(false);
		plot.getXAxis().setLabel("Datum");
		plot.getYAxis().setLabel("Wert");

		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 100);
		measurementSpinner.setValueFactory(valueFactory);
		measurementSpinner.setEditable(true);

		station.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !model.getWeatherEntries().isEmpty()) {
					updateChart();
				}
			}
		});

		measurementSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if (station.getValue() != null) {
					updateChart();
				}
			}
		});

		ChangeListener<Boolean> checkBoxListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
				updateChart();
			}
		};

		maxTemp.selectedProperty().addListener(checkBoxListener);
		minTemp.selectedProperty().addListener(checkBoxListener);
		rain.selectedProperty().addListener(checkBoxListener);
	}

	@FXML
	private void updateChart() {
		plot.getData().clear();
		String selectedStationString = station.getValue();

		if (selectedStationString == null || model.getWeatherEntries().isEmpty()
				|| model.getWeatherStations().isEmpty()) {
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

		XYChart.Series<String, Number> seriesMax = new XYChart.Series<>();
		seriesMax.setName("Max. Temp.");

		XYChart.Series<String, Number> seriesMin = new XYChart.Series<>();
		seriesMin.setName("Min. Temp.");

		XYChart.Series<String, Number> seriesRain = new XYChart.Series<>();
		seriesRain.setName("Niederschlag");

		int maxDataPoints = measurementSpinner.getValue();

		List<IWeatherEntry> stationEntries = new ArrayList<>();
		for (IWeatherEntry entry : model.getWeatherEntries()) {
			if (entry.getID() == targetStationID) {
				stationEntries.add(entry);
			}
		}

		int startIndex = Math.max(0, stationEntries.size() - maxDataPoints);
		int count = 0;

		for (int i = startIndex; i < stationEntries.size(); i++) {
			IWeatherEntry entry = stationEntries.get(i);

			String prettyDate = formatDate(entry.getDate());

			if (maxTemp.isSelected()) {
				seriesMax.getData().add(new XYChart.Data<>(prettyDate, entry.getMaxTemp()));
			}
			if (minTemp.isSelected()) {
				seriesMin.getData().add(new XYChart.Data<>(prettyDate, entry.getMinTemp()));
			}
			if (rain.isSelected()) {
				seriesRain.getData().add(new XYChart.Data<>(prettyDate, entry.getRain()));
			}
			count++;
		}

		if (maxTemp.isSelected()) {
			plot.getData().add(seriesMax);
			seriesMax.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #e74c3c;");
		}
		if (minTemp.isSelected()) {
			plot.getData().add(seriesMin);
			seriesMin.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #3498db;");
		}
		if (rain.isSelected()) {
			plot.getData().add(seriesRain);
			seriesRain.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #7f8c8d;");
		}

		dataIsVisible = true;
		plot.setTitle("Wetterdaten für " + selectedStationString);
		status.setText(count + " Messwerte angezeigt.");
	}

	@FXML
	private void loadWeatherStation() {
		Stage stage = (Stage) plot.getScene().getWindow();
		fileChooser.setTitle("Wetterstation laden");
		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile == null) {
			return;
		}

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

		if (selectedFile == null) {
			return;
		}

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

	private String formatDate(String rawDate) {
		if (rawDate != null && rawDate.length() == 8) {
			return rawDate.substring(6, 8) + "." + rawDate.substring(4, 6) + "." + rawDate.substring(0, 4);
		}
		return rawDate;
	}
}