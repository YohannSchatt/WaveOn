package waveon.waveon.core;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private IUser[] users = new IUser[2];
    private List<Message> listMessages = new ArrayList<>();

    public Conversation(IUser user) {
        this.users[0] = user;
    }

    public void deleteMessage(Message message) {
        listMessages.remove(message);
    }

    public void sendMessage(Message message) {
        listMessages.add(message);
    }
}