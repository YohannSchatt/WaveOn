package waveon.waveon.persist;

import waveon.waveon.core.Music;

import java.sql.Connection;
import java.util.ArrayList;

public interface MusicDAO {
     ArrayList<Music> getAllMusic();


    Connection connection = null;


    Music getMusicById( String id);

    ArrayList<Music> getMusicsByArtist(String artist);

    ArrayList<Music> getAllMusics();

    ArrayList<Music> getMusicsByName( String name);

    //public abstract void createMusic( File file, String name, File image);

    void deleteMusic(String id);

    void modifyMusic( String id, Music musicInfo);

    boolean saveMusic(Music music);
}