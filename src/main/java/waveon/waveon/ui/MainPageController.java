package waveon.waveon.ui;

//JavaFX imports
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//BL imports
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import waveon.waveon.bl.*;
import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;

import java.util.ArrayList;
import java.util.Comparator;

//components imports

//Java imports


public class MainPageController {

    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();
    private final MusicFacade musicFacade = new MusicFacade();
    private final SearchFacade searchFacade = new SearchFacade();
    private PauseTransition pauseTransition;


    @FXML
    private VBox TopRightPane;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private ListView<String> musicsListView;
    @FXML
    private ListView<String> artistsListView;
    @FXML
    private Label musicResultLabel;
    @FXML
    private Label artistResultLabel;
    @FXML
    private Label currentMusicLabel;
    @FXML
    private Button playPauseButton;

    @FXML
    private HBox hBox;

    @FXML
    private Slider progressBar;

    @FXML
    private Label timerLabel;

    @FXML
    private Slider volumeSlider;
    public void initialize() {
        // Initialize the pause transition with a 500ms delay
        pauseTransition = new PauseTransition(Duration.millis(500));
        pauseTransition.setOnFinished(e -> handleSearch());

        // Create the event handler for key release
        searchField.setOnKeyReleased(e -> pauseTransition.playFromStart());

        filterComboBox.setOnAction(e -> useFilterMusic(convertToFilterOption(filterComboBox.getValue())));

        musicsListView.setOnMouseClicked(event -> {
            String selectedMusic = musicsListView.getSelectionModel().getSelectedItem();
            if (selectedMusic != null) {
                musicFacade.loadMusicByTitle(selectedMusic);
                musicFacade.playMusic();
                updateCurrentMusicLabel(selectedMusic);
                updatePlayPauseButton();
            }
        });

        playPauseButton.setOnAction(e -> {
            if (musicFacade.getMediaPlayer() != null) {
                if (musicFacade.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                    musicFacade.pauseMusic();
                    playPauseButton.setText("Play");
                } else {
                    musicFacade.playMusic();
                    playPauseButton.setText("Pause");
                }
            }
        });

        updateLoginButton();
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

        for (Music music : searchResults) {
            String formattedResult = music.getTitle();
            formattedResults.add(formattedResult);
        }

        if (!searchResults.isEmpty()) {
            musicsListView.getItems().setAll(formattedResults);
            musicResultLabel.setText("Music Results: " + searchResults.size() + " found");
        } else {
            musicsListView.getItems().clear();
            musicResultLabel.setText("Music Results: No matches found");
        }

        useFilterMusic(convertToFilterOption(filterComboBox.getValue()));
    }

    private void updateArtistResults() {
        ArrayList<Artist> searchResults = searchFacade.getCurrentArtistSearch();
        ArrayList<String> formattedResults = new ArrayList<>();

        for (Artist artist : searchResults) {
            String formattedResult = artist.getUsername();
            formattedResults.add(formattedResult);
        }

        if (!searchResults.isEmpty()) {
            artistsListView.getItems().setAll(formattedResults);
            artistResultLabel.setText("Artist Results: " + searchResults.size() + " found");
        } else {
            artistsListView.getItems().clear();
            artistResultLabel.setText("Artist Results: No matches found");
        }
    }

    private FilterOption convertToFilterOption(String filter) {
        return switch (filter) {
            case "Time ↓" -> FilterOption.Newest;
            case "Time ↑" -> FilterOption.Oldest;
            case "Streams count ↑" -> FilterOption.LeastListened;
            default -> FilterOption.MostListened;
        };
    }

    private void updateCurrentMusicLabel(String musicTitle) {
        currentMusicLabel.setText("Playing: " + musicTitle);
    }

    private void updatePlayPauseButton() {
        if (musicFacade.getMediaPlayer() != null && musicFacade.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
            playPauseButton.setText("Pause");
        } else {
            playPauseButton.setText("Play");
        }
    }

    private void useFilterMusic(FilterOption filter) {
        ArrayList<Music> searchResults = searchFacade.getCurrentMusicSearch();

        if (searchResults == null || searchResults.isEmpty()) {
            musicsListView.getItems().clear();
            musicResultLabel.setText("Music Results: No matches found");
            return;
        }

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

        ArrayList<String> formattedResults = new ArrayList<>();
        for (Music music : searchResults) {
            String formattedResult = music.getTitle();
            formattedResults.add(formattedResult);
        }

        musicsListView.getItems().setAll(formattedResults);
        musicResultLabel.setText("Music Results: " + searchResults.size() + " found");
    }

    private void updateLoginButton() {
        if (UserSessionFacade.getCurrentUser() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/LogoutButton.fxml"));
                hBox.getChildren().add(loader.load());

                Button Upload = new Button("Upload Music");
                Upload.setOnAction(event -> {
                    try {
                        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/waveon/waveon/UploadMusic.fxml"));
                        Parent root = loader2.load();
                        Stage stage = (Stage) hBox.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                hBox.getChildren().add(Upload);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathLoginButton.fxml"));
                hBox.getChildren().add(loader.load());

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/PathRegisterButton.fxml"));
                hBox.getChildren().add(loader2.load());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    private void togglePlayPause() {
        if (musicFacade.getMediaPlayer() != null && musicFacade.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
            musicFacade.pauseMusic();
            playPauseButton.setText("Play Music");
        } else {
            musicFacade.playMusic();
            playPauseButton.setText("Pause Music");
            updateProgressBar();
        }
        updateCurrentMusicLabel();
        updateProgressBar();
    }

    @FXML
    private void skipMusic() {
        musicFacade.skipMusic();
        updateProgressBar();
        updateCurrentMusicLabel();
    }

    @FXML
    private void restartMusic() {
        musicFacade.restartMusic();
    }

    private void updateProgressBar() {
        if (musicFacade != null && musicFacade.getMediaPlayer() != null) {
            Duration totalDuration = musicFacade.getTotalDuration();
            progressBar.setMax(totalDuration.toSeconds());

            musicFacade.getMediaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                progressBar.setValue(newValue.toSeconds());
                updateTimerLabel(newValue, totalDuration);
            });
        }
    }

    private void updateTimerLabel(Duration currentTime, Duration totalDuration) {
        String currentTimeStr = formatDuration(currentTime);
        String totalDurationStr = formatDuration(totalDuration);
        timerLabel.setText(currentTimeStr + "/" + totalDurationStr);
    }

    private String formatDuration(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateCurrentMusicLabel() {
        Music currentMusic = musicFacade.getCurrentMusic();
        if (currentMusic != null) {
            currentMusicLabel.setText("Now playing: " + currentMusic.getTitle());
        } else {
            currentMusicLabel.setText("No music playing");
        }
    }
}