package waveon.waveon.persist;


import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Music;
import waveon.waveon.core.OrdUser;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MusicDAOPG implements MusicDAO {


    public MusicDAOPG() {
    }


    //public Connection connection;

    public Music getMusicById(String id) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM music WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Music(rs.getInt("id"),rs.getString("title"), rs.getString("artist_id"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getMusicById : " + e);
        }
        return null;
    }

    public Music getMusicByName(String name) {
        // TODO implement here
        return null;
    }

    public ArrayList<Music> getMusicByArtist(String artist) {
        // TODO implement here
        return null;
    }

    public ArrayList<Music> getAllMusics() {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM music";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            // recuperer les musiques dans une liste
            ArrayList<Music> musics = new ArrayList<>();
            while (rs.next()) {
                Music music = new Music(rs.getInt("id"),rs.getString("title"), rs.getString("artist_id"));
                musics.add(music);
            }
            return musics;
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getAllMusics : " + e);
        }
        return null;
    }

    public void createMusic(File file, String name, File image) {
        // TODO implement here
    }

    public void deleteMusic(String id) {
        // TODO implement here
    }

    public void modifyMusic(String id, Music musicInfo) {
        // TODO implement here
    }

}