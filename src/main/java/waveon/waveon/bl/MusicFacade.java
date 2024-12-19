 package waveon.waveon.bl;

import javafx.util.Duration;
import waveon.waveon.core.Music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class MusicFacade {
    private Music music;

    public MusicFacade(Music music) {
        this.music = music;
    }

    public void playMusic() {
        music.play();
    }

    public void stopMusic() {
        music.stop();
    }

    public Duration getCurrentTime() {
        return music.getCurrentTime();
    }

    public Duration getTotalDuration() {
        return music.getTotalDuration();
    }

    public MediaPlayer getMediaPlayer() {
        return music.getMediaPlayer();
    }
}