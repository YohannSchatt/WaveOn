// File: src/main/java/waveon/waveon/core/Music.java
package waveon.waveon.core;

import java.sql.Time;

public class Music {
    private final int id;
    private final String name;
    private final Artist artist;
    private final Time duration;
    private final byte[] content;

    public Music(int id, String name, Artist artist, Time duration, byte[] content) {
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

    public byte[] getContent() {
        return content;
    }
}