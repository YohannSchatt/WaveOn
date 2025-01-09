package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.bl.UserSessionFacade;
import waveon.waveon.core.Playlist;
import waveon.waveon.core.Music;
import waveon.waveon.persist.PlaylistDAO;
import waveon.waveon.persist.PlaylistDAOPG;

import java.util.List;

public class PlaylistController {

    private final PlaylistDAO playlistDAO = new PlaylistDAOPG();
    private final UserSessionFacade currentUser = UserSessionFacade.getInstance();

    @FXML
    private ListView<Playlist> playlistsListView;
    @FXML
    private Button addPlaylistButton;
    @FXML
    private Button addMusicToPlaylistButton;
    @FXML
    private VBox mainVBox;

    public void initialize() {
        mainVBox.setPadding(new Insets(20));
        // Logique de création de playlist
        addPlaylistButton.setOnAction(event -> {
            // Implémentation de la création d'une playlist
            String playlistName = "New Playlist";
            int userId = currentUser.getCurrentUser().getId();
            playlistDAO.createPlaylist(playlistName, userId);
        });


        // Ajouter une musique à la playlist
        addMusicToPlaylistButton.setOnAction(event -> {
            Playlist selectedPlaylist = playlistsListView.getSelectionModel().getSelectedItem();
        });
    }
}

