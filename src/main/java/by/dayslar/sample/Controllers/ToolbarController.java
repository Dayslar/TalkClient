package by.dayslar.sample.Controllers;

import by.dayslar.sample.Utilites.DialogUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ToolbarController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void clickButton(ActionEvent actionEvent) {
        JFXButton clickButton = (JFXButton) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        switch (clickButton.getId()){
            case "btnClose":
                closeWindow();
                break;

            case "btnFull":
                fullScreenWindow(stage);
                break;

            case "btnHide":
                hideWindow(stage);
                break;
        }
    }

    private void hideWindow(Stage stage) {
        stage.setIconified(true);
    }

    private void fullScreenWindow(Stage stage) {
        stage.setFullScreen(!stage.isFullScreen());
    }

    private void closeWindow() {
        DialogUtil.showExitDialog("Выход из приложения", "Вы уверены, что хотите выйти из приложения?");
    }
}
