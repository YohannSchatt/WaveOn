package waveon.waveon.persist;

import waveon.waveon.core.OrdUser;

import java.sql.Connection;

/**
 * 
 */
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
     * @return
     */
    public OrdUser getUserByEmail(String email) {
        // TODO implement here
        return null;
    }
}