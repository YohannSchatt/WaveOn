package waveon.waveon.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import waveon.waveon.bl.LoginFacade;
import waveon.waveon.core.Artist;

public class MainpageUIJavaFX extends Application {
    private final LoginFacade loginFacade = new LoginFacade();
    private Stage primaryStage;
    private VBox vBox;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Page d'Accueil");

        // Créer la barre de recherche
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");
        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(5, 5, 5, 5));

        // Disposer les éléments dans un layout
        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        updateLoginButton();

        // Ajouter la barre de recherche à la barre de navigation
        BorderPane topCenterPane = new BorderPane();
        topCenterPane.setCenter(searchBox);
        topCenterPane.setRight(vBox);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topCenterPane);

        VBox centerLayout = new VBox(10);
        centerLayout.setPadding(new Insets(20));

        if (loginFacade.getCurrentUser() != null && loginFacade.getCurrentUser() instanceof Artist) {
            Button uploadMusicButton = new Button("Upload Music");
            uploadMusicButton.setOnAction(e -> navigateToUploadMusicPage());
            centerLayout.getChildren().add(uploadMusicButton);
        }

        borderPane.setCenter(centerLayout);

        // Créer et afficher la scène
        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void navigateToUploadMusicPage() {
        if (loginFacade.getCurrentUser() != null && loginFacade.getCurrentUser() instanceof Artist) {
            UploadMusicUIJavaFX uploadMusicUI = new UploadMusicUIJavaFX();
            try {
                uploadMusicUI.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Unable to navigate to the Upload Music page.");
            }
        } else {
            showAlert("Access Denied", "Only artists can upload music.");
        }
    }

    private void updateLoginButton() {
        vBox.getChildren().clear();
        if (loginFacade.getCurrentUser() != null) {
            //Label userEmailLabel = new Label(loginFacade.getCurrentUser().getEmail());
            //Button logoutButton = new Button("Se déconnecter");
            //logoutButton.setOnAction(e -> {
               // loginFacade.logout();
                //updateLoginButton();
            //});
            //vBox.getChildren().addAll(userEmailLabel, logoutButton);
            Menu userMenu = new Menu(loginFacade.getCurrentUser().getUsername());
            MenuItem logoutItem = new MenuItem("Se déconnecter");
            logoutItem.setOnAction(e -> {
                loginFacade.logout();// permet de mettre à null l'utilisateur courant
                updateLoginButton(); // met à jour l'affichage et le bouton se connecter
            });
            userMenu.getItems().add(logoutItem);
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().add(userMenu);
            vBox.getChildren().add(menuBar);
        } else {
            Button loginButton = new Button("Se connecter");
            loginButton.setOnAction(e -> {
                // Charger la page de connexion
                LoginUIJavaFX loginPage = new LoginUIJavaFX();
                try {
                    loginPage.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            vBox.getChildren().add(loginButton);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}