package waveon.waveon.persist;

import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NotificationDAOPG implements NotificationDAO {

    @Override
    public Notification getNotificationById(int id) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT id, title, content, link FROM notification WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            Notification notification = null;
            if (rs.next()) {
                notification = new Notification(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("link"));
            }
            return notification;
        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.getNotificationById: " + e);
        }
        return null;
    }

    @Override
    public ArrayList<Notification> getNotificationsByUserId(int userId) {
        System.out.println("getNotificationsByUserId DAO PG");
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT n.id, n.title, n.content, n.link " +
                "FROM notification n " +
                "JOIN user_notification un ON n.id = un.notification_id " +
                "WHERE un.user_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            ArrayList<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                Notification notification = new Notification(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("link"));
                notifications.add(notification);
            }
            return notifications;
        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.getNotificationsByUserId: " + e);
        }
        return null;
    }
}