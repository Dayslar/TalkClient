package by.dayslar.sample.Controllers;

import by.dayslar.sample.Utilites.PlayerUtil;
import by.dayslar.sample.Utilites.ViewUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawerController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void clickButton(ActionEvent actionEvent) {
        JFXButton clickButton = (JFXButton) actionEvent.getSource();

        switch (clickButton.getId()) {
            case "btnSetting":
                setWindow(ViewUtil.SETTING_PANE);
                break;

            case "btnRecords":
                setWindow(ViewUtil.RECORDS_PANE);
                break;

            case "btnOtchet":
                setWindow(ViewUtil.REPORT_PANE);
                break;

            case "btnGraphic":
                setWindow(ViewUtil.GRAPHIC_PANE);
                break;

        }
    }

    private void setWindow(Node node) {
        PlayerUtil.stop();
        ViewUtil.setPane(node);
    }
}
