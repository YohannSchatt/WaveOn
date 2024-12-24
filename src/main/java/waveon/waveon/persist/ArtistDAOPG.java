package waveon.waveon.persist;

import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Artist;
import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 */
public class ArtistDAOPG implements ArtistDAO {

    /**
     * Default constructor
     */
    public ArtistDAOPG() {
    }

    /**
     *
     */
    public Connection connection;

    /**
     * @param email
     * @return
     */
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
                Artist artist = new Artist(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
                return artist;
            }
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.getUserByEmail : " + e);
        }
        return null;
    }
}