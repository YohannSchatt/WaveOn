package waveon.waveon.bl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.core.Music;
import waveon.waveon.core.Playlist;
import waveon.waveon.persist.MusicDAOPG;
import waveon.waveon.persist.PlaylistDAOPG;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicFacade {
    private List<Music> musicList;
    private MediaPlayer mediaPlayer;
    private MusicDAOPG musicDAOPG;
    private int currentMusicIndex = -1;
    private boolean isPaused = false;
    private double volume = 0.5; // Default volume
    private List<Playlist> playlists;
    private PlaylistDAOPG playlistDAOPG;

    public MusicFacade() {
        this.musicDAOPG = new MusicDAOPG();
        this.musicList = musicDAOPG.getAllMusic();
        this.playlistDAOPG = new PlaylistDAOPG();
    }

    public boolean createPlaylist(String name, int userId) {
        playlistDAOPG.createPlaylist(name, userId);
        return true;
    }

    public List<Playlist> getPlaylistsByUserId(int userId) {
        return playlistDAOPG.getPlaylistsByUserId(userId);
    }

    public boolean addMusicToPlaylist(Music music, Playlist playlist) {
        return playlistDAOPG.addMusicToPlaylist(music.getId(), playlist.getId());
    }

    public List<Music> getMusicByPlaylistId(int playlistId) {
        List<Music> musicList = new ArrayList<>();
        try {
            musicList = playlistDAOPG.getMusicByPlaylistId(playlistId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicList;
    }

    public void loadMusicByTitle(String title) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getTitle().equals(title)) {
                currentMusicIndex = i;
                initializeMediaPlayer(musicList.get(i));
                break;
            }
        }
    }

    private void initializeMediaPlayer(Music music) {
        if (music.getFileContent() != null) {
            try {
                // Create a temporary file
                File tempFile = File.createTempFile("music", ".mp3");
                tempFile.deleteOnExit();

                // Write the byte array to the temporary file
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(music.getFileContent());
                }

                // Create a Media object from the temporary file
                Media media = new Media(tempFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setVolume(volume); // Set the volume to the stored value
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playMusic() {
        if (mediaPlayer == null && !musicList.isEmpty()) {
            loadMusicByTitle(musicList.get(0).getTitle());
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
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPaused = false;
        }
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

    public void seekMusic(Duration time) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(time);
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

    public void uninstallMusic(String directoryPath) {
        // Implement the logic to uninstall music here
        // Delete all files in the specified directory
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        musicList.clear();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    public List<Music> getAllMusic() {
        return musicList;
    }
}