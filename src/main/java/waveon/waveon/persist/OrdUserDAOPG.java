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
    }

    /**
     * 
     */
    public Connection connection;

    /**
     * @param int id
     * @return
     */
    public User getUserById(int id) {
        // TODO implement here
        return null;
    }
}