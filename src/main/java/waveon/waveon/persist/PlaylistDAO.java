package waveon.waveon.persist;

import waveon.waveon.core.Playlist;
import waveon.waveon.core.Music;

import java.util.List;

public interface PlaylistDAO {

    // Créer une playlist
    boolean createPlaylist(String name, int userId);

    // Ajouter une musique à une playlist
    boolean addMusicToPlaylist(int playlistId, int musicId);

    // Récupérer toutes les playlists qui appartiennent à un utilisateur
    List<Playlist> getPlaylistsByUserId(int userId);
}
