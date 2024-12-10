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
     * @param int id
     */
    public abstract Artist getArtistById(int id);

}