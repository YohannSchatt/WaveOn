package waveon.waveon.ui;

//JavaFX imports
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

    @FXML
    public HBox hbox;

    public void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathRegisterButton.fxml"));
            hbox.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            if (loginFacade.login(email,password)) {
                resultLabel.setText("Login successful");
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
    public void handleLogin() {
        login();
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