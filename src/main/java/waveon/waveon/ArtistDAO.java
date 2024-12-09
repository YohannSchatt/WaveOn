package waveon.waveon;

import java.io.*;
import java.util.*;
import java.sql.Connection;

/**
 * 
 */
public interface ArtistDAO {

    /**
     * 
     */
    public Connection connection = null;

    /**
     * @param int id
     */
    public abstract Artist getArtistById( int id);

}