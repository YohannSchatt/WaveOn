package waveon.waveon.persist;

import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrdUserDAO {

    public Connection connection = null;

    /**
     * @param email
     * @return OrduUser
     */
    public abstract OrdUser getUserByEmail(String email);

    void addUser(String username, String email, String password) throws SQLException;
}