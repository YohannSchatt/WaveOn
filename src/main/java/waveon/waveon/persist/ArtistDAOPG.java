package waveon.waveon.persist;

import waveon.waveon.core.Artist;

import java.sql.Connection;

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
     * @param String id
     * @return
     */
    public Artist getArtistById(int id) {
        // TODO implement here
        return null;
    }
}