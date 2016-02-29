package by.dayslar.sample.Utilites;

import by.dayslar.sample.Model.Record;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewUtil {

    private static final String RECORDS_WINDOW = "/ui/record_window.fxml";
    private static final String SETTING_WINDOW = "/ui/setting_window.fxml";
    private static final String GRAPHIC_WINDOW = "/ui/graphic_window.fxml";
    private static final String DETAIL_WINDOW = "/ui/details_record.fxml";
    private static final String LOGIN_WINDOW = "/ui/login_window.fxml";
    private static final String REPORT_WINDOW = "/ui/report_window.fxml";

    public static final String DRAWER_CONTENT = "/ui/drawer.fxml";
    public static final String TOOLBAR_CONTENT = "/ui/toolbar.fxml";

    public static AnchorPane SETTING_PANE;
    public static AnchorPane RECORDS_PANE;
    public static AnchorPane GRAPHIC_PANE;
    public static AnchorPane DETAIL_PANE;
    public static AnchorPane LOGIN_PANE;
    public static AnchorPane REPORT_PANE;

    public static ToolBar TOOLBAR_PANE;
    public static AnchorPane DRAWER_PANE;

    public static BorderPane content;
    public static Stage homeStage;

    public static void initializeLoginWindow(){

        LOGIN_PANE = (AnchorPane) getWindow(LOGIN_WINDOW);
        DRAWER_PANE= (AnchorPane) getWindow(DRAWER_CONTENT);
        TOOLBAR_PANE = (ToolBar) getWindow(TOOLBAR_CONTENT);
    }

    public static void initialize(){

        SETTING_PANE = (AnchorPane) getWindow(SETTING_WINDOW);
        RECORDS_PANE = (AnchorPane) getWindow(RECORDS_WINDOW);
        GRAPHIC_PANE = (AnchorPane) getWindow(GRAPHIC_WINDOW);
        REPORT_PANE = (AnchorPane) getWindow(REPORT_WINDOW);
    }

    private static Node getWindow(String layout) {
        try {
            Node node = FXMLLoader.load(ViewUtil.class.getClass().getResource(layout));
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new AnchorPane();
    }

    public static void setPane(Node node){
        Platform.runLater(()->{
            content.setCenter(node);
        });

    }

    public static void setDrawerContent(Node node){
        Platform.runLater(() -> {
            content.setLeft(node);
        });
    }

    public static void setToolbarContent(Node node){
        Platform.runLater(() -> {
            content.setTop(node);
        });

    }

    public static void setInfoPane(Record record){
        DETAIL_PANE = (AnchorPane) getWindow(DETAIL_WINDOW);
        RecordUtil.setSelectRecord(record);
        setPane(DETAIL_PANE);
    }

    public static void setContent(BorderPane content){
        ViewUtil.content = content;
    }

    public static void setStage(Stage homeStage){
        ViewUtil.homeStage = homeStage;
    }
}
