package waveon.waveon.ui;

//import business.facades.LoginFacade;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import waveon.waveon.bl.LoginFacade;
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

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

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
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            boolean user = loginFacade.login(username, password);

            if (user) {
                resultLabel.setText("Welcome " + username);
            } else {
                resultLabel.setText("Invalid username or password.");
            }

            //resultLabel.setText("Welcome to test");
        });

        // ajoute tous les composants au grid
        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, resultLabel);

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
