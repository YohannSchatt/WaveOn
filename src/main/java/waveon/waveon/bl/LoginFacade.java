package waveon.waveon.bl;

//import waveon.waveon.core.User;

import java.sql.*;

/**
 * 
 */
public class LoginFacade {


    private static final String URL = "jdbc:postgresql://cluster-ig4.igpolytech.fr:30017/waveon_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "86ff479c8e85cc70f0c7aa5868e0c04d";

    /**
     * Default constructor
     */
    public LoginFacade() {
    }


    public boolean login(String username, String password) {
        String sql = "SELECT * FROM ordinaryuser WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            return false;
        }
    }
}