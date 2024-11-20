package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.luisdeveloper.billeteravirtualuq.controller.TransaccionController;
import com.luisdeveloper.billeteravirtualuq.model.Transaccion;

public class VistaReportesController {

    TransaccionController transaccionController = new TransaccionController();

    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Declaración de los controles FXML
    @FXML
    private ComboBox<String> reportTypeComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button generateReportButton;

    @FXML
    private ComboBox<String> secondReportTypeComboBox;

    // Método de inicialización del controlador
    @FXML
    private void initialize() {
        // Llenamos el ComboBox de tipos de reportes
        reportTypeComboBox.getItems().addAll("Ingresos", "Gastos", "Saldos");

        // Llenamos el segundo ComboBox de tipo de reporte adicional (PDF o Excel)
        secondReportTypeComboBox.getItems().addAll("PDF", "Excell");

        // Acción para el botón "Generar Reporte"
        generateReportButton.setOnAction(event -> generateReport());
    }

    private void generateReport() {
        List<Transaccion> transacciones = transaccionController.listarTransacciones(idUsuario);
        // Obtener los valores seleccionados por el usuario
        String reportType = reportTypeComboBox.getValue();
        String secondReportType = secondReportTypeComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // Verificar si todos los campos están completos
        if (reportType != null && secondReportType != null && startDate != null && endDate != null) {
            // Lógica para filtrar las transacciones por el tipo de reporte y el rango de
            // fechas
            double totalIngresos = 0.0;
            double totalGastos = 0.0;
            double saldo = 0.0;

            for (Transaccion transaccion : transacciones) {
                // Filtrar por tipo de reporte (Ingresos, Gastos, Saldos)
                if (reportType.equals("Ingresos") && transaccion.getTipoTransaccion().equals("Depósito")) {
                    totalIngresos += transaccion.getMonto();
                } else if (reportType.equals("Gastos") && transaccion.getTipoTransaccion().equals("Transferencia")) {
                    totalGastos += transaccion.getMonto();
                } else if (reportType.equals("Saldos")) {
                    // Calcular saldo (por ejemplo, ingresos - gastos)
                    saldo += (transaccion.getTipoTransaccion().equals("Transferencia") ? transaccion.getMonto() : -transaccion.getMonto());
                }
            }
            

            // Mostrar los totales en consola para ver los resultados
            System.out.println("Total Ingresos: " + totalIngresos);
            System.out.println("Total Gastos: " + totalGastos);
            System.out.println("Saldo: " + saldo);

            // Generar el reporte dependiendo del formato seleccionado (PDF o Excel)
            if (secondReportType.equals("PDF")) {
                generatePDFReport(reportType, startDate, endDate, totalIngresos, totalGastos, saldo);
            } else if (secondReportType.equals("Excell")) {
                generateExcelReport(reportType, startDate, endDate, totalIngresos, totalGastos, saldo);
            }
        } else {
            // Mostrar un mensaje de error si faltan campos
            System.out.println("Error: Por favor complete todos los campos.");
        }
    }

    private void generatePDFReport(String reportType, LocalDate startDate, LocalDate endDate, double totalIngresos,
            double totalGastos, double saldo) {
        // Crear un documento PDF
        PDDocument document = new PDDocument();

        try {
            // Crear la carpeta "C:\Reportes" si no existe
            java.io.File reportFolder = new java.io.File("C:/Reportes");
            if (!reportFolder.exists()) {
                reportFolder.mkdir(); // Crear la carpeta si no existe
            }

            // Crear una nueva página
            PDPage page = new PDPage();
            document.addPage(page);

            // Crear un flujo de contenido para escribir en la página
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Configurar el estilo de texto
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);

            // Escribir título
            contentStream.showText("Reporte Financiero");
            contentStream.newLineAtOffset(0, -20);

            // Escribir el tipo de reporte y el rango de fechas
            contentStream.showText("Tipo de Reporte: " + reportType);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Fecha de Inicio: " + startDate.toString());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Fecha de Fin: " + endDate.toString());

            // Escribir los totales según el tipo de reporte
            if (reportType.equals("Ingresos")) {
                contentStream.showText("Total Ingresos: " + totalIngresos);
            } else if (reportType.equals("Gastos")) {
                contentStream.showText("Total Gastos: " + totalGastos);
            } else if (reportType.equals("Saldos")) {
                contentStream.showText("Saldo: " + saldo);
            }

            // Finalizar el flujo de contenido
            contentStream.endText();
            contentStream.close();

            // Guardar el documento en "C:\Reportes"
            document.save("C:/Reportes/Reporte_" + reportType + "_" + startDate + "_to_" + endDate + ".pdf");

            // Cerrar el documento
            document.close();

            System.out.println("Reporte PDF generado exitosamente.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al generar el reporte PDF.");
        }
    }

    // Método para generar el reporte en Excel (implementación básica con Apache
    // POI)
    private void generateExcelReport(String reportType, LocalDate startDate, LocalDate endDate, double totalIngresos,
            double totalGastos, double saldo) {
        try {
            // Crear la carpeta "C:\Reportes" si no existe
            java.io.File reportFolder = new java.io.File("C:/Reportes");
            if (!reportFolder.exists()) {
                reportFolder.mkdir(); // Crear la carpeta si no existe
            }

            org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
            org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.createSheet("Reporte");

            // Crear una fila para los encabezados
            org.apache.poi.xssf.usermodel.XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("Tipo de Reporte");
            row.createCell(1).setCellValue("Fecha de Inicio");
            row.createCell(2).setCellValue("Fecha de Fin");

            // Crear una fila con los valores del reporte
            row = sheet.createRow(1);
            row.createCell(0).setCellValue(reportType);
            row.createCell(1).setCellValue(startDate.toString());
            row.createCell(2).setCellValue(endDate.toString());

            // Añadir totales según el tipo de reporte
            row = sheet.createRow(2);
            if (reportType.equals("Ingresos")) {
                row.createCell(3).setCellValue("Total Ingresos: " + totalIngresos);
            } else if (reportType.equals("Gastos")) {
                row.createCell(3).setCellValue("Total Gastos: " + totalGastos);
            } else if (reportType.equals("Saldos")) {
                row.createCell(3).setCellValue("Saldo: " + saldo);
            }

            // Guardar el archivo Excel en "C:\Reportes"
            java.io.FileOutputStream fileOut = new java.io.FileOutputStream(
                    "C:/Reportes/Reporte_" + reportType + "_" + startDate + "_to_" + endDate + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();

            workbook.close();

            System.out.println("Reporte Excel generado exitosamente.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al generar el reporte Excel.");
        }
    }

}
