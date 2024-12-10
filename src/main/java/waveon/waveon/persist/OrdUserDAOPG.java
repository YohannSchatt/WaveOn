package waveon.waveon.persist;

import waveon.waveon.core.User;

import java.sql.Connection;

/**
 * 
 */
public class OrdUserDAOPG implements OrdUserDAO {

    /**
     * Default constructor
     */
    public OrdUserDAOPG() {
        this.connection = null;
    }

    /**
     * 
     */
    public Connection connection;

    /**
     * @param int id
     * @return
     */
    public User getUserByEmail(String email) {
        // TODO implement here
        return null;
    }
}