package by.dayslar.sample.Controllers;


import by.dayslar.sample.Model.Report;
import by.dayslar.sample.Utilites.PlayerUtil;
import by.dayslar.sample.Utilites.RecordUtil;
import by.dayslar.sample.Utilites.ReportUtil;
import by.dayslar.sample.Utilites.UserUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReportWindowController implements Initializable {

    /** Типо календари **/
    @FXML private DatePicker calendarOld;
    @FXML private DatePicker calendarNew;

    /** Лэйблы для общего отчета **/
    @FXML private Label labelAllCount;
    @FXML private Label labelAnswerCount;
    @FXML private Label labelNotAnswerCount;
    @FXML private Label labelAllDuration;

    /** Лэйблы для исходящих вызовов **/
    @FXML private Label labelOutgoingCount;
    @FXML private Label labelOutgoingAnswerCount;
    @FXML private Label labelOutgoingNotAnswerCount;
    @FXML private Label labelOutgoingDuration;

    /** Лэйблы для входящих вызовов **/
    @FXML private Label labelIncomingCount;
    @FXML private Label labelIncomingAnswerCount;
    @FXML private Label labelIncomingNotAnswerCount;
    @FXML private Label labelIncomingDuration;

    @FXML private ComboBox comboBox;

    //общий метод инитиализации
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCalendar();
        initializeComboBox();
        update();
    }

    //вызывается при выборе даты на одном из календарей
    public void clickCalendar(ActionEvent event){
        update();
    }

    //устанавливае начальное положение календарей (по умолчанию сегоднищний день)
    private void initializeCalendar(){
        calendarOld.setValue(LocalDate.now());
        calendarNew.setValue(LocalDate.now());
    }

    //устанавливаем начальное состояние комбобокса
    private void initializeComboBox(){
        comboBox.getItems().add(RecordUtil.FilterType.TYPE_ALL_SUBDIVISION);
        comboBox.setValue(RecordUtil.FilterType.TYPE_ALL_SUBDIVISION);


        comboBox.setOnAction(event -> {
            updateInfo(ReportUtil.getReport(comboBox.getValue().toString()));
        });
    }

    //вызывается при обновлении комбобкса
    private void updateComboBox(){
        ObservableList<String> listSubdivision = ReportUtil.getListSubdivision(ReportUtil.getRecords());

        Platform.runLater(() -> {
            String value = comboBox.getValue().toString();
            comboBox.getItems().clear();
            comboBox.getItems().addAll(listSubdivision);
            comboBox.getItems().add(RecordUtil.FilterType.TYPE_ALL_SUBDIVISION);
            comboBox.setValue(value);
        });

    }

    //вызывается для обновления списка записей
    private void updateList(){
        long[] time = calculationTime();
        ReportUtil.initialize(time[0], time[1], UserUtil.getUser().getSubdivision());
    }

    //возвращает массив с временем в секундах из календарей
    private long[] calculationTime(){
        long oldTime = calendarOld.getValue().toEpochDay() * 24 * 3600 * 1000;
        long newTime = calendarNew.getValue().toEpochDay() * 24 * 3600 * 1000 + 86400000;

        return new long[]{oldTime, newTime};
    }

    //общий метод для обновления
    private void update(){
        new Thread(() -> {
            updateList();
            updateInfo(ReportUtil.getReport(comboBox.getValue().toString()));
            updateComboBox();
        }).start();
    }

    //обновляет информацию на форме
    private void updateInfo(Report report) {
        Platform.runLater(()->{
            labelAllCount.setText(report.getCount() + "");
            labelAnswerCount.setText(report.getAnswerCount() + "");
            labelNotAnswerCount.setText(report.getNotAnswerCount() + "");
            labelAllDuration.setText(PlayerUtil.getCalculateTime(report.getDuration()));

            labelIncomingCount.setText(report.getIncomingCount() + "");
            labelIncomingAnswerCount.setText(report.getIncomingAnswerCount() + "");
            labelIncomingNotAnswerCount.setText(report.getIncomingNotAnswerCount() + "");
            labelIncomingDuration.setText(PlayerUtil.getCalculateTime(report.getIncomingDuration()));

            labelOutgoingCount.setText(report.getOutgoingCount() + "");
            labelOutgoingAnswerCount.setText(report.getOutgoingAnswerCount() + "");
            labelOutgoingNotAnswerCount.setText(report.getOutgoingNotAnswerCount() + "");
            labelOutgoingDuration.setText(PlayerUtil.getCalculateTime(report.getOutgoingDuration()));
        });
    }
}
