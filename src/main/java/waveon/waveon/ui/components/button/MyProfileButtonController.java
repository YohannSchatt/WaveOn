package waveon.waveon.ui.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import waveon.waveon.bl.UserSessionFacade;

import java.io.IOException;

public class MyProfileButtonController {

    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();

    @FXML
    public Button myProfileButton;


    @FXML
    public void initialize() {
        assert myProfileButton != null;
        myProfileButton.setOnMouseClicked(e -> MyProfile());
    }

    private void MyProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/MyProfilePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) myProfileButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
