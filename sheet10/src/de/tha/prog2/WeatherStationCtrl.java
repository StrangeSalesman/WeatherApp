package de.tha.prog2;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


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
    private ChoiceBox<String> station;

    
    private FileChooser fileChooser = new FileChooser();  
    
    
    private WeatherStationModel model;
    

    private boolean dataIsVisible = false;

    @FXML
    public void initialize() {
        model = new WeatherStationModel();

        plot.setAnimated(false);
        plot.getXAxis().setLabel("Datum");
        plot.getYAxis().setLabel("Wert");
        
        status.setText("Test");
    }
        
        
    
    @FXML
//    public void onButtonClicked() {
//        if (dataIsVisible) {
//            clearChart();
//        } else {
//            drawChart();
//        }
//
//        dataIsVisible = !dataIsVisible;
//    }

//    private void drawChart() {
//    	
//        XYChart.Series<String, Number> maxTemperature = new XYChart.Series<>();
//        maxTemperature.setName("Tageshöchsttemperatur");
//
//        for (WeatherStationModel.DemoWeatherEntry entry : model.getDemoData()) {
//            maxTemperature.getData().add(
//                    new XYChart.Data<>(entry.getDate(), entry.getMaxTemperature())
//            );
//        }
//
//        XYChart.Series<String, Number> minTemperature = new XYChart.Series<>();
//        minTemperature.setName("Tagestiefsttemperatur");
//
//        for (WeatherStationModel.DemoWeatherEntry entry : model.getDemoData()) {
//            minTemperature.getData().add(
//                    new XYChart.Data<>(entry.getDate(), entry.getMinTemperature())
//            );
//        }
//
//        plot.setData(FXCollections.observableArrayList(maxTemperature, minTemperature));
//        myButton.setText("Daten entfernen");
//    }

    private void clearChart() {
        plot.getData().clear();
        myButton.setText("Daten anzeigen");
    }
    
    @FXML
    private void checkMaxTemp() {
    	if (maxTemp.isSelected()) {
			status.setText("Checkbox is selected");
		}
    	else {
			status.setText("Bereit");
		}
    }
    
    @FXML
    private void loadWeatherStation () {
    	Stage stage = (Stage) plot.getScene().getWindow();
    	fileChooser.setTitle("Wetterstation laden");
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
			InputStream in = new FileInputStream(selectedFile);
			List<IWeatherStation> list = WeatherStationModel.readWeatherStations(in);
			in.close();
			
			List<String> stationStrings = list.stream()
				    .map(s -> s.getCity() + ", " + s.getState()) // Kombiniere Stadt und Land
				    .distinct()                                  
				    .sorted()                                    // Optional: Alphabetisch sortieren
				    .toList();
			
			station.setItems(FXCollections.observableArrayList(stationStrings));
			status.setText(list.size() + " Stationen geladen");
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			e.printStackTrace();
		}       
        
    }
    
    @FXML
    private void loadWeatherEntry () {
    	Stage stage = (Stage) plot.getScene().getWindow();
    	fileChooser.setTitle("Wetterdaten laden");
    	File selectedFile = fileChooser.showOpenDialog(stage);
        try {
			InputStream in = new FileInputStream(selectedFile);
			List<IWeatherEntry> list = WeatherStationModel.readWeatherEntries(in);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			e.printStackTrace();
		}       
    }
    
}