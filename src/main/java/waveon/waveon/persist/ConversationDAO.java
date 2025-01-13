package waveon.waveon.persist;

import waveon.waveon.core.Conversation;
import waveon.waveon.core.Message;

import java.util.ArrayList;

public interface ConversationDAO {

    Conversation getConversationByUser(int id_user1, int id_user2);

    Conversation getConversationById(int id);

    boolean deleteMessage(int id);

    boolean sendMessage(int id, int author_id, String message);

    boolean createConversation(int id_user1, int id_user2);

    public ArrayList<Message> getMessagesByConversationId(int id);

}
