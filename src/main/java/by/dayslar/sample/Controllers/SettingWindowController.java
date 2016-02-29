package by.dayslar.sample.Controllers;

import by.dayslar.sample.Utilites.SettingUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingWindowController implements Initializable {

    @FXML private JFXTextField etDirectory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateSetting();
    }

    private void updateSetting() {
        SettingUtil.loadSetting();
        etDirectory.setText(SettingUtil.getDirectory());
    }

    public void clickButton(ActionEvent actionEvent) {
        JFXButton jfxButton = (JFXButton) actionEvent.getSource();

        switch (jfxButton.getId()){
            case "btnSave":
                SettingUtil.saveSetting(etDirectory.getText());
                break;

            case "btnCancel":
                updateSetting();
                break;
        }
    }
}
