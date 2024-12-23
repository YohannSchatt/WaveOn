package waveon.waveon.ui.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PathLoginButtonController {

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        System.out.println("PathLoginButtonController Initialize");
        assert loginButton != null;
        loginButton.setOnAction(event -> goToLogin());
    }

    public void goToLogin() {
        try {
            System.out.println("Login Button Clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}