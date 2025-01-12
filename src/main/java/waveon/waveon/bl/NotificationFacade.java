package waveon.waveon.bl;

import waveon.waveon.core.Artist;
import waveon.waveon.core.Notification;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.NotificationDAO;

import java.util.ArrayList;

public class NotificationFacade {


    NotificationDAO notificationDAO;

    public NotificationFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        notificationDAO = factory.createNotificationDAO();
    }

    // Get the list of notifications for the current user
    public ArrayList<Notification> getNotificationsList() {
        ArrayList<Notification> notificationsList;
        System.out.println("load Notifs Facade");
        if (UserSessionFacade.getCurrentUser() != null) {
            if (UserSessionFacade.getCurrentUser() instanceof Artist) {
                notificationsList = notificationDAO.getNotificationsByArtistId(UserSessionFacade.getCurrentUser().getId());
            }
            else {
                notificationsList = notificationDAO.getNotificationsByUserId(UserSessionFacade.getCurrentUser().getId());
            }
        }
        else {
            System.out.println("User not connected");
            notificationsList = new ArrayList<>();
        }
        return notificationsList;
    }

    // Remove the link between the notification and the user/artist
    public void clearNotification(int notificationId) {
        if (UserSessionFacade.getCurrentUser() != null) {
            if (UserSessionFacade.getCurrentUser() instanceof Artist) {
                notificationDAO.clearNotificationsForArtistById(UserSessionFacade.getCurrentUser().getId(), notificationId);
            }
            else {
                notificationDAO.clearNotificationsForUserById(UserSessionFacade.getCurrentUser().getId(), notificationId);
            }
        }
        else {
            System.out.println("User not connected");
        }
    }

}
