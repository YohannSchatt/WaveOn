// File: src/main/java/waveon/waveon/ui/MainpageUIJavaFX.java
package waveon.waveon.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import waveon.waveon.bl.LoginFacade;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.core.Music;

import java.io.File;
import java.sql.Time;

public class MainpageUIJavaFX extends Application {
    private final LoginFacade loginFacade = new LoginFacade();
    private final MusicFacade musicFacade = new MusicFacade(new Music(1, "Sample Music", null, new Time(0), new File("src/music/Zoltraak.mp3")));
    private Stage primaryStage;
    private VBox vBox;
    private Slider progressBar;

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

        // Créer et afficher la scène
        Scene scene = new Scene(borderPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
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

        // Add buttons to play and stop music
        Button playMusicButton = new Button("Play Music");
        playMusicButton.setOnAction(e -> {
            musicFacade.playMusic();
            updateProgressBar();
        });
        vBox.getChildren().add(playMusicButton);

        Button stopMusicButton = new Button("Stop Music");
        stopMusicButton.setOnAction(e -> musicFacade.stopMusic());
        vBox.getChildren().add(stopMusicButton);

        // Add progress bar
        progressBar = new Slider();
        progressBar.setMin(0);
        progressBar.setMax(100);
        progressBar.setValue(0);
        vBox.getChildren().add(progressBar);
    }

    private void updateProgressBar() {
        if (musicFacade != null) {
            Duration totalDuration = musicFacade.getTotalDuration();
            progressBar.setMax(totalDuration.toSeconds());

            musicFacade.getMediaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                progressBar.setValue(newValue.toSeconds());
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}