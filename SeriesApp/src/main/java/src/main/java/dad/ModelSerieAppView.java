package src.main.java.dad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ModelSerieAppView  extends Application {

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ModelSerieAppView.stage = stage;
    }

    private static Stage stage;

        public static void main(String[] args) {
            launch(args);
        }

        Image image = new Image("TV.png");
        @Override
        public void start(Stage primaryStage) throws IOException {
            stage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("SerieAppView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(image);
            primaryStage.setScene(scene);
            scene.getStylesheets().add("styloCss.css");
            primaryStage.setTitle("Series Search");
            primaryStage.show();
        }

}
