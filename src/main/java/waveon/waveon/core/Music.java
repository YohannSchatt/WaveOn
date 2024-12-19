package waveon.waveon.core;

import java.io.*;
import java.sql.Time;

/**
 * 
 */
public class Music {

    /**
     * Default constructor
     */
    public Music( int id, String title, String artist_id){//Time duration, File content) {
        this.id = id;
        this.name = title;
        this.artist_id = artist_id;
    }


    private int id;


    private String name;


    private String artist_id;

    private Time Duration;

    private File Content;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public Time getDuration() {
        return Duration;
    }

    public File getContent() {
        return Content;
    }

    public void AddToQueue() {
        // TODO implement here
    }

}