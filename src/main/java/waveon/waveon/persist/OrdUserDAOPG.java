package waveon.waveon.persist;

import waveon.waveon.core.OrdUser;
import waveon.waveon.connector.PGconnector;

import java.sql.*;

public class OrdUserDAOPG implements OrdUserDAO {

    /**
     * Default constructor
     */
    public OrdUserDAOPG() {
    }

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
}