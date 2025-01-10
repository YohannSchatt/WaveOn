package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.bl.UserSessionFacade;
import waveon.waveon.core.IUser;
import waveon.waveon.core.Music;
import waveon.waveon.core.Playlist;

import java.util.List;

public class PlaylistController {

    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();
    private final MusicFacade musicFacade = new MusicFacade();

    @FXML
    private MenuButton playlistMenuButton;

    // Référence vers le MainPageController pour accéder aux fonctions de lecture
    private MainPageController mainPageController;

    public void setMainPageController(MainPageController controller) {
        this.mainPageController = controller;
    }

    @FXML
    public void initialize() {
        setupPlaylistMenu();
    }

    @FXML
    public void handleCreatePlaylist() {
        TextField playlistNameField = new TextField();
        playlistNameField.setPromptText("Enter Playlist Name");

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Create Playlist");
        dialog.setHeaderText("Enter a name for your new playlist:");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String playlistName = playlistNameField.getText();
            int userId = UserSessionFacade.getCurrentUser().getId();
            if (playlistName != null && !playlistName.isEmpty() && userId >= 0) {
                boolean isCreated = musicFacade.createPlaylist(playlistName, userId);
                if (isCreated) {
                    System.out.println("Playlist saved: " + playlistName);
                    setupPlaylistMenu();
                    if (mainPageController != null) {
                        mainPageController.updateAddToPlaylistMenu();
                    }
                } else {
                    System.out.println("Failed to save playlist.");
                }
            }
            dialog.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            System.out.println("Playlist creation canceled.");
            dialog.close();
        });

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        VBox vbox = new VBox(10, playlistNameField, saveButton, cancelButton);
        vbox.setSpacing(10);

        dialog.getDialogPane().setContent(vbox);
        dialog.showAndWait();
    }

    public void setupPlaylistMenu() {
        IUser currentUser = loginFacade.getCurrentUser();
        if (currentUser == null) {
            System.err.println("Error: No user is logged in. Cannot populate playlists.");
            return;
        }

        playlistMenuButton.getItems().clear();

        List<Playlist> playlists = musicFacade.getPlaylistsByUserId(currentUser.getId());

        for (Playlist playlist : playlists) {
            Button playlistButton = new Button(playlist.getName());
            ContextMenu playlistContextMenu = new ContextMenu();

            MenuItem deletePlaylistItem = new MenuItem("Delete Playlist");
            deletePlaylistItem.setOnAction(event -> {
                deletePlaylist(playlist);
                playlistMenuButton.getItems().removeIf(item -> {
                    Button btn = (Button) item.getGraphic();
                    return btn != null && btn.getText().equals(playlist.getName());
                });
            });

            playlistContextMenu.getItems().add(deletePlaylistItem);

            Menu playlistSubMenu = new Menu("Songs");
            List<Music> songs = musicFacade.getMusicByPlaylistId(playlist.getId());

            for (Music music : songs) {
                Label musicLabel = new Label(music.getTitle());
                CustomMenuItem musicItem = new CustomMenuItem(musicLabel);
                musicItem.setHideOnClick(false);

                ContextMenu musicContextMenu = new ContextMenu();
                MenuItem deleteMusicItem = new MenuItem("Delete Music");
                deleteMusicItem.setOnAction(event -> {
                    deleteMusicFromPlaylist(playlist, music);
                    playlistSubMenu.getItems().remove(musicItem);
                });

                musicContextMenu.getItems().add(deleteMusicItem);

                musicLabel.setOnContextMenuRequested(event -> {
                    musicContextMenu.show(musicLabel, event.getScreenX(), event.getScreenY());
                    event.consume();
                });

                musicLabel.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY && mainPageController != null) {
                        mainPageController.playSelectedMusic(music.getTitle());
                    }
                });

                playlistSubMenu.getItems().add(musicItem);
            }

            playlistContextMenu.getItems().add(playlistSubMenu);

            playlistButton.setOnContextMenuRequested(event -> {
                playlistContextMenu.show(playlistButton, event.getScreenX(), event.getScreenY());
            });

            playlistButton.setOnAction(event -> {
                System.out.println("Selected playlist: " + playlist.getName());
            });

            MenuItem playlistItem = new MenuItem();
            playlistItem.setGraphic(playlistButton);
            playlistMenuButton.getItems().add(playlistItem);
        }
    }

    private void deletePlaylist(Playlist playlist) {
        if (playlist == null) {
            System.err.println("Error: Playlist is null.");
            return;
        }

        boolean isDeleted = musicFacade.deletePlaylist(playlist.getId());
        if (isDeleted) {
            System.out.println("Playlist deleted: " + playlist.getName());
            setupPlaylistMenu();
            if (mainPageController != null) {
                mainPageController.updateAddToPlaylistMenu();
            }
        } else {
            System.err.println("Failed to delete playlist: " + playlist.getName());
        }
    }

    private void deleteMusicFromPlaylist(Playlist playlist, Music music) {
        if (playlist == null || music == null) {
            System.err.println("Error: Playlist or music is null.");
            return;
        }

        boolean isDeleted = musicFacade.deleteMusicFromPlaylist(playlist.getId(), music.getId());
        if (isDeleted) {
            System.out.println("Music deleted from playlist: " + music.getTitle());
            setupPlaylistMenu();
        } else {
            System.err.println("Failed to delete music from playlist: " + music.getTitle());
        }
    }
}