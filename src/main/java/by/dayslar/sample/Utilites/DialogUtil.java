package by.dayslar.sample.Utilites;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogUtil {

    //диолог с информацией
    public static void showDialogInfo(String title, String info){

        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            initAlertMainInfo(title, info, alert);

            alert.showAndWait();
        });
    }

    //диолог для выхода
    public static void showExitDialog(String title, String info){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            initAlertMainInfo(title, info, alert);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                PlayerUtil.releasePlayer();
                Platform.exit();
            }
        });
    }

    //диолог с ошибкой
    public static void showErrorDialog(String title, String info){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            initAlertMainInfo(title, info, alert);
            alert.showAndWait();
        });
    }

    //инитиализация общей информации для всех диологов
    private static void initAlertMainInfo(String title, String info, Alert alert) {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.initOwner(ViewUtil.homeStage);
    }
}
