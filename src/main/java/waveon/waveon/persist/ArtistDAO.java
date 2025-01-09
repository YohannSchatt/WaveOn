package waveon.waveon.persist;

import waveon.waveon.core.Artist;
import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public interface ArtistDAO {

    public Connection connection = null;

     abstract Artist getArtistByEmail(String email);

     abstract Artist getArtistById(int id);

     abstract  ArrayList<Artist> getArtistsByName(String name);

     abstract ArrayList<Artist> getAllArtists();

     abstract Artist getArtistByMusicId(int musicId);

     abstract public Artist getAllInfoArtistById(int id);

    void updateArtist(int id, String username, String email, String password);

    List<OrdUser> getSubscribers(int id);
}