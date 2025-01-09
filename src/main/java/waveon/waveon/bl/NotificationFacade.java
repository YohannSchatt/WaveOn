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
        notificationsList = notificationDAO.getNotificationsByUserId(UserSessionFacade.getCurrentUser().getId());
        System.out.println("load Notifs Facade : " + notificationsList.size());
    }

    public ArrayList<Notification> getNotificationsList() {
        return notificationsList;
    }


}
