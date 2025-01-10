package waveon.waveon.ui.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import waveon.waveon.bl.UserSessionFacade;

public class LogoutController {

    @FXML
    public Button logoutButton;

    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();


    @FXML
    public void initialize() {
        assert logoutButton != null;
        logoutButton.setOnMouseClicked(e -> Logout());
    }

    private void Logout() {
        try {
            loginFacade.logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
