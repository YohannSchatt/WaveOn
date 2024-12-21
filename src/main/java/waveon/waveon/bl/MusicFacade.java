// File: src/main/java/waveon/waveon/bl/MusicFacade.java
package waveon.waveon.bl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.core.Music;

public class MusicFacade {
    private Music music;
    private MediaPlayer mediaPlayer;

    public MusicFacade(Music music) {
        this.music = music;
        initializeMediaPlayer();
    }

    private void initializeMediaPlayer() {
        if (music.getContent() != null) {
            Media media = new Media(music.getContent().toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        }
    }

    public void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void skipMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
        }
    }

    public void restartMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
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