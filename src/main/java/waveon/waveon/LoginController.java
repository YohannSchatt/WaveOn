package waveon.waveon;

public interface LoginController {
    /**
     * Launches the login interface.
     */
    void launch();

    /**
     * Handles user login by collecting input and displaying the result.
     */
    void handleLogin();

    /**
     * Handles user registration by collecting input and displaying the result.
     */
    void handleRegister();

    /**
     * Handles password reset by collecting input and displaying the result.
     */
    void handlePasswordReset();
}
