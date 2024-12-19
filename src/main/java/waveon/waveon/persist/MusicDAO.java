package waveon.waveon.persist;

import waveon.waveon.core.Music;

import java.sql.Connection;
import java.util.ArrayList;

public interface MusicDAO {


    public Connection connection = null;

    public abstract Music getMusicById( String id);

    public abstract ArrayList<Music> getMusicByArtist( String artist);

    public abstract ArrayList<Music> getAllMusics();

    public abstract Music getMusicByName( String name);

    //public abstract void createMusic( File file, String name, File image);

    public abstract void deleteMusic(String id);

    public abstract void modifyMusic( String id, Music musicInfo);

}