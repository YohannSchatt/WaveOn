package waveon.waveon.ui;

import waveon.waveon.core.IUser;

public interface IConversationController {
    void createConversation(IUser withUser);
    void sendMessage();
    void moveToTrash();
    void shareMusic();
    void searchConversation();
}

