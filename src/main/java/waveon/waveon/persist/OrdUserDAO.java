package waveon.waveon.persist;

import waveon.waveon.core.User;

import java.sql.Connection;

/**
 * 
 */
public interface OrdUserDAO {

    /**
     * 
     */
    public Connection connection = null;

    /**
     * @param int id
     * @return
     */
    public abstract User getUserById(int id);

}