package waveon.waveon.persist;

import waveon.waveon.connector.PGconnector;
import waveon.waveon.core.Conversation;
import waveon.waveon.core.IUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConversationDAOPG implements ConversationDAO {
    private Connection connection;

    public ConversationDAOPG() {
        PGconnector pg = PGconnector.getInstance();
        try {
            this.connection = pg.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Conversation getConversationByUser(IUser user) {
        String sql = "SELECT * FROM conversation WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Conversation(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

