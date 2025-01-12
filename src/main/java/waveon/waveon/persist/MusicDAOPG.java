package waveon.waveon.persist;

import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MusicDAOPG implements MusicDAO {

    public boolean saveMusic(Music music) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO music (title, file_content, cover_image, artist_id, artist_name, release_date, stream_count) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, music.getTitle());
            pstmt.setBytes(2, music.getFileContent()); // Insertion du contenu binaire
            pstmt.setBytes(3, music.getCoverMusic()); // Insertion du contenu binaire
            pstmt.setInt(4, music.getArtistId());
            pstmt.setString(5, music.getArtistName());
            pstmt.setDate(6, music.getReleaseDate());
            pstmt.setInt(7, music.getStreamCount());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in MusicDAOPG.saveMusic: " + e);
            return false;
        }
    }

    public Music getMusicById(String id) {
        System.out.println("SELECT * FROM music WHERE id = ?" + id);
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM music WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Music(
                        rs.getInt("id"),
                        rs.getString("title"),
                        null,null,
                        rs.getInt("artist_id"),
                        rs.getString("artistName"),
                        rs.getDate("releaseDate"),
                        rs.getInt("streamCount"));

            }
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getMusicById : " + e);
        }
        return null;
    }

    public ArrayList<Music> getListMusicsByidArtist(int id) {
        System.out.println("SELECT * FROM music WHERE artist_id = ?" + id);
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM music WHERE artist_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Music> musics = new ArrayList<>();
            while (rs.next()) {
                musics.add(new Music(rs.getInt("id"), rs.getString("title"), rs.getInt("artist_id")));
            }
            return musics;
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getListMusicsByidArtist : " + e);
        }
        return null;
    }

    public ArrayList<Music> getMusicsByName(String name) {
        System.out.println("SELECT id,title,artist_id,artist_name,release_date,stream_count FROM music WHERE LOWER(title) LIKE LOWER(?)" + name);
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT id,title,artist_id,artist_name,release_date,stream_count FROM music WHERE LOWER(title) LIKE LOWER(?)"; // Corrigé ici
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Ajoutez les % pour faire une recherche par sous-chaîne
            pstmt.setString(1, "%" + name.toLowerCase() + "%");

            ResultSet rs = pstmt.executeQuery();
            // Récupérer les musiques dans une liste
            ArrayList<Music> musics = new ArrayList<>();
            while (rs.next()) {
                Music music = new Music(
                        rs.getInt("id"),
                        rs.getString("title"),
                        null,null,
                        rs.getInt("artist_id"),
                        rs.getString("artist_name"),
                        rs.getDate("release_date"),
                        rs.getInt("stream_count"));

                musics.add(music);
            }
            return musics;
        } catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getMusicsByName: " + e);
        }
        return null;
    }

    @Override
    public void deleteMusic(String id) { }

    @Override
    public void modifyMusic(String id, Music musicInfo){}


    public ArrayList<Music> getMusicsByArtist(String artist) {
        System.out.println("SELECT id,title,artist_id,artist_name,release_date,stream_count FROM music WHERE artist_id = ?" + artist);
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT id,title,artist_id,artist_name,release_date,stream_count FROM music WHERE artist_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, artist);
            ResultSet rs = pstmt.executeQuery();
            // recuperer les musiques dans une liste
            ArrayList<Music> musics = new ArrayList<>();
            while (rs.next()) {
                Music music = new Music(
                        rs.getInt("id"),
                        rs.getString("title"),
                        null,null,
                        rs.getInt("artist_id"),
                        rs.getString("artist_name"),
                        rs.getDate("release_date"),
                        rs.getInt("stream_count"));

                musics.add(music);
            }
            return musics;
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getMusicsByArtist : " + e);
        }
        return null;
    }

    public ArrayList<Music> getAllMusics() {
        System.out.println("SELECT id,title,artist_id,artist_name,release_date,stream_count FROM music");
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT id,title,artist_id,artist_name,release_date,stream_count FROM music";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            // recuperer les musiques dans une liste
            ArrayList<Music> musics = new ArrayList<>();
            while (rs.next()) {
                Music music = new Music(
                        rs.getInt("id"),
                        rs.getString("title"),
                        null,null,
                        rs.getInt("artist_id"),
                        rs.getString("artist_name"),
                        rs.getDate("release_date"),
                        rs.getInt("stream_count"));


                musics.add(music);
            }
            return musics;
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getAllMusics : " + e);
        }
        return null;
    }

    public static int getLastId() {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT MAX(id) FROM music";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getLastId : " + e);
        }
        return 0;
    }

    public Music getMusicWithContentById(int id) {
        System.out.println("CONTENT -------- SELECT * FROM music WHERE id = ?" + id);
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM music WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Music(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getBytes("file_content"),
                        rs.getBytes("cover_image"),
                        rs.getInt("artist_id"),
                        rs.getString("artist_name"),
                        rs.getDate("release_date"),
                        rs.getInt("stream_count"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getMusicWithContentById : " + e);
        }


        return null;
    }


}

