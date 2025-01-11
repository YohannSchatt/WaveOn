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

    @Override
    public ArrayList<Notification> getNotificationsByArtistId(int artistId) {
        System.out.println("getNotificationsByArtistId DAO PG");
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT n.id, n.title, n.content, n.link " +
                "FROM notification n " +
                "JOIN artist_notification an ON n.id = an.notification_id " +
                "WHERE an.artist_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, artistId);

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

    @Override
    public void createNotification(String title, String content, String link) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO notification (title, content, link) VALUES (?, ?, ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, link);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.createNotification: " + e);
        }
    }

    @Override
    public void clearNotificationsForUserById(int userId, int notificationId) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "DELETE FROM user_notification WHERE user_id = ? AND notification_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, notificationId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.clearNotificationsForUserById: " + e);
        }
    }

    @Override
    public void clearNotificationsForArtistById(int artistId, int notificationId) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "DELETE FROM artist_notification WHERE artist_id = ? AND notification_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, artistId);
            pstmt.setInt(2, notificationId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.clearNotificationsForArtistById: " + e);
        }
    }

    @Override
    public ArrayList<Integer> getUserIdsFollowingArtist(int artistId) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT user_id FROM ordinaryuser";
        ArrayList<Integer> followersId = new ArrayList<>();
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                followersId.add(rs.getInt("user_id"));
            }
        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.getUserIdsFollowingArtist: " + e);
        }
        return followersId;
    }

    @Override
    public void linkNotificationToUser(int notificationId, int userId) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO user_notification (user_id, notification_id) VALUES (?, ?)";
        System.out.println("INSERT INTO user_notification (" + userId + ", " + notificationId + ")");
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, notificationId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.linkNotificationToUser: " + e);
        }
    }

    @Override
    public void linkNotificationToArtist(int notificationId, int artistId) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO artist_notification (artist_id, notification_id) VALUES (?, ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, artistId);
            pstmt.setInt(2, notificationId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in NotificationDAO.linkNotificationToArtist: " + e);
        }
    }

    @Override
    public int getLastId() {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT MAX(id) FROM notification";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (Exception e) {
            System.out.println("Error in MusicDAOPG.getLastId : " + e);
        }
        return 0;
    }
}