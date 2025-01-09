package waveon.waveon.persist;

import waveon.waveon.core.Playlist;
import waveon.waveon.core.Music;
import waveon.waveon.connector.PGconnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOPG implements PlaylistDAO {

    @Override
    public boolean createPlaylist(String name, int userId) {
        String sql = "INSERT INTO playlist (name, userId) VALUES (?, ?)";
        try (Connection conn = PGconnector.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in PlaylistDAOPG.createPlaylist: " + e);
            return false;
        }
    }

    @Override
    public boolean addMusicToPlaylist(int playlistId, int musicId) {
        String sql = "INSERT INTO playlist_music (playlist_id, music_id) VALUES (?, ?)";
        try (Connection conn = PGconnector.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playlistId);
            pstmt.setInt(2, musicId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in PlaylistDAOPG.addMusicToPlaylist: " + e);
            return false;
        }
    }

    @Override
    public List<Playlist> getPlaylistsByUserId(int userId) {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM playlist WHERE userId = ?";
        try (Connection conn = PGconnector.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Playlist playlist = new Playlist(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("userId"));
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            System.out.println("Error in PlaylistDAOPG.getPlaylistsByUserId: " + e);
        }
        return playlists;
}
    }
