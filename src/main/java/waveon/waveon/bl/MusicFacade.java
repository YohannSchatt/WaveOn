package waveon.waveon.bl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.core.Music;
import waveon.waveon.core.Playlist;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.PlaylistDAO;
import waveon.waveon.persist.PlaylistDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MusicFacade {
    private final ArrayList<Music> musicList;
    private MediaPlayer mediaPlayer;
    private MusicDAO musicDAO;
    private PlaylistDAO playlistDAO;
    private int currentMusicIndex = -1;
    private Music currentMusic = null;
    private boolean isPaused = false;
    private double volume = 0.1; // Default volume
    private ArrayList<Playlist> playlists;

    public MusicFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        musicDAO = factory.createMusicDAO();
        this.musicList = musicDAO.getAllMusics();
        playlistDAO = factory.createPlaylistDAO();
    }

    // Loads the music file content from the database and initializes the MediaPlayer object
    public void loadMusicByTitle(String title) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getTitle().equals(title)) {
                currentMusicIndex = i;
                int musicId = musicList.get(i).getId();
                Music music = musicDAO.getMusicWithContentById(musicId);
                initializeMediaPlayer(music);
                break;
            }
        }
    }

    // Initializes the MediaPlayer object with the music file content
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

    // PLay the loaded music
    public void playMusic() {
        if (mediaPlayer == null && !musicList.isEmpty()) {

            loadMusicByTitle(musicList.get(currentMusicIndex).getTitle());
        }
        if (mediaPlayer != null) {
            mediaPlayer.play();
            currentMusic = musicList.get(currentMusicIndex);
            isPaused = false;
        }
    }

    // Pause the music (if playing) or resume the music (if paused)
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

    // Stop the music
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPaused = false;
        }
    }

    // Skip to the next music in the list
    public void skipMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPaused = false;
        }
        if (!musicList.isEmpty()) {
            if (currentMusic == musicList.get(currentMusicIndex)) {
                currentMusicIndex = (currentMusicIndex + 1) % musicList.size();
            }
                loadMusicByTitle(musicList.get(currentMusicIndex).getTitle());
            playMusic();
        }
    }

    // Restart the music (set the current time to 0)
    public void restartMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    // Set the volume of the music
    public void setVolume(double volume) {
        this.volume = volume; // Store the volume
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    // Seek to a specific time in the music
    public void seekMusic(Duration time) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(time);
        }
    }

    // Get the current time of the music
    public Duration getCurrentTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentTime();
        }
        return Duration.ZERO;
    }

    // Get the total duration of the music
    public Duration getTotalDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getTotalDuration();
        }
        return Duration.ZERO;
    }

    // Return the current MediaPlayer object
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }



    // Return the current music object
    public Music getCurrentMusic() {
        if (currentMusic != null) {
            return currentMusic;
        } else if (currentMusicIndex != -1 && currentMusicIndex < musicList.size()) {
            return musicList.get(currentMusicIndex);
        }
        return null;
    }

    // Set the current music object
    public void setCurrentMusic(Music music) {
        System.out.println("Setting current music to: " + music.getTitle());
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getId() == music.getId()) {
                currentMusicIndex = i;
                System.out.println("Current music index: " + currentMusicIndex + "Done");
                break;
            }
        }
    }

    // Uninstall music from the specified directory
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

    // Return all musics from the DAO without the music file content to avoid memory issues
    public ArrayList<Music> getAllMusics() {
        return musicList;
    }

    // Return the current musicList
    public ArrayList<Music> getMusicList() {
        return musicList;
    }

    // Creates a new playlist with the specified name and user ID
    // Returns true if the playlist is created successfully, false otherwise
    public boolean createPlaylist(String name, int userId) {
        return playlistDAO.createPlaylist(name, userId);
    }

    // Returns all playlists for the specified user ID
    public ArrayList<Playlist> getPlaylistsByUserId(int userId) {
        return playlistDAO.getPlaylistsByUserId(userId);
    }

    // Adds a music to the specified playlist
    public boolean addMusicToPlaylist(Music music, Playlist playlist) {
        return playlistDAO.addMusicToPlaylist(music.getId(), playlist.getId());
    }

    // Returns all musics from the specified playlist
    public ArrayList<Music> getMusicsByPlaylistId(int playlistId) {
        ArrayList<Music> musicList = new ArrayList<>();
        try {
            musicList = playlistDAO.getMusicByPlaylistId(playlistId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicList;
    }

    // Deletes the specified playlist
    public boolean deletePlaylist(int playlistId) {
        return playlistDAO.deletePlaylist(playlistId);
    }

    // Deletes the specified music from the specified playlist
    public boolean deleteMusicFromPlaylist(int playlistId, int musicId) {
        return playlistDAO.deleteMusicFromPlaylist(playlistId, musicId);
    }

    public int getLasId() {
        return musicDAO.getLastId();
    }
}