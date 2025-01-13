package waveon.waveon.core;

import java.util.ArrayList;

/**
 * 
 */
public class Conversation {

    /**
     * Default constructor
     */
    public Conversation() {
    }

    /**
     * 
     */
    public  OrdUser[] Users;

    /**
     * 
     */
    public ArrayList<Message> ListMessage;

    public ArrayList<Message> getListMessage() {
        return ListMessage;
    }

    public void setListMessage(ArrayList<Message> listMessage) {
        ListMessage = listMessage;
    }

    public OrdUser[] getUsers() {
        return Users;
    }

    public void setUsers(OrdUser[] users) {
        this.Users = users;
    }

    public void addMessage(Message message) {
        ListMessage.add(message);
    }

    public void removeMessage(Message message) {
        ListMessage.remove(message);
    }

}