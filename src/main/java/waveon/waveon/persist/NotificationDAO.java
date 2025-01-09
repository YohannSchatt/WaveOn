package waveon.waveon.persist;

import waveon.waveon.core.Notification;

import java.sql.Connection;
import java.util.ArrayList;

public interface NotificationDAO {

    Notification getNotificationById(int id);

    ArrayList<Notification> getNotificationsByUserId(int userId);

    void createNotification(String title, String content, String link);

    ArrayList<Integer> getUserIdsFollowingArtist(int artistId);

    void linkNotificationToUser(int notificationId, int userId);

    void linkNotificationToArtist(int notificationId, int artistId);

    int getLastId();
}
