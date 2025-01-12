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

public class UserSessionFacadeTest {

    private UserSessionFacade userSessionFacade;
    private OrdUserDAO mockOrdUserDAO;
    private ArtistDAO mockArtistDAO;
    private AbstractFactory mockFactory;
    private OrdUser mockOrdUser;
    private Artist mockArtist;

    @BeforeEach
    public void setUp() {
        mockOrdUserDAO = mock(OrdUserDAO.class);
        mockArtistDAO = mock(ArtistDAO.class);
        mockFactory = mock(AbstractFactory.class);
        mockOrdUser = mock(OrdUser.class);
        mockArtist = mock(Artist.class);

        when(mockFactory.createOrdUserDAO()).thenReturn(mockOrdUserDAO);
        when(mockFactory.createArtistDAO()).thenReturn(mockArtistDAO);
        AbstractFactory.setInstance(mockFactory);

        userSessionFacade = UserSessionFacade.getInstance();
    }

    @Test
    public void testLoginAsOrdUser() {
        String email = "user@example.com";
        String password = "password";
        when(mockOrdUserDAO.getUserByEmail(email)).thenReturn( mockOrdUser);
        when(mockOrdUser.getPassword()).thenReturn(password);
        when(mockOrdUser.getEmail()).thenReturn(email);

        boolean result = userSessionFacade.login(email, password, false);

        assertTrue(result);
        assertEquals(mockOrdUser, UserSessionFacade.getCurrentUser());
    }

    @Test
    public void testLoginAsArtist() {
        String email = "weeknd@example.com";
        String password = "weeknd123";
        when(mockArtistDAO.getArtistByEmail(email)).thenReturn( mockArtist);
        when(mockArtist.getPassword()).thenReturn(password);
        when(mockArtist.getEmail()).thenReturn(email);

        boolean result = userSessionFacade.login(email, password, true);

        assertTrue(result);
        assertEquals(mockArtist, UserSessionFacade.getCurrentUser());
    }

    @Test
    public void testRegisterOrdUser() throws Exception {
        String email = "alice@example.com";
        String username = "Alice";
        String password = "123";
        when(mockOrdUserDAO.getUserByEmail(email)).thenReturn(null);

        boolean result = userSessionFacade.register(email, username, password, false);

        assertTrue(result);
        verify(mockOrdUserDAO).addUser(email, username, password);
    }

    @Test
    public void testRegisterArtist() throws Exception {
        String email = "newartist@example.com";
        String username = "newartist";
        String password = "password";
        when(mockArtistDAO.getArtistByEmail(email)).thenReturn(null);

        boolean result = userSessionFacade.register(email, username, password, true);

        assertTrue(result);
        verify(mockArtistDAO).addArtist(email, username, password);
    }

    @Test
    public void testLogout() {
        userSessionFacade.logout();
        assertNull(UserSessionFacade.getCurrentUser());
    }


}