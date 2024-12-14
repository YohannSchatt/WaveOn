package waveon.waveon.persist;

import waveon.waveon.core.Conversation;
import waveon.waveon.core.IUser;

public interface ConversationDAO {
    Conversation getConversationByUser(IUser user);
}