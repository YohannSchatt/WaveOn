package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.MusicDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UploadMusicFacadeTest {

    private UploadMusicFacade uploadMusicFacade;
    private MusicDAO musicDAO;

    @BeforeEach
    public void setUp() {
        musicDAO = mock(MusicDAO.class);
        AbstractFactory factory = mock(AbstractFactory.class);
        when(factory.createMusicDAO()).thenReturn(musicDAO);
        AbstractFactory.setInstance(factory);

        uploadMusicFacade = new UploadMusicFacade();

        Artist user = new Artist(1, "username", "email", "password");
        UserSessionFacade.setCurrentUser(user);
    }

    @Test
    public void testUploadMusicSuccess() {
        byte[] fileContent = "dummyContent".getBytes();
        byte[] coverImage = "dummyCover".getBytes();

        when(musicDAO.saveMusic(any(Music.class))).thenReturn(true);

        boolean result = uploadMusicFacade.uploadMusic("title", fileContent, coverImage);
        assertTrue(result);
        verify(musicDAO, times(1)).saveMusic(any(Music.class));
    }

    @Test
    public void testUploadMusicFailure() {
        byte[] fileContent = "dummyContent".getBytes();
        byte[] coverImage = "dummyCover".getBytes();

        when(musicDAO.saveMusic(any(Music.class))).thenReturn(false);

        boolean result = uploadMusicFacade.uploadMusic("title", fileContent, coverImage);
        assertFalse(result);
        verify(musicDAO, times(1)).saveMusic(any(Music.class));
    }

    @Test
    public void testUploadMusicNotLoggedIn() {
        UserSessionFacade.setCurrentUser(null);

        byte[] fileContent = "dummyContent".getBytes();
        byte[] coverImage = "dummyCover".getBytes();

        boolean result = uploadMusicFacade.uploadMusic("title", fileContent, coverImage);
        assertFalse(result);
        verify(musicDAO, times(0)).saveMusic(any(Music.class));
    }

}
