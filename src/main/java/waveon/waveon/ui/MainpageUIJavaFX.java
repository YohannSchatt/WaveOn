package waveon.waveon.ui;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import waveon.waveon.bl.FilterOption;
import waveon.waveon.bl.LoginFacade;
import waveon.waveon.bl.SearchFacade;
import waveon.waveon.core.Artist;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.core.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class MainpageUIJavaFX extends Application {
    private final LoginFacade loginFacade = new LoginFacade();
    private final MusicFacade musicFacade = new MusicFacade();
    private Stage primaryStage;
    private VBox TopRightPane;
    private ListView<String> musicListView;

    public TextField searchField;
    public Label musicResultLabel;
    public Label artistResultLabel;
    public ListView<String> musicsListView;
    public ListView<String> artistsListView;
    public SearchFacade searchFacade = new SearchFacade();
    public ComboBox<String> filterComboBox;

    // Variable to store the pause transition (used to delay search)
    private PauseTransition pauseTransition;

    // File: src/main/java/waveon/waveon/ui/MainpageUIJavaFX.java

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Page d'Accueil");

        // Create the search bar
        searchField = new TextField();
        searchField.setPromptText("Search...");

        // Initialize the pause transition with a 500ms delay
        pauseTransition = new PauseTransition(Duration.millis(500));
        pauseTransition.setOnFinished(e -> handleSearch()); // Trigger search after delay

        // Create the event handler for key release
        searchField.setOnKeyReleased(e -> pauseTransition.playFromStart()); // Restart the pause timer

        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(5, 5, 5, 5));

        // Create a ComboBox for sorting options
        filterComboBox = new ComboBox<>();
        filterComboBox.getItems().addAll("Time ↓", "Time ↑", "Streams count ↓", "Streams count ↑");
        filterComboBox.setValue("Streams count ↓"); // Default selection
        filterComboBox.setOnAction(e -> useFilterMusic(convertToFilterOption(filterComboBox.getValue()))); // Apply filter on selection

        // Add the ComboBox to the search bar
        HBox filterBox = new HBox(10, new Label("Sort by:"), filterComboBox);
        filterBox.setPadding(new Insets(10));

        // Create ListViews for music and artists
        musicsListView = new ListView<>();
        musicsListView.setPrefHeight(150);
        artistsListView = new ListView<>();
        artistsListView.setPrefHeight(150);

        // Create labels for each ListView
        musicResultLabel = new Label("Music Results:");
        artistResultLabel = new Label("Artist Results:");

        // Group the music results in a VBox
        VBox musicBox = new VBox(10, musicResultLabel, musicsListView);
        musicBox.setPadding(new Insets(10));

        // Group the artist results in a VBox
        VBox artistBox = new VBox(10, artistResultLabel, artistsListView);
        artistBox.setPadding(new Insets(10));

        // Combine the two result sections in an HBox
        HBox resultsBox = new HBox(10, musicBox, artistBox);

        // Set up the layout
        VBox topBox = new VBox(10, searchBox, filterBox);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topBox);
        borderPane.setCenter(resultsBox);

        // Layout elements
        TopRightPane = new VBox(10);
        TopRightPane.setPadding(new Insets(10));
        updateLoginButton();

        // Create the music list view
        musicListView = new ListView<>();
        for (Music music : musicFacade.getMusicList()) {
            musicListView.getItems().add(music.getTitle());
        }
        musicsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                musicFacade.loadMusicByTitle(newValue);
            }
        });

        // Add mouse click event to play music on click
        musicsListView.setOnMouseClicked(event -> {
            String selectedMusic = musicsListView.getSelectionModel().getSelectedItem();
            if (selectedMusic != null) {
                musicFacade.loadMusicByTitle(selectedMusic);
                musicFacade.playMusic();
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

        borderPane.setBottom(musicPlayerControl);

        VBox centerLayout = new VBox(10);
        centerLayout.setPadding(new Insets(20));

        if (loginFacade.getCurrentUser() != null && loginFacade.getCurrentUser() instanceof Artist) {
            Button uploadMusicButton = new Button("Upload Music");
            uploadMusicButton.setOnAction(e -> navigateToUploadMusicPage());
            centerLayout.getChildren().add(uploadMusicButton);
        }

        // Create and display the scene
        Scene scene = new Scene(borderPane, 600, 400);
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
                //LoginUIJavaFX loginPage = new LoginUIJavaFX();
                //try {
                  //  loginPage.start(primaryStage);
                //} catch (Exception ex) {
                  //  ex.printStackTrace();
                //}
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
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleSearch() {
        String search = searchField.getText();
        if (!search.isEmpty()) {
            searchFacade.searchMusic(search);
            searchFacade.searchArtist(search);
            updateMusicResults();
            updateArtistResults();
        } else {
            musicsListView.getItems().clear();
            artistsListView.getItems().clear();
            musicResultLabel.setText("Music Results: (Start typing to search)");
            artistResultLabel.setText("Artist Results: (Start typing to search)");
        }
    }

    private void updateMusicResults() {
        ArrayList<Music> searchResults = searchFacade.getCurrentMusicSearch();
        ArrayList<String> formattedResults = new ArrayList<>();

        // Format the music results
        for (Music music : searchResults) {
            String formattedResult = music.getTitle();// + "  -  " + music.getArtistName();
            formattedResults.add(formattedResult);
        }

        // Update the ListView and label
        if (!searchResults.isEmpty()) {
            musicsListView.getItems().setAll(formattedResults);
            musicResultLabel.setText("Music Results: " + searchResults.size() + " found");
        } else {
            musicsListView.getItems().clear();
            musicResultLabel.setText("Music Results: No matches found");
        }

        // Apply the currently selected filter
        useFilterMusic(convertToFilterOption(filterComboBox.getValue()));
    }

    private void updateArtistResults() {
        ArrayList<Artist> searchResults = searchFacade.getCurrentArtistSearch();
        ArrayList<String> formattedResults = new ArrayList<>();

        // Format the artist results
        for (Artist artist : searchResults) {
            String formattedResult = artist.getUsername();
            formattedResults.add(formattedResult);
        }

        // Update the ListView and label
        if (!searchResults.isEmpty()) {
            artistsListView.getItems().setAll(formattedResults);
            artistResultLabel.setText("Artist Results: " + searchResults.size() + " found");
        } else {
            artistsListView.getItems().clear();
            artistResultLabel.setText("Artist Results: No matches found");
        }
    }

    private void useFilterMusic(FilterOption filter) {
        ArrayList<Music> searchResults = searchFacade.getCurrentMusicSearch();

        if (searchResults == null || searchResults.isEmpty()) {
            musicsListView.getItems().clear();
            musicResultLabel.setText("Music Results: No matches found");
            return;
        }

        // Sort the results based on the selected filter
        switch (filter) {
            case Newest:
                searchResults.sort((m1, m2) -> m2.getReleaseDate().compareTo(m1.getReleaseDate()));
                break;
            case Oldest:
                searchResults.sort(Comparator.comparing(Music::getReleaseDate));
                break;
            case MostListened:
                searchResults.sort((m1, m2) -> Integer.compare(m2.getStreamCount(), m1.getStreamCount()));
                break;
            case LeastListened:
                searchResults.sort(Comparator.comparingInt(Music::getStreamCount));
                break;
        }

        // Update the ListView with the sorted results
        ArrayList<String> formattedResults = new ArrayList<>();
        for (Music music : searchResults) {
            String formattedResult = music.getTitle();// + "  -  " + music.getArtistName();
            formattedResults.add(formattedResult);
        }

        musicsListView.getItems().setAll(formattedResults);
        musicResultLabel.setText("Music Results: " + searchResults.size() + " found");
    }

    public FilterOption convertToFilterOption(String filter) {
        return switch (filter) {
            case "Time ↓" -> FilterOption.Newest;
            case "Time ↑" -> FilterOption.Oldest;
            case "Streams count ↑" -> FilterOption.LeastListened;
            default -> FilterOption.MostListened;
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}