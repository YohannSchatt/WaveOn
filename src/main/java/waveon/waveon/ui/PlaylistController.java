package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.core.Music;
import waveon.waveon.core.Playlist;
import waveon.waveon.bl.UserSessionFacade;
import waveon.waveon.core.IUser;

import java.util.List;

public class PlaylistController {

    private final MusicFacade musicFacade = new MusicFacade();
    private final UserSessionFacade loginFacade = UserSessionFacade.getInstance();
    private Music selectedMusic;

    @FXML
    private ListView<String> playlistListView;

    public void initialize() {
        IUser currentUser = loginFacade.getCurrentUser();
        if (currentUser != null) {
            // Populate the playlist list view with the user's playlists
            List<Playlist> playlists = musicFacade.getPlaylistsByUserId(currentUser.getId());
            for (Playlist playlist : playlists) {
                playlistListView.getItems().add(playlist.getName());
            }
        }
    }

    @FXML
    public void addToPlaylist() {
        IUser currentUser = loginFacade.getCurrentUser();
        if (currentUser == null || selectedMusic == null) {
            System.out.println("Error: No user or music selected.");
            return;
        }

        String selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {
            Playlist playlist = getPlaylistByName(selectedPlaylist, currentUser.getId());
            if (playlist != null) {
                boolean success = musicFacade.addMusicToPlaylist(selectedMusic, playlist);
                if (success) {
                    System.out.println("Music added to playlist: " + playlist.getName());
                } else {
                    System.out.println("Failed to add music to playlist.");
                }
            }
        }
    }

    private Playlist getPlaylistByName(String name, int userId) {
        List<Playlist> playlists = musicFacade.getPlaylistsByUserId(userId);
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)) {
                return playlist;
            }
        }
        return null;
    }

    public void setSelectedMusic(Music selectedMusic) {
        this.selectedMusic = selectedMusic;
    }
}
