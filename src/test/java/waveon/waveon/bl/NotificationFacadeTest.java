package waveon.waveon.bl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import waveon.waveon.core.Artist;
import waveon.waveon.core.Notification;
import waveon.waveon.core.OrdUser;
import waveon.waveon.core.User;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.NotificationDAO;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationFacadeTest {

    private NotificationFacade notificationFacade;
    private NotificationDAO mockNotificationDAO;

    @BeforeEach
    public void setUp() {
        mockNotificationDAO = mock(NotificationDAO.class);
        AbstractFactory mockFactory = mock(AbstractFactory.class);

        when(mockFactory.createNotificationDAO()).thenReturn(mockNotificationDAO);
        AbstractFactory.setInstance(mockFactory);

        notificationFacade = new NotificationFacade();
    }

    @Test
    public void testGetNotificationsListForArtist() {
        Artist mockArtist = new Artist(1, "artist", "email", "password");
        UserSessionFacade.setCurrentUser(mockArtist);

        ArrayList<Notification> mockNotifications = new ArrayList<>();
        mockNotifications.add(new Notification(1, "New song released", "content", "link"));

        when(mockNotificationDAO.getNotificationsByArtistId(1)).thenReturn(mockNotifications);

        ArrayList<Notification> result = notificationFacade.getNotificationsList();

        assertEquals(mockNotifications, result);
        verify(mockNotificationDAO, times(1)).getNotificationsByArtistId(1);
    }

    @Test
    public void testGetNotificationsListForUser() {
        OrdUser mockUser = new OrdUser(2, "user", "email", "password");
        UserSessionFacade.setCurrentUser(mockUser);

        ArrayList<Notification> mockNotifications = new ArrayList<>();
        mockNotifications.add(new Notification(2, "Follow request", "content", "link"));

        when(mockNotificationDAO.getNotificationsByUserId(2)).thenReturn(mockNotifications);

        ArrayList<Notification> result = notificationFacade.getNotificationsList();

        assertEquals(mockNotifications, result);
        verify(mockNotificationDAO, times(1)).getNotificationsByUserId(2);
    }

    @Test
    public void testGetNotificationsListWhenUserNotConnected() {
        UserSessionFacade.setCurrentUser(null);

        ArrayList<Notification> result = notificationFacade.getNotificationsList();

        assertTrue(result.isEmpty());
        verify(mockNotificationDAO, never()).getNotificationsByArtistId(anyInt());
        verify(mockNotificationDAO, never()).getNotificationsByUserId(anyInt());
    }

    @Test
    public void testClearNotificationForArtist() {
        Artist mockArtist = new Artist(1, "artist", "email", "password");
        UserSessionFacade.setCurrentUser(mockArtist);

        notificationFacade.clearNotification(101);

        verify(mockNotificationDAO, times(1)).clearNotificationsForArtistById(1, 101);
    }

    @Test
    public void testClearNotificationForUser() {
        OrdUser mockUser = new OrdUser(2, "user", "email", "password");
        UserSessionFacade.setCurrentUser(mockUser);

        notificationFacade.clearNotification(102);

        verify(mockNotificationDAO, times(1)).clearNotificationsForUserById(2, 102);
    }

    @Test
    public void testClearNotificationWhenUserNotConnected() {
        UserSessionFacade.setCurrentUser(null);

        notificationFacade.clearNotification(103);

        verify(mockNotificationDAO, never()).clearNotificationsForArtistById(anyInt(), anyInt());
        verify(mockNotificationDAO, never()).clearNotificationsForUserById(anyInt(), anyInt());
    }
}
