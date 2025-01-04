package waveon.waveon.connector;

import java.sql.*;

public class PGconnector {

    private static final String URL = "jdbc:postgresql://localhost:5432/waveon_db";//"jdbc:postgresql://cluster-ig4.igpolytech.fr:30011/waveon_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";//"86ff479c8e85cc70f0c7aa5868e0c04d";

    private static PGconnector instance = null;

    public PGconnector() {}

    public static PGconnector getInstance(){
        if (instance == null) {
            instance = new PGconnector();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
