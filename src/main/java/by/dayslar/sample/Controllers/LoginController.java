package by.dayslar.sample.Controllers;

import by.dayslar.sample.Interface.UserDAO;
import by.dayslar.sample.Interface.impl.UserDAODatabase;
import by.dayslar.sample.Model.User;
import by.dayslar.sample.Utilites.DialogUtil;
import by.dayslar.sample.Utilites.UserUtil;
import by.dayslar.sample.Utilites.ViewUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Button btnLogin;
    @FXML private Button btnCancel;

    @FXML private TextField etName;
    @FXML private PasswordField etPassword;
    @FXML private JFXSpinner progress;

    private UserDAO userDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDatabaseUser();
    }

    @FXML
    private void onClick(ActionEvent event){
        JFXButton button = (JFXButton) event.getSource();

        switch (button.getId()){
            case "btnLogin":
                login();
                break;

            case "btnCancel":
                Platform.exit();
                break;
        }
    }

    private void initializeDatabaseUser() {
        new Thread(()->{
            userDAO = new UserDAODatabase();
        }).start();
    }

    private void login() {
        new Thread(() -> {
            if (etName.getText().equals("") || etPassword.getText().equals(""))
                DialogUtil.showDialogInfo("Ошика входа","Вы не ввели данные.\n");

            User user = new User(etName.getText(), etPassword.getText());
            progressVisibility(true);

            if (userDAO.checkUser(user)) {
                user.setSubdivision(userDAO.getSubdivisionUser());
                UserUtil.setUser(user);

                ViewUtil.initialize();
                ViewUtil.DRAWER_PANE.setDisable(false);
                ViewUtil.setPane(ViewUtil.RECORDS_PANE);
            }

            else {
                progressVisibility(false);
                DialogUtil.showDialogInfo("Ошика входа","Пользователь с введенными данными не обнаружен в системе!\n " +
                        "Попробуйте ввести другие данные и повторить попытку.");
            }

        }).start();
    }

    private void progressVisibility(boolean flag) {
        Platform.runLater(() -> progress.setVisible(flag));
    }
}
