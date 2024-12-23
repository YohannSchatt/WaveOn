package waveon.waveon.ui;

//JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//BL imports
import waveon.waveon.bl.LoginFacade;

//components imports

//Java imports


public class MainPageController {

    private final LoginFacade loginFacade = LoginFacade.getInstance();

    @FXML
    private TextField searchField;

    @FXML
    private HBox searchBox;

    @FXML
    private VBox vBox;

    public void initialize() {
        updateLoginButton();
    }


    private void updateLoginButton() {
        vBox.getChildren().clear();
        if (loginFacade.getCurrentUser() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/Logout.fxml"));
                vBox.getChildren().add(loader.load());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathLoginButton.fxml"));
                vBox.getChildren().add(loader.load());

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathRegisterButton.fxml"));
                vBox.getChildren().add(loader2.load());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}