package waveon.waveon.persist;

import waveon.waveon.core.OrdUser;

import java.sql.Connection;

public interface OrdUserDAO {

    public Connection connection = null;

    /**
     * @param email
     * @return OrduUser
     */
    public abstract OrdUser getUserByEmail(String email);

}