package waveon.waveon.persist;

import waveon.waveon.core.Artist;

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
     * @param String id
     */
    public abstract Artist getArtistByEmail(String email);

}