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
    public Music( int id, String title, int artist_id, String artist_name){//Time duration, File content) {
        this.id = id;
        this.name = title;
        this.artist_id = artist_id;
        this.artist_name = artist_name;
    }


    private int id;


    private String name;


    private int artist_id;

    private String artist_name;

    private Time Duration;

    private File Content;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getArtist_id() { return artist_id; }

    public String getArtist_name() { return artist_name; }

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