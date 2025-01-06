package waveon.waveon.persist;

import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Artist;
import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ArtistDAOPG implements ArtistDAO {

    public ArtistDAOPG() {
    }

    public Connection connection;

    public Artist getArtistByEmail(String email) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM artist WHERE email = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Artist artist = new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
                return artist;
            }
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.getUserByEmail : " + e);
        }
        return null;
    }

    public Artist getArtistById(int id) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM artist WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getUserByEmail : " + e);
        }
        return null;
    }

    @Override
    public ArrayList<Artist> getArtistsByName(String name) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM artist WHERE LOWER(username) LIKE LOWER(?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Artist> artists = new ArrayList<Artist>();
            while (rs.next()) {
                artists.add(new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
            }
            return artists;
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getArtistsByName : " + e);
        }
        return null;
    }

    public ArrayList<Artist> getAllArtists() {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM artist";
        ArrayList<Artist> artists = new ArrayList<Artist>();
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                artists.add(new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
            }
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getUserByEmail : " + e);
        }
        return artists;
    }

    @Override
    public Artist getArtistByMusicId(int musicId) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM artist WHERE id = (SELECT artist_id FROM music WHERE id = ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, musicId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in ArtistDAOPG.getArtistByMusicId : " + e);
        }
        return null;
    }


}