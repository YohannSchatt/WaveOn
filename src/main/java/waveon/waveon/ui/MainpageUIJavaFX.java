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

        // Créer la barre de recherche
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");
        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(5, 5, 5, 5));

        // Layout elements
        TopRightPane = new VBox(10);
        TopRightPane.setPadding(new Insets(10));
        updateLoginButton();

        // Ajouter la barre de recherche à la barre de navigation
        BorderPane topCenterPane = new BorderPane();
        topCenterPane.setCenter(searchBox);
        topCenterPane.setRight(TopRightPane);

        // Create the music list view
        musicListView = new ListView<>();
        for (Music music : musicFacade.getMusicList()) {
            musicListView.getItems().add(music.getTitle());
        }
        musicListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                musicFacade.loadMusicByTitle(newValue);
            }
        });

        // Load the MusicPlayerControl FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/MusicPlayerControl.fxml"));
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

            // Ajouter le bouton de recherche
            Button searchButton = new Button("Search");
            searchButton.setOnAction(e -> {
                SearchController searchPage = new SearchController();
                try {
                    searchPage.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            TopRightPane.getChildren().add(searchButton);
            TopRightPane.getChildren().add(loginButton);
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