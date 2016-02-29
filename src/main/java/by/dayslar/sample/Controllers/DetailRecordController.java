package by.dayslar.sample.Controllers;

import by.dayslar.sample.Model.Record;
import by.dayslar.sample.Utilites.PlayerUtil;
import by.dayslar.sample.Utilites.RecordUtil;
import by.dayslar.sample.Utilites.ViewUtil;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class DetailRecordController implements Initializable {

    @FXML private Slider slider;
    @FXML private JFXButton btnPlay;
    @FXML private Label labelManagerName;
    @FXML private Label labelManagerPhone;
    @FXML private Label labelPodr;
    @FXML private Label labelContactName;
    @FXML private Label labelContactPhone;
    @FXML private Label labelType;
    @FXML private Label labelDate;

    @FXML private Label labelPlayerTime;

    private static Record record;
    private boolean isPlayingChange = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            record = RecordUtil.getSelectRecord();

            initializeListener();
            initializeInfo();
    }

    private void initializeInfo() {
        labelManagerName.setText("Менеджер: " + record.getManagerName());
        labelManagerPhone.setText("Телефон:" + record.getPhoneNumber());
        labelPodr.setText("Подразделение: " + record.getSubdivision());
        labelContactName.setText(record.getContactName());
        labelContactPhone.setText("Телефон: " + record.getCallNumber());
        labelType.setText("Тип вызова: " + (record.isCall()?"Исходящий":"Входящий") + " - " + (record.isCallAnswer()? "Отвечен":"Без ответа"));
        labelDate.setText("Дата вызова: " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(record.getCallTime()));
    }

    public void clickButton(ActionEvent actionEvent) {
        JFXButton button = (JFXButton) actionEvent.getSource();

        switch (button.getId()){
            case "btnPlay":
                btnPlay();
                break;

            case "btnBack":
                PlayerUtil.rewindMedia(-2000);
                break;

            case "btnNext":
                PlayerUtil.rewindMedia(2000);
                break;

            case "btnBackRecords":
                ViewUtil.setPane(ViewUtil.RECORDS_PANE);
                PlayerUtil.getMediaPlayer().stop();
                break;
        }
    }

    private void initializeListener() {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isPlayingChange)
                PlayerUtil.getMediaPlayer().setPosition(newValue.longValue() / 1000.0f);
            isPlayingChange = false;

        });

        PlayerUtil.addListener(new MediaPlayerEventAdapter(){

            private long timePosition;
            private String time;
            private long timeFull;
            private String fullTime;
            private float newPosition;

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                newPosition = mediaPlayer.getPosition();
                isPlayingChange = true;

                timePosition = mediaPlayer.getTime() / 1000;
                time = PlayerUtil.getCalculateTime(timePosition);

                timeFull = record.getCallDuration();
                fullTime = PlayerUtil.getCalculateTime(timeFull);

                Platform.runLater(() -> {
                    labelPlayerTime.setText(time + "/" + fullTime);
                    slider.setValue(newPosition * 1000.0f);
                });
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                isPlayingChange = false;
                slider.setValue(slider.getMin());
            }
        });
    }

    private void btnPlay(){
        Image image;
        if (PlayerUtil.isPlaying()){
            PlayerUtil.pause();

            image = new Image(getClass().getResourceAsStream("/icons/play.png"));
            btnPlay.setGraphic(new ImageView(image));
        }

        else {
            if (!record.getFilePatch().equals("")) {
                PlayerUtil.start();

                image = new Image(getClass().getResourceAsStream("/icons/pause.png"));
                btnPlay.setGraphic(new ImageView(image));
            }
        }
    }
}
