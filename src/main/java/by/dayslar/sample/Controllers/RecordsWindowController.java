package by.dayslar.sample.Controllers;

import by.dayslar.sample.Interface.impl.RecordDAODatabase;
import by.dayslar.sample.Model.Record;
import by.dayslar.sample.Utilites.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RecordsWindowController implements Initializable {

    @FXML private TableView tableSource;
    @FXML private TableColumn<Record, String> collumName;
    @FXML private TableColumn<Record , String> collumManager;
    @FXML private TableColumn<Record , String> collumDuration;
    @FXML private TableColumn<Record , String> collumDate;

    @FXML private JFXTextField etSearch;

    @FXML private Label labelInfo;
    @FXML private Label labelInfoAllTime;

    @FXML private DatePicker calendarOld;
    @FXML private DatePicker calendarNew;

    @FXML private ComboBox comboBoxManagers;
    @FXML private ComboBox comboBoxType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBox();
        initializeCalendar();
        initializeTable();

        updateTable(CalendarUtil.getOldDate(), CalendarUtil.getNewDate());
        initializeListener();

        new Thread(()->{
            PlayerUtil.initialize();
            SettingUtil.loadSetting();
        }).start();

    }

    private void initializeComboBox() {
        initializeComboBoxType();
        comboBoxManagers.setValue(RecordUtil.FilterType.TYPE_ALL_SUBDIVISION);
    }

    //инитиализация комбобоксаМенеджеров
    private void initializeComboBoxManager(ObservableList<Record> list) {
        Platform.runLater(()->{
            String value = (String) comboBoxManagers.getValue();
            comboBoxManagers.getItems().clear();
            comboBoxManagers.getItems().addAll(RecordUtil.getListBoxManager(list));
            comboBoxManagers.getItems().add(RecordUtil.FilterType.TYPE_ALL_SUBDIVISION);
            comboBoxManagers.setValue(value);
        });

    }

    //инитиализайия комбобокса типов
    private void initializeComboBoxType() {
        comboBoxType.getItems().addAll(FXCollections.observableArrayList(
                RecordUtil.FilterType.TYPE_ANSWER,
                RecordUtil.FilterType.TYPE_MISSED,
                RecordUtil.FilterType.TYPE_ALL_RECORD));
        comboBoxType.setValue(RecordUtil.FilterType.TYPE_ALL_RECORD);
    }

    //инициализация календарей
    private void initializeCalendar() {
        calendarOld.setValue(LocalDate.now());
        calendarNew.setValue(LocalDate.now());

        CalendarUtil.setOldDate(calendarOld.getValue());
        CalendarUtil.setNewDate(calendarNew.getValue());
    }

    //вызывается при нажатии на кнопки на экране
    public void clickButton(ActionEvent actionEvent) {
        JFXButton jfxButton = (JFXButton) actionEvent.getSource();

        switch (jfxButton.getId()){
            case "btnUpdate":
                updateTable(CalendarUtil.getOldDate(), CalendarUtil.getNewDate());
                break;

            case "btnSearch":
                search(etSearch.getText());
                break;
        }
    }

    //обновить таблицу из базы сервера по датам
    private void updateTable(long oldDate, long newDate) {
        new Thread(()-> {
            RecordUtil.setRecords(new RecordDAODatabase().getRecords(oldDate, newDate, UserUtil.getUser().getSubdivision()));
            updateTable(RecordUtil.getFilterRecord(RecordUtil.getRecords(), (String) comboBoxType.getValue(), (String) comboBoxManagers.getValue()));
            initializeComboBoxManager(RecordUtil.getRecords());
        }).start();
    }

    //обновить таблицу вручную из списка
    private void updateTable(ObservableList<Record> records){
        Platform.runLater(()->{
            tableSource.setItems(records);
            labelInfo.setText("Найдено" + " записей: " + tableSource.getItems().size());
            labelInfoAllTime.setText("Общая продолжительность: " + PlayerUtil.getCalculateTime(RecordUtil.getCalculationAllTime(records)));
        });
    }

    //инициализация таблицы
    private void initializeTable() {
        collumName.setCellValueFactory(record -> record.getValue().getContactNameProperty());
        collumManager.setCellValueFactory(record -> record.getValue().getManagerNameProperty());
        collumDuration.setCellValueFactory(record -> record.getValue().getCallDurationProperty());
        collumDate.setCellValueFactory(record -> record.getValue().getCallTimeProperty());

        tableSource.setRowFactory(new Callback<TableView, TableRow>() {
            @Override
            public TableRow call(TableView param) {
                return new TableRow<Record>(){
                    @Override
                    protected void updateItem(Record item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (!item.isCallAnswer())
                                setStyle("-fx-background-color:bisque");
                        }
                       else setStyle("-fx-background-color: white");

                        collumDate.setCellFactory(new Callback<TableColumn<Record, String>, TableCell<Record, String>>() {
                            @Override
                            public TableCell<Record, String> call(TableColumn<Record, String> param) {
                                return new TableCell<Record, String>(){
                                    @Override
                                    protected void updateItem(String value, boolean empty) {
                                        super.updateItem(value, empty);

                                        if (item != null){
                                            if (item.isCall())
                                                setImageCall("/icons/icon_outgoing.png");
                                            else
                                                setImageCall("/icons/icon_incoming.png");
                                        }

                                        setText(value);
                                    }

                                    private void setImageCall(String url) {
                                        ImageView imageView = new ImageView(url);
                                        setGraphic(imageView);
                                        ;
                                    }
                                };
                            }
                        });
                    }
                };
            }
        });
   }

    //инициализация лисенеров
    private void initializeListener() {
        tableSource.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                clickTableElement();
            }
        });

        etSearch.textProperty().addListener((event, newValue, oldValue)->{
            search(oldValue);
        });

    }

    //процедура поиска
    private void search(String oldValue) {
        SortedList<Record> sortedList =  RecordUtil.getSearchRecords(oldValue);
        sortedList.comparatorProperty().bind(tableSource.comparatorProperty());

        updateTable(sortedList);
    }

    //вызывается при выборе элемена в таблице
    private void clickTableElement() {
        SettingUtil.loadSetting();

        Record record = ((Record) tableSource.getSelectionModel().getSelectedItem());
        RecordUtil.setSelectRecord(record);

        String patch = SettingUtil.getDirectory() + "/" + record.getFilePatch();

        PlayerUtil.play(patch);
        ViewUtil.setInfoPane(record);
    }

    //вызывается при выборе даты
    public void clickCalendar(ActionEvent actionEvent) {

        CalendarUtil.setOldDate(calendarOld.getValue());
        CalendarUtil.setNewDate(calendarNew.getValue());

        updateTable(CalendarUtil.getOldDate(), CalendarUtil.getNewDate());
    }
}
