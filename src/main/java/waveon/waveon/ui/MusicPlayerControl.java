package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.core.Music;

/**
 * The MusicPlayerControl class provides the UI controls for managing music playback,
 * including play/pause, skip, restart, volume control, and progress tracking.
 */
public class MusicPlayerControl extends VBox {
    private MusicFacade musicFacade;

    @FXML
    private Slider progressBar;

    @FXML
    private Label currentMusicLabel;

    @FXML
    private Button playPauseButton;

    @FXML
    private Label timerLabel;

    @FXML
    private Slider volumeSlider;

    /**
     * Constructs a new MusicPlayerControl instance.
     */
    public MusicPlayerControl() {
        // Default constructor
    }

    /**
     * Sets the MusicFacade instance to be used by this control.
     *
     * @param musicFacade the MusicFacade instance
     */
    public void setMusicFacade(MusicFacade musicFacade) {
        this.musicFacade = musicFacade;
    }

    /**
     * Initializes the UI components and their event listeners.
     */
    @FXML
    private void initialize() {
        // Set the initial text for the play/pause button
        playPauseButton.setText("Play Music");

        // Add volume slider listener
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicFacade.setVolume(newValue.doubleValue());
        });

        // Add progress bar listener for value changes
        progressBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (progressBar.isValueChanging()) {
                musicFacade.seekMusic(Duration.seconds(newValue.doubleValue()));
            }
        });

        // Add progress bar listener for mouse released event
        progressBar.setOnMouseReleased(event -> {
            musicFacade.seekMusic(Duration.seconds(progressBar.getValue()));
        });
    }

    /**
     * Toggles between playing and pausing the music playback.
     */
    @FXML
    private void togglePlayPause() {
        if (musicFacade.getMediaPlayer() != null && musicFacade.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
            musicFacade.togglePauseResumeMusic();
            playPauseButton.setText("Play Music");
        } else {
            musicFacade.playMusic();
            playPauseButton.setText("Pause Music");
            updateProgressBar();
        }
        updateCurrentMusicLabel();
        updateProgressBar();
    }

    /**
     * Skips to the next music track in the list.
     */
    @FXML
    private void skipMusic() {
        musicFacade.skipMusic();
        updateProgressBar();
        updateCurrentMusicLabel();
    }

    /**
     * Restarts the current music track from the beginning.
     */
    @FXML
    private void restartMusic() {
        musicFacade.restartMusic();
    }

    /**
     * Updates the progress bar to reflect the current playback time.
     */
    private void updateProgressBar() {
        if (musicFacade != null && musicFacade.getMediaPlayer() != null) {
            Duration totalDuration = musicFacade.getTotalDuration();
            progressBar.setMax(totalDuration.toSeconds());

            musicFacade.getMediaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                progressBar.setValue(newValue.toSeconds());
                updateTimerLabel(newValue, totalDuration);
            });
        }
    }

    /**
     * Updates the timer label to show the current and total playback time.
     *
     * @param currentTime the current playback time
     * @param totalDuration the total duration of the music track
     */
    private void updateTimerLabel(Duration currentTime, Duration totalDuration) {
        String currentTimeStr = formatDuration(currentTime);
        String totalDurationStr = formatDuration(totalDuration);
        timerLabel.setText(currentTimeStr + "/" + totalDurationStr);
    }

    /**
     * Formats a Duration object into a string in the format MM:SS.
     *
     * @param duration the Duration object to format
     * @return the formatted duration string
     */
    private String formatDuration(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Updates the label to show the title of the currently playing music track.
     */
    private void updateCurrentMusicLabel() {
        Music currentMusic = musicFacade.getCurrentMusic();
        if (currentMusic != null) {
            currentMusicLabel.setText("Now playing: " + currentMusic.getTitle());
        } else {
            currentMusicLabel.setText("No music playing");
        }
    }
}