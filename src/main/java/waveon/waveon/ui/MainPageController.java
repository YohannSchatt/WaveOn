package waveon.waveon.ui;

//JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//BL imports
import waveon.waveon.bl.UserSessionFacade;

//components imports

//Java imports


public class MainPageController {

    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();

    @FXML
    private TextField searchField;

    @FXML
    private HBox searchBox;

    @FXML
    private HBox hBox;

    public void initialize() {
        updateLoginButton();
    }


    private void updateLoginButton() {
        if (loginFacade.getCurrentUser() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/LogoutButton.fxml"));
                hBox.getChildren().add(loader.load());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathLoginButton.fxml"));
                hBox.getChildren().add(loader.load());

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathRegisterButton.fxml"));
                hBox.getChildren().add(loader2.load());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}