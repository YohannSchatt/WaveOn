package waveon.waveon.ui;

// JavaFX imports
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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

//components imports

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainPageController {

    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();
    private final MusicFacade musicFacade = new MusicFacade();
    private final SearchFacade searchFacade = new SearchFacade();
    private PauseTransition pauseTransition;
    private List<Music> searchResults;
    private Music selectedMusic;

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
    private MenuButton addToPlaylistMenu;
    @FXML
    private MenuButton playlistMenuButton;


    public void initialize() {
        updateProgressBar();
        filterComboBox.setOnAction(event -> applyFilter());
        pauseTransition = new PauseTransition(Duration.seconds(0.5));
        pauseTransition.setOnFinished(event -> handleSearch());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> pauseTransition.playFromStart());
        musicsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMusic = getMusicByName(newValue);  // Assurez-vous de récupérer l'objet Music à partir de son nom
            playSelectedMusic(newValue);  // Jouer la musique si nécessaire
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
        addToPlaylistMenu();
        setupPlaylistMenu();
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
        if (!search.isEmpty()) {
            searchFacade.searchMusic(search);
            searchFacade.searchArtist(search);
            updateMusicResults();
            updateArtistResults();
        } else {
            musicsListView.getItems().clear();
            artistsListView.getItems().clear();
            musicResultLabel.setText("Music Results: ");
            artistResultLabel.setText("Artist Results: ");
        }

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

    private void playSelectedMusic(String musicTitle) {
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
                // Si l'utilisateur est un artiste
                IUser currentUser = UserSessionFacade.getCurrentUser();
                if (currentUser.isArtist()) {
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

    @FXML
    private void addToPlaylistMenu() {
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
                    setupPlaylistMenu();
                } else {
                    currentMusicLabel.setText("Erreur lors de l'ajout de la musique à " + playlist.getName());
                }
            });
            addToPlaylistMenu.getItems().add(playlistItem);
        }
    }


    public void handleCreatePlaylist(javafx.event.ActionEvent actionEvent) {
        // Création du champ de texte pour le nom de la playlist
        TextField playlistNameField = new TextField();
        playlistNameField.setPromptText("Enter Playlist Name");

        // Création d'une boîte de dialogue personnalisée
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText("Enter a name for your new playlist:");

        // Définir un bouton "Save" personnalisé
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String playlistName = playlistNameField.getText();
            int userId = UserSessionFacade.getCurrentUser().getId();
            if (playlistName != null && !playlistName.isEmpty() && userId >= 0) {
                // Utilisation de MusicFacade pour créer la playlist et la sauvegarder dans la base de données
                boolean isCreated = musicFacade.createPlaylist(playlistName, userId);
                if (isCreated) {
                    System.out.println("Playlist saved: " + playlistName);
                    // Mettre à jour le menu "Add to Playlist" avec la nouvelle playlist
                    addToPlaylistMenu();
                    setupPlaylistMenu();
                } else {
                    System.out.println("Failed to save playlist.");
                }
            }
            dialog.close(); // Fermer la fenêtre de dialogue après la sauvegarde
        });

        // Créer un bouton "Cancel"
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            System.out.println("Playlist creation canceled.");
            dialog.close(); // Fermer la fenêtre de dialogue après annulation
        });

        // Ajouter des boutons à la boîte de dialogue
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // Ajouter le champ de texte et les boutons dans un VBox
        VBox vbox = new VBox(10, playlistNameField, saveButton, cancelButton);
        vbox.setSpacing(10);

        // Définir le contenu du dialogue
        dialog.getDialogPane().setContent(vbox);

        // Afficher la boîte de dialogue
        dialog.showAndWait();
    }

    private Music getMusicByName(String musicName) {
        for (Music music : musicFacade.getAllMusic()) {
            if (music.getTitle().equals(musicName)) {
                return music;
            }
        }
        return null;  // Retourne null si aucune musique n'a été trouvée
    }

    @FXML
    private void setupPlaylistMenu() {
        IUser currentUser = loginFacade.getCurrentUser();
        if (currentUser == null) {
            System.err.println("Error: No user is logged in. Cannot populate playlists.");
            return;
        }

        // Clear existing items in the menu
        playlistMenuButton.getItems().clear();

        // Fetch playlists for the current user
        List<Playlist> playlists = musicFacade.getPlaylistsByUserId(currentUser.getId());

        // Create menu items for each playlist
        for (Playlist playlist : playlists) {
            Menu playlistSubMenu = new Menu(playlist.getName());
            List<Music> musics = musicFacade.getMusicByPlaylistId(playlist.getId());

            // Add music items to the playlist submenu
            for (Music music : musics) {
                MenuItem musicItem = new MenuItem(music.getTitle());
                musicItem.setOnAction(event -> {
                    System.out.println("Selected music: " + music.getTitle());
                    playSelectedMusic(music.getTitle());
                });
                playlistSubMenu.getItems().add(musicItem);
            }

            // Add the playlist submenu to the main menu
            playlistMenuButton.getItems().add(playlistSubMenu);
        }
    }

}