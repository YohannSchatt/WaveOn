// File: src/main/java/waveon/waveon/core/Music.java
package waveon.waveon.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.sql.Time;

public class Music {

    private int id;
    private String name;
    private Artist artist;
    private Time duration;
    private File content;
    private MediaPlayer mediaPlayer;

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

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(content.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public Duration getCurrentTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentTime();
        }
        return Duration.ZERO;
    }

    public Duration getTotalDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getTotalDuration();
        }
        return Duration.ZERO;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}