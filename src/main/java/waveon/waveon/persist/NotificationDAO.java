package waveon.waveon.persist;

import waveon.waveon.core.Notification;

import java.sql.Connection;
import java.util.ArrayList;

public interface NotificationDAO {

    Notification getNotificationById(int id);

    ArrayList<Notification> getNotificationsByUserId(int userId);
}
