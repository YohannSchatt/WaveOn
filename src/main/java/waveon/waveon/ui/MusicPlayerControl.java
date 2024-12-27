// File: src/main/java/waveon/waveon/ui/MusicPlayerControl.java
package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.core.Music;

import java.util.List;
import java.util.Random;

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

    public MusicPlayerControl() {
        // Default constructor
    }

    public void setMusicFacade(MusicFacade musicFacade) {
        this.musicFacade = musicFacade;
    }

    @FXML
    private void initialize() {
        // Add play/pause button
        playPauseButton.setOnAction(e -> togglePlayPause());

        // Add skip and restart buttons
        Button skipMusicButton = new Button("Skip");
        skipMusicButton.setOnAction(e -> skipMusic());

        Button restartMusicButton = new Button("Restart");
        restartMusicButton.setOnAction(e -> restartMusic());

        // Add progress bar and timer label
        HBox progressBarContainer = new HBox(10);
        progressBar.setMin(0);
        progressBar.setMax(100);
        progressBar.setValue(0);
        progressBarContainer.getChildren().addAll(progressBar, timerLabel);

        // Add volume slider
        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicFacade.setVolume(newValue.doubleValue());
        });

        // Add current music label
        this.getChildren().addAll(playPauseButton, skipMusicButton, restartMusicButton, progressBarContainer, volumeSlider, currentMusicLabel);
    }

    @FXML
    private void togglePlayPause() {
        if (musicFacade.getMediaPlayer() != null && musicFacade.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
            musicFacade.pauseMusic();
            playPauseButton.setText("Play Music");
        } else {
            musicFacade.playMusic();
            playPauseButton.setText("Pause Music");
            updateProgressBar();
        }
        updateCurrentMusicLabel();
    }

    @FXML
    private void skipMusic() {
        musicFacade.skipMusic();
        updateCurrentMusicLabel();
        updateProgressBar();
    }

    @FXML
    private void restartMusic() {
        musicFacade.restartMusic();
    }

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

    private void updateTimerLabel(Duration currentTime, Duration totalDuration) {
        String currentTimeStr = formatDuration(currentTime);
        String totalDurationStr = formatDuration(totalDuration);
        timerLabel.setText(currentTimeStr + "/" + totalDurationStr);
    }

    private String formatDuration(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) duration.toSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateCurrentMusicLabel() {
        Music currentMusic = musicFacade.getCurrentMusic();
        if (currentMusic != null) {
            currentMusicLabel.setText("Now playing: " + currentMusic.getName());
        } else {
            currentMusicLabel.setText("No music playing");
        }
    }
}