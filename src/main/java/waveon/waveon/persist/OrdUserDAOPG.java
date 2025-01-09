package waveon.waveon.persist;

import waveon.waveon.core.OrdUser;
import waveon.waveon.connector.PGconnector;

import java.sql.*;

/**
 * 
 */
public class OrdUserDAOPG implements OrdUserDAO {

    /**
     * Default constructor
     */
    public OrdUserDAOPG() {}

    /**
     *
     */
    public Connection connection;

    /**
     * @param email
     * @return OrduUser
     */
    public OrdUser getUserByEmail(String email) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM ordinaryuser WHERE email = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new OrdUser(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.getUserByEmail : " + e);
        }
        return null;
    }

    public void addUser(String username,String email, String password) throws SQLException {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO ordinaryuser (email,username, password) VALUES (?, ?, ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        }
    }

    public void updateUser(int id, String username, String email, String password) {
        if (password == null || password.trim().isEmpty()) {
            updateWithOutPassword(id, username, email);
        } else {
            updateWithPassword(id, username, email, password);
        }

    }

    private void updateWithPassword(int id, String username, String email, String password) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "UPDATE ordinaryuser SET username = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.updateUser : " + e);
        }
    }

    private void updateWithOutPassword(int id, String username, String email) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "UPDATE ordinaryuser SET username = ?, email = ? WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.updateUser : " + e);
        }
    }

    public void addFollow(int id, int idUser) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO subscriber (artist_id, user_id) VALUES (?, ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, idUser);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.addFollow : " + e);
        }
    }

    public void removeFollow(int id, int idUser) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "DELETE FROM subscriber WHERE artist_id = ? AND user_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, idUser);
            pstmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in OrdUserDAOPG.removeFollow : " + e);
        }
    }
}