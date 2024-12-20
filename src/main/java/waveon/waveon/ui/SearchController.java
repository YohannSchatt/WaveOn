package waveon.waveon.ui;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import waveon.waveon.bl.FilterOption;
import waveon.waveon.bl.SearchFacade;
import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;

import java.util.ArrayList;

public class SearchController extends Application {

    public TextField searchField;
    public Label musicResultLabel;
    public Label artistResultLabel;
    public ListView<String> musicsListView;
    public ListView<String> artistsListView;
    public SearchFacade searchFacade = new SearchFacade();
    public ComboBox<String> filterComboBox;

    // Variable to store the pause transition (used to delay search)
    private PauseTransition pauseTransition;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Search Page with Sorting and Separate Lists");

        // Create the search bar
        searchField = new TextField();
        searchField.setPromptText("Search...");

        // Initialize the pause transition with a 500ms delay
        pauseTransition = new PauseTransition(Duration.millis(500));
        pauseTransition.setOnFinished(e -> handleSearch()); // Trigger search after delay

        // Create the event handler for key release
        searchField.setOnKeyReleased(e -> pauseTransition.playFromStart()); // Restart the pause timer

        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(10));

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

        // Create and display the scene
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleSearch() {
        String search = searchField.getText();
        if (search.length() > 0) {
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
            String formattedResult = music.getName() + "  -  " + music.getArtist_name();
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
                searchResults.sort((m1, m2) -> m2.getDate().compareTo(m1.getDate()));
                break;
            case Oldest:
                searchResults.sort((m1, m2) -> m1.getDate().compareTo(m2.getDate()));
                break;
            case MostListened:
                searchResults.sort((m1, m2) -> Integer.compare(m2.getStream_count(), m1.getStream_count()));
                break;
            case LeastListened:
                searchResults.sort((m1, m2) -> Integer.compare(m1.getStream_count(), m2.getStream_count()));
                break;
        }

        // Update the ListView with the sorted results
        ArrayList<String> formattedResults = new ArrayList<>();
        for (Music music : searchResults) {
            String formattedResult = music.getName() + "  -  " + music.getArtist_name();
            formattedResults.add(formattedResult);
        }

        musicsListView.getItems().setAll(formattedResults);
        musicResultLabel.setText("Music Results: " + searchResults.size() + " found (sorted by " + filter + ")");
    }

    public FilterOption convertToFilterOption(String filter) {
        return switch (filter) {
            case "Time ↓" -> FilterOption.Newest;
            case "Time ↑" -> FilterOption.Oldest;
            case "Streams count ↓" -> FilterOption.MostListened;
            case "Streams count ↑" -> FilterOption.LeastListened;
            default -> FilterOption.Newest;
        };
    }
}
