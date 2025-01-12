package waveon.waveon.persist;

import waveon.waveon.core.Artist;
import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.util.ArrayList;


public interface ArtistDAO {

     Connection connection = null;

     Artist getArtistByEmail(String email);

     Artist getArtistById(int id);

     ArrayList<Artist> getArtistsByName(String name);

     ArrayList<Artist> getAllArtists();

     ArrayList<OrdUser> getSubscribers(int id);

     Artist getArtistByMusicId(int musicId);

     Artist getAllInfoArtistById(int id);

     void updateArtist(int id, String username, String email, String password);

     void addArtist(String email, String username, String password);
}