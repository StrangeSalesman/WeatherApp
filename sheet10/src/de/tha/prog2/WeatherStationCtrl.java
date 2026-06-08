package de.tha.prog2;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.util.List;

public class WeatherStationCtrl {

    @FXML
    private LineChart<String, Number> plot;

    @FXML
    private Button myButton;

    private WeatherStationModel model;

    private boolean dataIsVisible = false;

    @FXML
    public void initialize() {
        model = new WeatherStationModel();

        plot.setAnimated(false);
        plot.setTitle("LineChart-Demo");
        plot.getXAxis().setLabel("Datum");
        plot.getYAxis().setLabel("Wert");
    }

    @FXML
    public void onButtonClicked() {
        if (dataIsVisible) {
            clearChart();
        } else {
            drawChart();
        }

        dataIsVisible = !dataIsVisible;
    }

    private void drawChart() {
    	
        XYChart.Series<String, Number> maxTemperature = new XYChart.Series<>();
        maxTemperature.setName("Tageshöchsttemperatur");

        for (WeatherStationModel.DemoWeatherEntry entry : model.getDemoData()) {
            maxTemperature.getData().add(
                    new XYChart.Data<>(entry.getDate(), entry.getMaxTemperature())
            );
        }

        XYChart.Series<String, Number> minTemperature = new XYChart.Series<>();
        minTemperature.setName("Tagestiefsttemperatur");

        for (WeatherStationModel.DemoWeatherEntry entry : model.getDemoData()) {
            minTemperature.getData().add(
                    new XYChart.Data<>(entry.getDate(), entry.getMinTemperature())
            );
        }

        plot.setData(FXCollections.observableArrayList(maxTemperature, minTemperature));
        myButton.setText("Daten entfernen");
    }

    private void clearChart() {
        plot.getData().clear();
        myButton.setText("Daten anzeigen");
    }
}