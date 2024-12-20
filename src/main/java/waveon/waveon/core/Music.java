package waveon.waveon.core;

import java.io.*;
import java.sql.Time;
import java.util.Date;

public class Music {

    public Music( int id, String title, int artist_id, String artist_name, Date date, int stream_count){//Time duration, File content) {
        this.id = id;
        this.name = title;
        this.artist_id = artist_id;
        this.artist_name = artist_name;
        this.date = date;
        //this.Duration = duration;
        this.stream_count = stream_count;

    }

    private int id;

    private String name;

    private int artist_id;

    private String artist_name;

    private Time Duration;

    private File Content;

    private Date date;

    private int stream_count;

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

    public Date getDate() {return date;}

    public int getStream_count() {return stream_count;}

    public void AddToQueue() {
        // TODO implement here
    }

}