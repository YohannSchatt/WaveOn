package waveon.waveon.persist;

import waveon.waveon.core.Music;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface MusicDAO {
    Connection connection = null;


    Music getMusicById(int id);

    ArrayList<Music> getMusicsByArtist(int artistId);

    ArrayList<Music> getAllMusics();

    ArrayList<Music> getMusicsByName( String name);

    //public abstract void createMusic( File file, String name, File image);

    void deleteMusic(String id);

    void modifyMusic( String id, Music musicInfo);

    boolean saveMusic(Music music);

    ArrayList<Music> getListMusicsByidArtist(int id);

    Music getMusicWithContentById(int musicId);

    int getLastId();

    void incrementPlayCount(int id);
}