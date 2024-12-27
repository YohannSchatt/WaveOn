// File: src/main/java/waveon/waveon/ui/MainpageUIJavaFX.java
package waveon.waveon.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import waveon.waveon.bl.LoginFacade;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.core.Music;

import java.io.IOException;

public class MainpageUIJavaFX extends Application {
    private final LoginFacade loginFacade = new LoginFacade();
    private final MusicFacade musicFacade = new MusicFacade();
    private Stage primaryStage;
    private VBox TopRightPane;
    private ListView<String> musicListView;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Page d'Accueil");

        // Create the search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");
        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(5, 5, 5, 5));

        // Layout elements
        TopRightPane = new VBox(10);
        TopRightPane.setPadding(new Insets(10));
        updateLoginButton();

        // Add the search bar to the navigation bar
        BorderPane topCenterPane = new BorderPane();
        topCenterPane.setCenter(searchBox);
        topCenterPane.setRight(TopRightPane);

        // Create the music list view
        musicListView = new ListView<>();
        for (Music music : musicFacade.getMusicList()) {
            musicListView.getItems().add(music.getName());
        }
        musicListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                musicFacade.loadMusicByTitle(newValue);
            }
        });

        // Load the MusicPlayerControl FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MusicPlayerControl.fxml"));
        VBox musicPlayerControl = null;
        try {
            musicPlayerControl = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the MusicFacade to the controller
        MusicPlayerControl controller = loader.getController();
        controller.setMusicFacade(musicFacade);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topCenterPane);
        borderPane.setCenter(musicListView);
        borderPane.setBottom(musicPlayerControl);

        // Create and display the scene
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        musicFacade.uninstallMusic("music");
    }

    private void updateLoginButton() {
        TopRightPane.getChildren().clear();
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
            TopRightPane.getChildren().add(menuBar);
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
            TopRightPane.getChildren().add(loginButton);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}