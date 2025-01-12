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

    public boolean addMusicToPlaylist(int musicId, int playlistId) {
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

    @Override
    public List<Music> getMusicByPlaylistId(int playlistId) throws SQLException {
        String query = """
            SELECT m.id, m.title, m.file_content, m.cover_image, m.artist_id, a.username AS artist_name, 
                   m.release_date, m.stream_count
            FROM music m
            JOIN playlist_music pm ON m.id = pm.music_id
            JOIN artist a ON m.artist_id = a.id
            WHERE pm.playlist_id = ?
        """;
        try (Connection conn = PGconnector.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playlistId);
            ResultSet rs = pstmt.executeQuery();
            List<Music> musics = new ArrayList<>();
            while (rs.next()) {
                Music music = new Music(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getBytes("file_content"),
                        rs.getBytes("cover_image"),
                        rs.getInt("artist_id"),
                        rs.getString("artist_name"),
                        rs.getDate("release_date"),
                        rs.getInt("stream_count"));
                musics.add(music);
            }
            return musics;
        }
    }

    @Override
    public boolean deletePlaylist(int playlistId) {
        String sql = "DELETE FROM playlist WHERE id = ?";
        try (Connection conn = PGconnector.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playlistId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in PlaylistDAOPG.deletePlaylist: " + e);
            return false;
        }
    }

    @Override
    public boolean deleteMusicFromPlaylist(int playlistId, int musicId) {
        String sql = "DELETE FROM playlist_music WHERE playlist_id = ? AND music_id = ?";
        try (Connection conn = PGconnector.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playlistId);
            pstmt.setInt(2, musicId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in PlaylistDAOPG.deleteMusicFromPlaylist: " + e);
            return false;
        }
    }
}
