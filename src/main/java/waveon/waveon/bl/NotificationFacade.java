package waveon.waveon.bl;

import waveon.waveon.core.Notification;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.NotificationDAO;

import java.util.ArrayList;

public class NotificationFacade {

    ArrayList<Notification> notificationsList;
    NotificationDAO notificationDAO;

    public NotificationFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        notificationDAO = factory.createNotificationDAO();
    }

    public void loadNotifications() {
        System.out.println("load Notifs Facade");
        if (UserSessionFacade.getCurrentUser() != null) {
            notificationsList = notificationDAO.getNotificationsByUserId(UserSessionFacade.getCurrentUser().getId());
        }
        else {
            System.out.println("User not connected");
        }
    }

    public ArrayList<Notification> getNotificationsList() {
        return notificationsList;
    }


}
