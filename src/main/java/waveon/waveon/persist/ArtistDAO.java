package waveon.waveon.persist;

import waveon.waveon.core.Artist;

import java.sql.Connection;
import java.util.ArrayList;


public interface ArtistDAO {

    public Connection connection = null;

     abstract Artist getArtistByEmail(String email);

     abstract Artist getArtistById(int id);

     abstract  ArrayList<Artist> getArtistsByName(String name);

     abstract ArrayList<Artist> getAllArtists();

     abstract Artist getArtistByMusicId(int musicId);


    void updateArtist(int id, String username, String email, String password);
}