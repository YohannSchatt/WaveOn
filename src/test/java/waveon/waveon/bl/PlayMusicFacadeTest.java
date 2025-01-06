package waveon.waveon.bl;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Music;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAO;

import java.util.concurrent.CountDownLatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayMusicFacadeTest {

    private MusicFacade musicFacade;
    private MusicDAO mockMusicDAO;

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    public void setUp() {
        // Mock de MusicDAO
        mockMusicDAO = mock(MusicDAO.class);
        AbstractFactory factory = mock(AbstractFactory.class);
        when(factory.createMusicDAO()).thenReturn(mockMusicDAO);
        AbstractFactory.setInstance(factory);

        // Créez une liste simulée de musiques
        Music music1 = new Music(1, "Rick Roll", null, null, 1, "Artist1", null, 0);
        Music music2 = new Music(2, "Never Gonna Give You Up", null, null, 2, "Artist2", null, 0);
        List<Music> mockMusicList = Arrays.asList(music1, music2);

        // Simulez la méthode pour retourner cette liste
        when(mockMusicDAO.getAllMusics()).thenReturn(new ArrayList<>(mockMusicList));


        // Instanciez MusicFacade
        musicFacade = new MusicFacade();
    }

    @Test
    public void testSkipMusicFunctionality() {
        // Vérifiez l'état initial (aucune musique jouée)
        assertNull(musicFacade.getCurrentMusic(), "Aucune musique ne devrait être sélectionnée au départ.");

        // Premier appel à skipMusic : charge et joue la première chanson
        musicFacade.skipMusic();
        assertEquals("Rick Roll", musicFacade.getCurrentMusic().getTitle(), "La première musique devrait être jouée.");

        // Deuxième appel à skipMusic : passe à la chanson suivante
        musicFacade.skipMusic();
        assertEquals("Never Gonna Give You Up", musicFacade.getCurrentMusic().getTitle(), "La deuxième musique devrait être jouée.");

        // Troisième appel à skipMusic : boucle à la première chanson
        musicFacade.skipMusic();
        assertEquals("Rick Roll", musicFacade.getCurrentMusic().getTitle(), "La boucle devrait revenir à la première musique.");
    }
}
