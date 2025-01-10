package waveon.waveon.persist;

import waveon.waveon.core.Artist;
import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.util.ArrayList;


public interface ArtistDAO {

    public Connection connection = null;

     abstract Artist getArtistByEmail(String email);

     abstract Artist getArtistById(int id);

     abstract  ArrayList<Artist> getArtistsByName(String name);

     abstract ArrayList<Artist> getAllArtists();

     ArrayList<OrdUser> getSubscribers(int id);

     abstract Artist getArtistByMusicId(int musicId);


}