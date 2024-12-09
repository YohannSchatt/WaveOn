package waveon.waveon;

import java.io.*;
import java.sql.Connection;
import java.util.*;

/**
 * 
 */
public class ArtistDAOPG implements ArtistDAO {

    /**
     * Default constructor
     */
    public ArtistDAOPG() {
    }

    /**
     * 
     */
    public Connection connection;

    /**
     * @param int id
     * @return
     */
    public Artist getArtistById(int id) {
        // TODO implement here
        return null;
    }

    /**
     * @param int id
     */
    public abstract void getArtistById(int id);

}