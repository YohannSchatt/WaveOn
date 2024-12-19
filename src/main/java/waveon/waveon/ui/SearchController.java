package waveon.waveon.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import waveon.waveon.bl.FilterOption;
import waveon.waveon.bl.SearchFacade;


public class SearchController extends Application { //} implements ISearchController {

    /**
     * Default constructor
     */
    public SearchController() {
    }

    public TextField searchField;
    public Label resultLabel;
    public SearchFacade searchFacade = new SearchFacade();
    public FilterOption filter;
    public int nbPage;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Search Page");

        // Create the search bar
        searchField = new TextField();
        searchField.setPromptText("Search...");
        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(10));

        // Set up the layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(searchBox);

        // Create a button to apply the search
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            SearchMusic();
        });
        searchButton.setPadding(new Insets(10));
        borderPane.setCenter(searchButton);

        // Create a label to display the search result
        resultLabel = new Label();
        borderPane.setBottom(resultLabel);

        // Create and display the scene
        Scene scene = new Scene(borderPane, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        searchButton.setOnAction(event -> {
            SearchMusic();
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void ChangePage() {
        // TODO implement here
    }

    public void SearchArtist() {
        // TODO implement ISearchController.SearchArtist() here
    }

    public void SearchMusic() {
        // TODO implement ISearchController.SearchMusic() here

        searchFacade.searchMusic(searchField.getText());
        String search = searchField.getText();
        searchFacade.searchMusic(search);
        if (!searchFacade.getCurrentMusicSearch().isEmpty()) {
            resultLabel.setText("Search successful");

        } else {
            resultLabel.setText("Search failed");
        }
    }

}