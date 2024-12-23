package waveon.waveon.ui.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PathRegisterButtonController {

    @FXML
    public Button registerButton;

    @FXML
    public void initialize() {
        assert registerButton != null;
        registerButton.setOnMouseClicked(e -> goToRegister());
    }

    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
