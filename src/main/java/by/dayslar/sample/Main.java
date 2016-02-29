package by.dayslar.sample;


import by.dayslar.sample.Controllers.HomeWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import by.dayslar.sample.Utilites.PlayerUtil;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Мои разговоры");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/homes_window.fxml"));

        Parent root  = loader.load();
        HomeWindowController controller =  loader.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(root, 1000, 550);
        scene.getStylesheets().add("/styles/records_style.css");
        primaryStage.setScene(scene);

        Image image = new Image("/icons/icon.png");
        primaryStage.getIcons().add(image);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        onDragWindow(primaryStage, root);
        primaryStage.show();

    }

    private void onDragWindow(Stage primaryStage, Parent root) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        PlayerUtil.releasePlayer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
