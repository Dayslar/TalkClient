package by.dayslar.sample.Controllers;

import by.dayslar.sample.Utilites.ViewUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeWindowController implements Initializable {

    @FXML private BorderPane container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ViewUtil.initializeLoginWindow();
        ViewUtil.setContent(container);
        ViewUtil.setPane(ViewUtil.LOGIN_PANE);
        ViewUtil.setDrawerContent(ViewUtil.DRAWER_PANE);
        ViewUtil.setToolbarContent(ViewUtil.TOOLBAR_PANE);

    }

    public void setStage(Stage stage){
        ViewUtil.setStage(stage);
    }
}
