package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import waveon.waveon.bl.LoginFacade;

import java.io.IOException;

public class MainPageController {
    private final LoginFacade loginFacade = new LoginFacade();
    private Stage primaryStage;

    @FXML
    private TextField searchField;

    @FXML
    private HBox searchBox;

    @FXML
    private VBox vBox;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Page d'Accueil");
        updateLoginButton();
    }

    private void updateLoginButton() {
        vBox.getChildren().clear();
        if (loginFacade.getCurrentUser() != null) {
            Menu userMenu = new Menu(loginFacade.getCurrentUser().getUsername());
            MenuItem logoutItem = new MenuItem("Se déconnecter");
            logoutItem.setOnAction(e -> {
                loginFacade.logout();
                updateLoginButton();
            });
            userMenu.getItems().add(logoutItem);
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().add(userMenu);
            vBox.getChildren().add(menuBar);
        } else {
            Button loginButton = new Button("Se connecter");
            loginButton.setOnAction(e -> {
                LoginController loginPage = new LoginController();
                try {
                    loginPage.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            vBox.getChildren().add(loginButton);
        }
    }

    private Button createButton(String text, String fxmlPath) {
        Button button = new Button(text);
        button.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Scene scene = new Scene(loader.load());
                primaryStage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }
}