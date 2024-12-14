package waveon.waveon.bl;

import waveon.waveon.core.Conversation;
import waveon.waveon.core.IUser;
import waveon.waveon.core.Message;
import waveon.waveon.core.Music;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ConversationDAO;

public class ConversationFacade {
    private Conversation[] conversations = new Conversation[100];
    private ConversationDAO conversationDAO;

    public ConversationFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        conversationDAO = factory.createConversationDAO();
    }

    public void sendMessage(IUser otherUser, Message message) {
        // Send message logic
    }

    public void addConversation(IUser otherUser) {
        Conversation conversation = conversationDAO.getConversationByUser(otherUser);
        // Add logic to manage new conversation
    }

    public boolean moveConversationToTrash() {
        // Move conversation to trash
        return true;
    }

    public void shareFile(Music musicFile) {
        // Share music file
    }

    public Conversation searchConversation(String search) {
        // Search conversation
        return null;
    }
}