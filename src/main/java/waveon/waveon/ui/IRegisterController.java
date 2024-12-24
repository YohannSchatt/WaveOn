package waveon.waveon.ui;
import javafx.scene.control.TextField;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public interface IRegisterController {

    TextField usernameField = new TextField();
    TextField emailField = new TextField();
    PasswordField passwordField = new PasswordField();

    /**
     * Launches the registration interface.
     */
    void register();

    /**
     * Handles user registration by collecting input and displaying the result.
     */
    void goToLogin();

    /**
     * Handles password reset by collecting input and displaying the result.
     */
    void goToForgetPassword();


}
