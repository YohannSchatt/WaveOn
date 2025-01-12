package waveon.waveon.ui;

// JavaFX imports
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import waveon.waveon.bl.*;
import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.bl.UserSessionFacade;
import waveon.waveon.core.IUser;
import waveon.waveon.core.Playlist;
import waveon.waveon.core.Notification;

import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainPageController {

    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();
    private final MusicFacade musicFacade = new MusicFacade();
    private final SearchFacade searchFacade = new SearchFacade();
    private final NotificationFacade notificationFacade = new NotificationFacade();
    private PauseTransition pauseTransition;
    private List<Music> searchResults;
    private Music selectedMusic;
    private final NotificationController notificationController = new NotificationController();

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
    @FXML
    private Button skipMusicButton;
    @FXML
    private Button restartMusicButton;
    @FXML
    private PlaylistController playlistController;
    @FXML
    private MenuButton addToPlaylistMenu;

    private Map<String, Integer> artistNameToIdMap = new HashMap<>();
    @FXML
    private VBox notificationBand;
    @FXML
    private Button toggleNotificationButton;
    @FXML
    private ListView<HBox> notificationListView;


    public void initialize() {
        updateProgressBar();
        System.out.println("filterComboBox initialzing");
        filterComboBox.setOnAction(event -> applyFilter());
        System.out.println("pauseTransition initialzing");
        pauseTransition = new PauseTransition(Duration.seconds(0.5));
        pauseTransition.setOnFinished(event -> handleSearch());
        System.out.println("searchField initialzing");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> pauseTransition.playFromStart());
        System.out.println("music listView initialzing");
        musicsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMusic = getMusicByName(newValue);  // Assurez-vous de récupérer l'objet Music à partir de son nom
            musicFacade.setCurrentMusic(selectedMusic);
        });
        System.out.println("searchField initialzing");
        artistsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int artistId = artistNameToIdMap.get(newValue);
                goToArtistPage(artistId);
            }
        });
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> musicFacade.setVolume(newValue.doubleValue()));
        skipMusicButton.setOnAction(event -> skipMusic());
        restartMusicButton.setOnAction(event -> restartMusic());
        // Add progress bar listener for value changes
        progressBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (progressBar.isValueChanging()) {
                musicFacade.seekMusic(Duration.seconds(newValue.doubleValue()));
            }
        });
        // Add progress bar listener for mouse click event
        progressBar.setOnMouseClicked(event -> {
            musicFacade.seekMusic(Duration.seconds(progressBar.getValue()));
        });
        updateLoginButton();
        if (playlistController != null) {
            playlistController.setMainPageController(this);
        }
        updateAddToPlaylistMenu();
        handleSearch();
    }


    @FXML
    private void toggleNotificationCenter() {
        if (Objects.equals(toggleNotificationButton.getText(), "Open Notifications")) {
            if (UserSessionFacade.getCurrentUser() == null) {
                return;
            }
            notificationBand.setVisible(true);
            loadNotifications();
            toggleNotificationButton.setText("Close Notifications");

        } else {
            notificationBand.setVisible(false);
            toggleNotificationButton.setText("Open Notifications");
        }
    }

    private void loadNotifications() {
        ArrayList<Notification> notifications = notificationFacade.getNotificationsList();
        notificationListView.getItems().clear();
        for (Notification notification : notifications) {
            HBox hBox = new HBox();
            Label label = new Label(notification.getContent());
            Button deleteButton = new Button("✖");
            deleteButton.setOnAction(event -> {
                notificationFacade.clearNotification(notification.getId());
                loadNotifications();
            });
            hBox.getChildren().addAll(label, deleteButton);
            notificationListView.getItems().add(hBox);
        }
    }

    private void applyFilter() {
        String selectedFilter = filterComboBox.getValue();
        if (selectedFilter != null && searchResults != null) {
            switch (selectedFilter) {
                case "Time ↓":
                    searchResults.sort(Comparator.comparing(Music::getReleaseDate).reversed());
                    break;
                case "Time ↑":
                    searchResults.sort(Comparator.comparing(Music::getReleaseDate));
                    break;
                case "Streams count ↑":
                    searchResults.sort(Comparator.comparingInt(Music::getStreamCount).reversed());
                    break;
                case "Streams count ↓":
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
    }

    private void handleSearch() {
        String search = searchField.getText();
        searchFacade.searchMusic(search);
        searchFacade.searchArtist(search);
        updateMusicResults();
        updateArtistResults();
    }

    private void updateMusicResults() {
        searchResults = searchFacade.getCurrentMusicSearch();
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

        String selectedFilter = filterComboBox.getValue();
        if (selectedFilter != null) {
            applyFilter();
        }

    }

    private void goToArtistPage(int idArtist) {
        System.out.println("Going to artist page with id: " + idArtist);
        try {
            if(searchFacade.getAllInfoArtistById(idArtist)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/profilePage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) hBox.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("Artist not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateArtistResults() {
        ArrayList<Artist> searchResults = searchFacade.getCurrentArtistSearch();
        ArrayList<String> formattedResults = new ArrayList<>();
        artistNameToIdMap.clear();

        for (Artist artist : searchResults) {
            String formattedResult = artist.getUsername();
            formattedResults.add(formattedResult);
            artistNameToIdMap.put(formattedResult, artist.getId());
        }

        if (!searchResults.isEmpty()) {
            artistsListView.getItems().setAll(formattedResults);
            artistResultLabel.setText("Artist Results: " + searchResults.size() + " found");
        } else {
            artistsListView.getItems().clear();
            artistResultLabel.setText("Artist Results: No matches found");
        }
    }

    void playSelectedMusic(String musicTitle) {
        //if (musicTitle != null) {
        musicFacade.loadMusicByTitle(musicTitle);
        musicFacade.playMusic();
        updateCurrentMusicLabel(musicTitle);
        updatePlayPauseButton();
        updateProgressBar();
        //}
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

    private void updateLoginButton() {
        if (UserSessionFacade.getCurrentUser() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/MyProfileButton.fxml"));
                hBox.getChildren().add(loader.load());

                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/CommentarySectionButton.fxml"));
                hBox.getChildren().add(loader2.load());
                // Si l'utilisateur est un artiste
                IUser currentUser = UserSessionFacade.getCurrentUser();
                if (currentUser.isArtist()) {
                    Button Upload = new Button("Upload Music");
                    Upload.setOnAction(event -> {
                        try {
                            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/waveon/waveon/UploadMusic.fxml"));
                            Parent root = loader3.load();
                            Stage stage = (Stage) hBox.getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    hBox.getChildren().add(Upload);
                    }
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
            System.out.println("Playing music" + musicFacade.getCurrentMusic().getTitle());
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


    private Music getMusicByName(String musicName) {
        for (Music music : musicFacade.getAllMusics()) {
            if (music.getTitle().equals(musicName)) {
                return music;
            }
        }
        return null;  // Retourne null si aucune musique n'a été trouvée
    }

    public void updateAddToPlaylistMenu() {
        IUser currentUser = loginFacade.getCurrentUser();
        if (currentUser == null) {
            System.err.println("Error: No user is logged in. Cannot populate playlists.");
            return;
        }
        // Clear existing items in the menu
        addToPlaylistMenu.getItems().clear();
        // Fetch playlists for the current user
        List<Playlist> playlists = musicFacade.getPlaylistsByUserId(currentUser.getId());
        // Create menu items for each playlist
        for (Playlist playlist : playlists) {
            MenuItem playlistItem = new MenuItem(playlist.getName());
            playlistItem.setOnAction(event -> {
                System.out.println("Selected playlist: " + playlist.getName());
                boolean success = musicFacade.addMusicToPlaylist(selectedMusic, playlist);  // add the music to the playlist
                if (success) {
                    currentMusicLabel.setText("Musique a été ajoutée à " + playlist.getName());
                    // Mettre à jour le menu des playlists dans le PlaylistController
                    if (playlistController != null) {
                        playlistController.setupPlaylistMenu();
                    }
                } else {
                    currentMusicLabel.setText("Erreur lors de l'ajout de la musique à " + playlist.getName());
                }
            });
            addToPlaylistMenu.getItems().add(playlistItem);
        }
    }
}