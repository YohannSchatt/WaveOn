// File: src/test/java/waveon/waveon/bl/MusicFacadeTest.java
package waveon.waveon.bl;

import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Music;
import waveon.waveon.persist.MusicDAOPG;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MusicFacadeTest {

    private MusicFacade musicFacade;
    private MusicDAOPG mockMusicDAOPG;
    private MediaPlayer mockMediaPlayer;

    @BeforeEach
    public void setUp() {
        mockMusicDAOPG = mock(MusicDAOPG.class);
        mockMediaPlayer = mock(MediaPlayer.class);

        Music music1 = new Music("Song1", null);
        Music music2 = new Music("Song2", null);
        List<Music> mockMusicList = Arrays.asList(music1, music2);

        when(mockMusicDAOPG.getAllMusic()).thenReturn(mockMusicList);

        musicFacade = new MusicFacade();
        musicFacade = Mockito.spy(musicFacade);

        // Inject mocks
        doReturn(mockMusicDAOPG).when(musicFacade).getMusicDAOPG();
        doNothing().when(musicFacade).initializeMediaPlayer(any());
        doReturn(mockMediaPlayer).when(musicFacade).getMediaPlayer();

        musicFacade = Mockito.spy(new MusicFacade());
        musicFacade.getMusicList().addAll(mockMusicList);
    }

    @Test
    public void testSkipMusic() {
        musicFacade.skipMusic();
        assertEquals("Song2", musicFacade.getCurrentMusic().getName());

        musicFacade.skipMusic();
        assertEquals("Song1", musicFacade.getCurrentMusic().getName()); // Test loop
    }
}
