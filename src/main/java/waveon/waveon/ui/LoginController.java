package waveon.waveon.ui;

//JavaFX imports
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import waveon.waveon.bl.LoginFacade;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

//Java imports
import java.io.IOException;

public class LoginController implements ILoginController {

    private final LoginFacade loginFacade = LoginFacade.getInstance();

    @FXML
    public TextField emailInput;

    @FXML
    public TextField passwordInput;

    @FXML
    public Label resultLabel;

    public LoginController() {
        initialize();
    }

    private void initialize() {}

    private void login() {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            if (loginFacade.login(email,password)) {
                resultLabel.setText("Login successful");
                MainPageController mainPage = new MainPageController();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                resultLabel.setText("Login failed");
            }
    }

    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleLogin() {
        // Already implemented via the JavaFX event handler
    }

    @Override
    public void handleRegister() {
        // Implementation for registration could be added here
    }

    @Override
    public void handlePasswordReset() {
        // Implementation for password reset could be added here
    }
}