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
        }
    }

    public void playMusic() {
        if (mediaPlayer == null && !musicList.isEmpty()) {
            loadMusicByTitle(musicList.get(0).getName());
        }
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
        if (currentMusicIndex != -1 && currentMusicIndex < musicList.size() - 1) {
            currentMusicIndex++;
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
}