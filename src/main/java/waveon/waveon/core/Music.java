// File: src/main/java/waveon/waveon/core/Music.java
package waveon.waveon.core;

import java.io.File;
import java.sql.Time;

public class Music {

    private int id;
    private String name;
    private Artist artist;
    private Time duration;
    private File content;

    public Music() {
    }

    public Music(int id, String name, Artist artist, Time duration, File content) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Artist getArtist() {
        return artist;
    }

    public Time getDuration() {
        return duration;
    }

    public File getContent() {
        return content;
    }
}