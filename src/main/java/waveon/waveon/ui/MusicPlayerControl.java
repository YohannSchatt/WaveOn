// File: src/main/java/waveon/waveon/ui/MusicPlayerControl.java
package waveon.waveon.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import waveon.waveon.bl.MusicFacade;
import waveon.waveon.core.Music;

import java.util.List;
import java.util.Random;

public class MusicPlayerControl extends VBox {
    private final MusicFacade musicFacade;
    private final Slider progressBar;
    private final Label currentMusicLabel;

    public MusicPlayerControl(MusicFacade musicFacade) {
        this.musicFacade = musicFacade;
        this.progressBar = new Slider();
        this.currentMusicLabel = new Label("No music playing");
        initializeUI();
    }

    private void initializeUI() {
        // Add buttons to play, pause, skip, and restart music
        Button playMusicButton = new Button("Play Music");
        playMusicButton.setOnAction(e -> {
            musicFacade.playMusic();
            updateProgressBar();
            updateCurrentMusicLabel();
        });
        this.getChildren().add(playMusicButton);

        Button pauseMusicButton = new Button("Pause Music");
        pauseMusicButton.setOnAction(e -> musicFacade.pauseMusic());
        this.getChildren().add(pauseMusicButton);

        Button skipMusicButton = new Button("Skip");
        skipMusicButton.setOnAction(e -> {
            musicFacade.skipMusic();
            updateCurrentMusicLabel();
        });
        this.getChildren().add(skipMusicButton);

        Button restartMusicButton = new Button("Restart");
        restartMusicButton.setOnAction(e -> musicFacade.restartMusic());
        this.getChildren().add(restartMusicButton);

        // Add progress bar
        progressBar.setMin(0);
        progressBar.setMax(100);
        progressBar.setValue(0);
        this.getChildren().add(progressBar);

        // Add volume slider
        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicFacade.setVolume(newValue.doubleValue());
        });
        this.getChildren().add(volumeSlider);

        // Add current music label
        this.getChildren().add(currentMusicLabel);
    }

    public void playRandomSong() {
        List<Music> musicList = musicFacade.getMusicList();
        if (!musicList.isEmpty()) {
            Random random = new Random();
            Music randomMusic = musicList.get(random.nextInt(musicList.size()));
            musicFacade.loadMusicByTitle(randomMusic.getName());
            musicFacade.playMusic();
            updateProgressBar();
            updateCurrentMusicLabel();
        }
    }

    private void updateProgressBar() {
        if (musicFacade != null) {
            Duration totalDuration = musicFacade.getTotalDuration();
            progressBar.setMax(totalDuration.toSeconds());

            musicFacade.getMediaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                progressBar.setValue(newValue.toSeconds());
            });
        }
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