package waveon.waveon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import waveon.waveon.ui.MainPageController;
import waveon.waveon.ui.stage.mainStage;

import java.io.IOException;
import java.net.URL;

public class StartApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlLocation = getClass().getResource("/waveon/waveon/MainPage.fxml");
            System.out.println("FXML Location: " + fxmlLocation);
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            primaryStage.setTitle("Main Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}