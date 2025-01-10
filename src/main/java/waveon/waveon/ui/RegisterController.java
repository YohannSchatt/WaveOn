package waveon.waveon.ui;

//JavaFX imports
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//Java imports
import java.io.IOException;

//BL imports
import javafx.util.Duration;
import waveon.waveon.bl.UserSessionFacade;

public class RegisterController implements IRegisterController {

    private final UserSessionFacade registerFacade = UserSessionFacade.getInstance();

    @FXML
    public PasswordField passwordInput;

    @FXML
    public TextField emailInput;

    @FXML
    public TextField usernameInput;

    @FXML
    public HBox hbox;

    @FXML
    public Label resultLabel;

    @FXML
    public CheckBox isArtist;

    @Override
    public void register() {
        boolean sucess = registerFacade.register(emailInput.getText(),usernameInput.getText(), passwordInput.getText(), isArtist.isSelected());
        if (sucess) {
            resultLabel.setText("Registration successful");
            goToLogin();
        } else {
            resultLabel.setText("Registration failed");
        }
    }

    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathLoginButton.fxml"));
            hbox.getChildren().add(loader.load());
            isArtist.setSelected(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailInput.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/MainPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailInput.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void goToForgetPassword() {
        // TODO Auto-generated method stub
    }

    public void handleRegister() {
        register();
    }


}
