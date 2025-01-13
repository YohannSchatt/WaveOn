package waveon.waveon.persist;

import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdUserDAO {

    public Connection connection = null;

    public abstract OrdUser getUserByEmail(String email);

    void addUser(String username, String email, String password) throws SQLException;

    void updateUser(int id, String username, String email, String password);


    void addFollow(int id, int idToFollow);

    void removeFollow(int id, int idToUnfollow);

    ArrayList<OrdUser> getUserByName(String name);
}