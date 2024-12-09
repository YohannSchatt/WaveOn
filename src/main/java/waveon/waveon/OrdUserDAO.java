package waveon.waveon;

import java.io.*;
import java.sql.Connection;
import java.util.*;

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