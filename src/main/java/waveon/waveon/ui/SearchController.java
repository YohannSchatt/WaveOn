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
    public Label resultLabel;
    public ListView<String> resultsListView;
    public SearchFacade searchFacade = new SearchFacade();
    public FilterOption filter;
    public int nbPage;

    // Variable to store the pause transition (used to delay search)
    private PauseTransition pauseTransition;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Search Page");

        // Create the search bar
        searchField = new TextField();
        searchField.setPromptText("Search...");

        // Initialize the pause transition with a 500ms delay
        pauseTransition = new PauseTransition(Duration.millis(500));
        pauseTransition.setOnFinished(e -> handleSearch()); // Trigger search after delay

        // Create the event handler for key release
        searchField.setOnKeyReleased(e -> {
            pauseTransition.playFromStart(); // Restart the pause timer on every key release
        });

        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(10));

        // Create a button to search for a music
        Button searchMusicButton = new Button("Search Music");
        searchMusicButton.setPadding(new Insets(10));
        searchMusicButton.setOnAction(e -> SearchMusic());

        // Create a button to search for an artist
        Button searchArtistButton = new Button("Search Artist");
        searchArtistButton.setPadding(new Insets(10));
        searchArtistButton.setOnAction(e -> SearchArtist());

        // Create a ListView to display the search results
        resultsListView = new ListView<>();
        resultsListView.setPrefHeight(150);

        // Create a label for displaying messages
        resultLabel = new Label();

        // Group the resultsListView and resultLabel in a VBox
        VBox bottomBox = new VBox(10, resultsListView, resultLabel);
        bottomBox.setPadding(new Insets(10));

        // Set up the layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(searchBox);
        borderPane.setLeft(searchMusicButton);
        borderPane.setRight(searchArtistButton);
        borderPane.setBottom(bottomBox);

        // Create and display the scene
        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void ChangePage() {
        // TODO implement here
    }

    public void SearchArtist() {
        // Clear previous results
        resultsListView.getItems().clear();

        // Perform the search
        String search = searchField.getText();
        searchFacade.searchArtist(search);
        ArrayList<Artist> searchResults = searchFacade.getCurrentArtistSearch();
        ArrayList<String> searchResultsFormatted = new ArrayList<>();

        // Format the results with the username
        for (Artist artist : searchResults) {
            String formattedResult = artist.getUsername();
            searchResultsFormatted.add(formattedResult);
        }

        // Display the results in the ListView
        if (!searchResults.isEmpty()) {
            resultsListView.getItems().addAll(searchResultsFormatted);
            resultLabel.setText("Search successful: " + searchResults.size() + " result(s) found.");
        } else {
            resultLabel.setText("No results found.");
        }
    }

    public void SearchMusic() {
        // Clear previous results
        resultsListView.getItems().clear();

        // Perform the search
        String search = searchField.getText();
        searchFacade.searchMusic(search);
        ArrayList<Music> searchResults = searchFacade.getCurrentMusicSearch();
        ArrayList<String> searchResultsFormatted = new ArrayList<>();

        // Format the results with the title and artist name
        for (Music music : searchResults) {
            String formattedResult = music.getName() + "  -  " + music.getArtist_name();
            searchResultsFormatted.add(formattedResult);
        }

        // Display the results in the ListView
        if (!searchResults.isEmpty()) {
            resultsListView.getItems().setAll(searchResultsFormatted);
            resultLabel.setText("Search successful: " + searchResults.size() + " result(s) found.");
        } else {
            resultsListView.getItems().clear();
            resultLabel.setText("No results found.");
        }
    }

    // This method will handle the search after a short delay
    private void handleSearch() {
        String search = searchField.getText();
        if (search.length() > 0) {
            searchFacade.searchMusic(search); // Search music as the user types
            updateSearchResults();
        } else {
            resultsListView.getItems().clear();
            resultLabel.setText("Start typing to search...");
        }
    }

    // Method to update the ListView with the current search results
    private void updateSearchResults() {
        ArrayList<Music> searchResults = searchFacade.getCurrentMusicSearch();
        ArrayList<String> searchResultsFormatted = new ArrayList<>();

        // Format the results with the title and artist name
        for (Music music : searchResults) {
            String formattedResult = music.getName() + "  -  " + music.getArtist_name();
            searchResultsFormatted.add(formattedResult);
        }

        // Display the results in the ListView
        if (!searchResults.isEmpty()) {
            resultsListView.getItems().setAll(searchResultsFormatted);
            resultLabel.setText("Search successful: " + searchResults.size() + " result(s) found.");
        } else {
            resultsListView.getItems().clear();
            resultLabel.setText("No results found.");
        }
    }
}
