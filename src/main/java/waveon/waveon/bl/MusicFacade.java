package waveon.waveon.bl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.core.Music;
import waveon.waveon.persist.MusicDAOPG;

import java.util.List;

public class MusicFacade {
    private List<Music> musicList;
    private MediaPlayer mediaPlayer;
    private MusicDAOPG musicDAOPG;
    private int currentMusicIndex = -1;
    private boolean isPaused = false;
    private double volume = 0.5; // Default volume

    public MusicFacade() {
        this.musicDAOPG = new MusicDAOPG();
        this.musicList = musicDAOPG.getAllMusic();
    }

    public void loadMusicByTitle(String title) {
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getName().equals(title)) {
                currentMusicIndex = i;
                initializeMediaPlayer(musicList.get(i));
                break;
            }
        }
    }

    private void initializeMediaPlayer(Music music) {
        if (music.getContent() != null) {
            Media media = new Media(music.getContent().toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volume); // Set the volume to the stored value
        }
    }

    public void playMusic() {
        if (mediaPlayer == null && !musicList.isEmpty()) {
            loadMusicByTitle(musicList.get(0).getName());
        }
        if (mediaPlayer != null) {
            mediaPlayer.play();
            isPaused = false;
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            if (isPaused) {
                mediaPlayer.play();
                isPaused = false;
            } else {
                mediaPlayer.pause();
                isPaused = true;
            }
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPaused = false;
        }
    }

    public void skipMusic() {
        if (!musicList.isEmpty()) {
            currentMusicIndex = (currentMusicIndex + 1) % musicList.size();
            initializeMediaPlayer(musicList.get(currentMusicIndex));
            playMusic();
        }
    }

    public void restartMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    public void setVolume(double volume) {
        this.volume = volume; // Store the volume
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

    public List<Music> getMusicList() {
        return musicList;
    }

    public Music getCurrentMusic() {
        if (currentMusicIndex != -1 && currentMusicIndex < musicList.size()) {
            return musicList.get(currentMusicIndex);
        }
        return null;
    }
}