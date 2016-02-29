package by.dayslar.sample.Controllers;

import by.dayslar.sample.Utilites.UserUtil;
import by.dayslar.sample.Utilites.ChartUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GraphicWindowController implements Initializable {

    @FXML private DatePicker calendarOld;
    @FXML private DatePicker calendarNew;

    @FXML private ComboBox comboBox;

    @FXML private PieChart pieChart;
    @FXML private BarChart barChart;
    @FXML private AreaChart areaChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCalendar();
        initializeComboBox();

        long[] times = getTimeCalendar();
        ChartUtil.initializeRecords(times[0], times[1], UserUtil.getUser().getSubdivision());

    }

    @FXML
    public void clickCalendar(ActionEvent event) {
        String type = comboBox.getValue().toString();
        long[] times = getTimeCalendar();

        ChartUtil.initializeRecords(times[0], times[1], UserUtil.getUser().getSubdivision());
        checkChart(type);
    }

    private void initializeComboBox() {
        comboBox.setValue(ChartUtil.ChartType.DISTRIBUTION_CALL);

        comboBox.getItems().addAll(
                ChartUtil.ChartType.DISTRIBUTION_CALL,
                ChartUtil.ChartType.DISTRIBUTION_MANAGER,
                ChartUtil.ChartType.DISTRIBUTION_ANSWER);

        comboBox.setOnAction(event -> checkChart(comboBox.getValue().toString()));
    }

    private void initializeCalendar() {
        calendarOld.setValue(LocalDate.now());
        calendarNew.setValue(LocalDate.now());
    }

    private long[] getTimeCalendar() {
        long oldTime = calendarOld.getValue().toEpochDay() * 24 * 3600 * 1000;
        long newTime = calendarNew.getValue().toEpochDay() * 24 * 3600 * 1000 + 86400000;

        return new long[]{oldTime, newTime};
    }

    private void checkChart(String type){
        if (type != null){
            new Thread(()-> {
                ObservableList<XYChart.Series<String, Number>> barChartInfo = null;
                ObservableList<XYChart.Series<String, Number>> areaChartInfo = null;
                ObservableList<PieChart.Data> pieChartInfo = null;

                switch (type){
                    case ChartUtil.ChartType.DISTRIBUTION_CALL:
                        barChartInfo = ChartUtil.getDistributionCallSubdivision();
                        areaChartInfo = ChartUtil.getDistributionCallDay();
                        pieChartInfo = ChartUtil.getDistributionCallType();

                        break;

                    case ChartUtil.ChartType.DISTRIBUTION_MANAGER:
                        barChartInfo = ChartUtil.getDistributionManagersSubdivision();
                        areaChartInfo = ChartUtil.getDistributionManagersDay();
                        pieChartInfo = ChartUtil.getDistributionManagersType();

                        break;

                    case ChartUtil.ChartType.DISTRIBUTION_ANSWER:
                        barChartInfo = ChartUtil.getDistributionAnswerSubdivision();
                        areaChartInfo = ChartUtil.getDistributionAnswerDays();
                        pieChartInfo = ChartUtil.getDistributionAnswerManager();

                        break;
                }
                setChartInfo(type, barChartInfo, areaChartInfo, pieChartInfo);

            }).start();
        }
    }

    private void setChartInfo(String type,
                              ObservableList<XYChart.Series<String, Number>> barChartInfo,
                              ObservableList<XYChart.Series<String, Number>> areaChartInfo,
                              ObservableList<PieChart.Data> pieChartInfo) {
        Platform.runLater(()-> {
            barChart.setTitle(type);
            barChart.setData(barChartInfo);

            pieChart.setTitle(type);
            pieChart.setData(pieChartInfo);

            areaChart.setTitle(type);
            areaChart.setData(areaChartInfo);
        });
    }

}
