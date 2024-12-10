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
        this.connection = null;
    }

    /**
     *
     */
    public Connection connection;

    /**
     * @param email
     * @return
     */
    public Artist getArtistByEmail(String email) {
        // TODO implement here
        return null;
    }
}