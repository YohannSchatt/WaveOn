package waveon.waveon.persist;

import waveon.waveon.core.Conversation;
import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.IUser;
import waveon.waveon.core.Message;
import waveon.waveon.core.OrdUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConversationDAOPG implements ConversationDAO {

    public ConversationDAOPG() {}

    public Conversation getConversationByUser(int id_user1, int id_user2) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM conversation WHERE (user_id1 = ? AND user_id2 = ?) OR (user_id1 = ? AND user_id2 = ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_user1);
            pstmt.setInt(2, id_user2);
            pstmt.setInt(3, id_user2);
            pstmt.setInt(4, id_user1);
            pstmt.executeQuery();
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int conversationId = rs.getInt("id");
                Conversation conversation = new Conversation(conversationId);
                OrdUser user1 = new OrdUser(rs.getInt("user_id1"));
                OrdUser user2 = new OrdUser(rs.getInt("user_id2"));
                OrdUser[] users = new OrdUser[]{user1, user2};
                conversation.setUsers(users);
                return conversation;
            }
        } catch (SQLException e) {
            System.out.println("Error in ConversationDAOPG.getConversation: " + e);
            return null;
        }
        return null;
    }

    @Override
    public boolean createConversation(int id_user1, int id_user2) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO conversation (user_id1, user_id2) VALUES (?, ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_user1);
            pstmt.setInt(2, id_user2);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in ConversationDAOPG.createConversation: " + e);
            return false;
        }
    }

    @Override
    public Conversation getConversationById(int id) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM conversation WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int conversationId = rs.getInt("id");
                Conversation conversation = new Conversation(conversationId);
                OrdUser user1 = new OrdUser(rs.getInt("user_id1"));
                OrdUser user2 = new OrdUser(rs.getInt("user_id2"));
                OrdUser[] users = new OrdUser[]{user1, user2};
                conversation.setUsers(users);
                return conversation;
            }
        } catch (SQLException e) {
            System.out.println("Error in ConversationDAOPG.getConversationById: " + e);
        }
        return null;
    }

    public ArrayList<Message> getMessagesByConversationId(int id) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "SELECT * FROM message WHERE conversation_id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Message> messages = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContent(rs.getString("content"));
                message.setDate(rs.getDate("date"));
                message.setAuthor(new OrdUser(rs.getInt("author_id")));
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            System.out.println("Error in ConversationDAOPG.getMessagesByConversationId: " + e);
        }
        return null;
    }

    public boolean deleteMessage(int id) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "DELETE FROM message WHERE id = ?";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in ConversationDAOPG.deleteMessage: " + e);
            return false;
        }
    }

    public boolean sendMessage(int id, int author_id, String message) {
        PGconnector pg = PGconnector.getInstance();
        String sql = "INSERT INTO message (conversation_id, author_id, content) VALUES (?, ?, ?)";
        try (Connection conn = pg.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, 1);
            pstmt.setString(3, message);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error in ConversationDAOPG.addMessage: " + e);
            return false;
        }
    }
}
