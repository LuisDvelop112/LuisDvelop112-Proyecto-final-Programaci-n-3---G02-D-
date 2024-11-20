package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class VistaGenerarGraficos {

    @FXML
    private BarChart<String, Number> barChartGastos;

    @FXML
    private CategoryAxis xAxisGastos;

    @FXML
    private NumberAxis yAxisGastos;

    @FXML
    private PieChart pieChartUsuarios;

    @FXML
    private LineChart<String, Number> lineChartSaldo;

    @FXML
    private CategoryAxis xAxisSaldo;

    @FXML
    private NumberAxis yAxisSaldo;

    @FXML
    public void initialize() {
        configurarBarChartGastos();
        configurarPieChartUsuarios();
        configurarLineChartSaldo();
    }

    private void configurarBarChartGastos() {
        // Datos quemados para el BarChart de gastos más comunes
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Gastos");
        series.getData().add(new XYChart.Data<>("Comida", 450));
        series.getData().add(new XYChart.Data<>("Transporte", 300));
        series.getData().add(new XYChart.Data<>("Ropa", 150));
        series.getData().add(new XYChart.Data<>("Entretenimiento", 200));
        barChartGastos.getData().add(series);

        // Configuración adicional (opcional)
        xAxisGastos.setLabel("Categorías");
        yAxisGastos.setLabel("Monto en $");
    }

    private void configurarPieChartUsuarios() {
        // Datos quemados para el PieChart de usuarios con más transacciones
        pieChartUsuarios.getData().add(new PieChart.Data("Usuario 1", 35));
        pieChartUsuarios.getData().add(new PieChart.Data("Usuario 2", 25));
        pieChartUsuarios.getData().add(new PieChart.Data("Usuario 3", 20));
        pieChartUsuarios.getData().add(new PieChart.Data("Usuario 4", 15));
        pieChartUsuarios.getData().add(new PieChart.Data("Usuario 5", 5));
    }

    private void configurarLineChartSaldo() {
        // Datos quemados para el LineChart del saldo promedio
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Saldo Promedio");

        series.getData().add(new XYChart.Data<>("Usuario 1", 500));
        series.getData().add(new XYChart.Data<>("Usuario 2", 450));
        series.getData().add(new XYChart.Data<>("Usuario 3", 600));
        series.getData().add(new XYChart.Data<>("Usuario 4", 300));
        series.getData().add(new XYChart.Data<>("Usuario 5", 700));

        lineChartSaldo.getData().add(series);

        // Configuración adicional (opcional)
        xAxisSaldo.setLabel("Usuarios");
        yAxisSaldo.setLabel("Saldo en $");
    }
}
