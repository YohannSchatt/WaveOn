package waveon.waveon.ui;

//import business.facades.LoginFacade;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import waveon.waveon.bl.LoginFacade;
import waveon.waveon.core.IUser;
import waveon.waveon.core.OrdUser;
//import users.User;

public class LoginUIJavaFX extends Application  implements ILoginController {
    private final LoginFacade loginFacade = new LoginFacade();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login System");

        // créer un form pour le login
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        Label resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 1, 3);

        // bouton d'action du login
        loginButton.setOnAction(event -> {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            loginFacade.login(email);
            if (loginFacade.checkCredentials(email, password)) {
                resultLabel.setText("Login successful");
                resultLabel.setText("");
            } else {
                resultLabel.setText("Login failed");
            }

            resultLabel.setText("Welcome to test");
        });

        // ajoute tous les composants au grid
        grid.getChildren().addAll(emailLabel, emailInput, passwordLabel, passwordInput, loginButton, resultLabel);

        // créer et affiche la scène
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void launch() {
        launch();
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

    public static void main(String[] args) {
        launch(args);
    }
}
