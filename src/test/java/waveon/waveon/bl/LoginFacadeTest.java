package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Artist;
import waveon.waveon.core.OrdUser;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ArtistDAO;
import waveon.waveon.persist.OrdUserDAO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginFacadeTest {

    private LoginFacade loginFacade;
    private OrdUserDAO mockOrdUserDAO;
    private ArtistDAO mockArtistDAO;
    private AbstractFactory mockFactory;

    @BeforeEach
    public void setUp() {
        mockOrdUserDAO = mock(OrdUserDAO.class);
        mockArtistDAO = mock(ArtistDAO.class);
        mockFactory = mock(AbstractFactory.class);

        when(mockFactory.createOrdUserDAO()).thenReturn(mockOrdUserDAO);
        when(mockFactory.createArtistDAO()).thenReturn(mockArtistDAO);

        loginFacade = new LoginFacade() {
            {
                AbstractFactory factory = mockFactory;
                userDAO = mockOrdUserDAO;
                artistDAO = mockArtistDAO;
            }
        };
    }

    @Test
    public void testLoginAsOrdUser() {
        OrdUser user = new OrdUser(1, "user", "user@example.com", "password");
        when(mockOrdUserDAO.getUserByEmail("user@example.com")).thenReturn(user);

        loginFacade.login("user@example.com", false);

        assertEquals(user, LoginFacade.getCurrentUser());
    }

    @Test
    public void testLoginAsArtist() {
        Artist artist = new Artist(1, "artist", "artist@example.com", "password");
        when(mockArtistDAO.getArtistByEmail("artist@example.com")).thenReturn(artist);

        loginFacade.login("artist@example.com", true);

        assertEquals(artist, LoginFacade.getCurrentUser());
    }


    @Test
    public void testCheckCredentialsSuccess() {
        OrdUser user = new OrdUser(1, "user", "user@example.com", "password");
        when(mockOrdUserDAO.getUserByEmail("user@example.com")).thenReturn(user);

        loginFacade.login("user@example.com", false);
        boolean result = loginFacade.checkCredentials("user@example.com", "password");

        assertTrue(result);
    }

    @Test
    public void testCheckCredentialsFailure() {
        OrdUser user = new OrdUser(1, "user", "user@example.com", "password");
        when(mockOrdUserDAO.getUserByEmail("user@example.com")).thenReturn(user);

        loginFacade.login("user@example.com", false);
        boolean result = loginFacade.checkCredentials("user@example.com", "wrongpassword");

        assertFalse(result);
    }
}