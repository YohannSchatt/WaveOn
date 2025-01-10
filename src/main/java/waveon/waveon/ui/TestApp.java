package waveon.waveon.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/MusicCommentsPage.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Music Comments Test Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}