package waveon.waveon.ui.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PrivateMessageButton {

    @FXML
    public Button privateMessageButton;

    @FXML
    public void initialize() {
        assert privateMessageButton != null;
        privateMessageButton.setOnAction(event -> goToRegister());
    }

    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/privateMessagePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) privateMessageButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
