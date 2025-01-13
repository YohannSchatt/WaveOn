package waveon.waveon.bl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.core.Music;
import waveon.waveon.core.Playlist;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAO;
import waveon.waveon.persist.PlaylistDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The MusicFacade class provides methods to manage and control music playback,
 * including loading, playing, pausing, stopping, and seeking music tracks.
 * It also handles volume control, playlist management, and music ordering.
 */
public class MusicFacade {
    private final ArrayList<Music> musicList;
    private static MediaPlayer mediaPlayer;
    private MusicDAO musicDAO;
    private PlaylistDAO playlistDAO;
    private int currentMusicIndex = 1;
    private Music currentMusic = null;
    private boolean isPaused = false;
    private double volume = 0.1; // Default volume
    private ArrayList<Playlist> playlists;

    /**
     * Constructs a new MusicFacade instance and initializes the music and playlist DAOs.
     */
    public MusicFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        musicDAO = factory.createMusicDAO();
        this.musicList = musicDAO.getAllMusics();
        playlistDAO = factory.createPlaylistDAO();
    }

    /**
     * Loads the music file content by its title and initializes the MediaPlayer object.
     *
     * @param title the title of the music to load
     */
    public void loadMusicByTitle(String title) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        for (Music value : musicList) {
            if (value.getTitle().equals(title)) {
                currentMusicIndex = value.getId();
                Music music = musicDAO.getMusicWithContentById(currentMusicIndex);
                initializeMediaPlayer(music);
                break;
            }
        }
    }

    /**
     * Loads the music file content by its ID and initializes the MediaPlayer object.
     *
     * @param id the ID of the music to load
     */
    public void loadMusicById(int id) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        currentMusicIndex = id;
        Music music = musicDAO.getMusicWithContentById(id);
        initializeMediaPlayer(music);
        incrementPlayCount(id);
    }

    /**
     * Increments the play count of the music by its ID.
     *
     * @param id the ID of the music
     */
    private void incrementPlayCount(int id) {
        musicDAO.incrementPlayCount(id);
    }

    /**
     * Orders the music list based on the specified filter option.
     *
     * @param filterOption the filter option to order the music list
     */
    public void orderMusicList(FilterOption filterOption) {
        switch (filterOption) {
            case Newest:
                musicList.sort((m1, m2) -> m2.getReleaseDate().compareTo(m1.getReleaseDate()));
                break;
            case Oldest:
                musicList.sort((m1, m2) -> m1.getReleaseDate().compareTo(m2.getReleaseDate()));
                break;
            case MostListened:
                musicList.sort((m1, m2) -> m2.getStreamCount() - m1.getStreamCount());
                break;
            case LeastListened:
                musicList.sort((m1, m2) -> m1.getStreamCount() - m2.getStreamCount());
                break;
            default:
                break;
        }
    }

    /**
     * Initializes the MediaPlayer object with the music file content.
     *
     * @param music the music object containing the file content
     */
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

    /**
     * Plays the loaded music.
     */
    public void playMusic() {
        if (mediaPlayer == null && currentMusic != null) {
            loadMusicById(currentMusic.getId());
        } else if (mediaPlayer == null && !musicList.isEmpty()) {
            loadMusicById(currentMusicIndex);
        }
        if (mediaPlayer != null) {
            mediaPlayer.play();
            currentMusic = musicList.get(currentMusicIndex - 1);
            isPaused = false;
        }
    }

    /**
     * Toggles between pausing and resuming the music playback.
     */
    public void togglePauseResumeMusic() {
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

    /**
     * Stops the music playback.
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPaused = false;
        }
    }

    /**
     * Skips to the next music in the list.
     */
    public void skipMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPaused = false;
        }
        if (!musicList.isEmpty()) {
            if (currentMusic == musicList.get(currentMusicIndex - 1)) {
                currentMusicIndex = (currentMusicIndex + 1) % musicList.size();
            }
            loadMusicById(currentMusicIndex);
            playMusic();
        }
    }

    /**
     * Restarts the music playback from the beginning.
     */
    public void restartMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.ZERO);
        }
    }

    /**
     * Sets the volume of the music playback.
     *
     * @param volume the volume level to set (0.0 to 1.0)
     */
    public void setVolume(double volume) {
        this.volume = volume; // Store the volume
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Seeks to a specific time in the music playback.
     *
     * @param time the time to seek to
     */
    public void seekMusic(Duration time) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(time);
        }
    }

    /**
     * Gets the current playback time of the music.
     *
     * @return the current playback time
     */
    public Duration getCurrentTime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentTime();
        }
        return Duration.ZERO;
    }

    /**
     * Gets the total duration of the music.
     *
     * @return the total duration of the music
     */
    public Duration getTotalDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getTotalDuration();
        }
        return Duration.ZERO;
    }

    /**
     * Gets the current MediaPlayer object.
     *
     * @return the current MediaPlayer object
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * Gets the current music object.
     *
     * @return the current music object
     */
    public Music getCurrentMusic() {
        if (currentMusic != null) {
            return currentMusic;
        } else if (currentMusicIndex < musicList.size()) {
            return musicList.get(currentMusicIndex - 1);
        }
        return null;
    }

    /**
     * Sets the current music object.
     *
     * @param music the music object to set as current
     */
    public void setCurrentMusic(Music music) {
        currentMusic = music;
        for (Music m : musicList) {
            if (m.getId() == music.getId()) {
                currentMusicIndex = m.getId();
                break;
            }
        }
    }

    /**
     * Uninstalls music from the specified directory.
     *
     * @param directoryPath the path of the directory to uninstall music from
     */
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

    /**
     * Gets all music tracks without the file content to avoid memory issues.
     *
     * @return a list of all music tracks
     */
    public ArrayList<Music> getAllMusics() {
        return musicList;
    }

    /**
     * Gets the current list of music tracks.
     *
     * @return the current list of music tracks
     */
    public ArrayList<Music> getMusicList() {
        return musicList;
    }

    /**
     * Creates a new playlist with the specified name and user ID.
     *
     * @param name the name of the playlist
     * @param userId the ID of the user creating the playlist
     * @return true if the playlist is created successfully, false otherwise
     */
    public boolean createPlaylist(String name, int userId) {
        return playlistDAO.createPlaylist(name, userId);
    }

    /**
     * Gets all playlists for the specified user ID.
     *
     * @param userId the ID of the user
     * @return a list of playlists for the user
     */
    public ArrayList<Playlist> getPlaylistsByUserId(int userId) {
        return playlistDAO.getPlaylistsByUserId(userId);
    }

    /**
     * Adds a music track to the specified playlist.
     *
     * @param music the music track to add
     * @param playlist the playlist to add the music track to
     * @return true if the music track is added successfully, false otherwise
     */
    public boolean addMusicToPlaylist(Music music, Playlist playlist) {
        return playlistDAO.addMusicToPlaylist(music.getId(), playlist.getId());
    }

    /**
     * Gets a music track by its ID.
     *
     * @param id the ID of the music track
     * @return the music track with the specified ID
     */
    public Music getMusicById(int id) {
        return musicDAO.getMusicById(id);
    }

    /**
     * Gets all music tracks from the specified playlist.
     *
     * @param playlistId the ID of the playlist
     * @return a list of music tracks in the playlist
     */
    public ArrayList<Music> getMusicsByPlaylistId(int playlistId) {
        ArrayList<Music> musicList = new ArrayList<>();
        try {
            musicList = playlistDAO.getMusicByPlaylistId(playlistId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicList;
    }

    /**
     * Deletes the specified playlist.
     *
     * @param playlistId the ID of the playlist to delete
     * @return true if the playlist is deleted successfully, false otherwise
     */
    public boolean deletePlaylist(int playlistId) {
        return playlistDAO.deletePlaylist(playlistId);
    }

    /**
     * Deletes the specified music track from the specified playlist.
     *
     * @param playlistId the ID of the playlist
     * @param musicId the ID of the music track to delete
     * @return true if the music track is deleted successfully, false otherwise
     */
    public boolean deleteMusicFromPlaylist(int playlistId, int musicId) {
        return playlistDAO.deleteMusicFromPlaylist(playlistId, musicId);
    }

    /**
     * Gets the last ID used for a music track.
     *
     * @return the last ID used for a music track
     */
    public int getLasId() {
        return musicDAO.getLastId();
    }
}