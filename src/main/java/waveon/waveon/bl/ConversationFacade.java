package waveon.waveon.bl;

import waveon.waveon.core.*;
import waveon.waveon.persist.AbstractFactory;
import waveon.waveon.persist.ConversationDAO;
import waveon.waveon.ui.IPrivateMessageontroller;
import waveon.waveon.bl.UserSessionFacade;

import java.util.ArrayList;

public class ConversationFacade {

    private static ConversationFacade instance;

    private Conversation conversation;
    private Thread thread;
    private boolean running = true;

    private IPrivateMessageontroller controller;

    private ConversationFacade() {
        AbstractFactory factory = AbstractFactory.getInstance();
        assert factory != null;
        ConversationDAO conversationDAO = factory.createConversationDAO();
        conversation = null;
    }

    public static ConversationFacade getInstace() {
        if (UserSessionFacade.getCurrentUser() == null) {
            return null;
        }
        else if (ConversationFacade.instance == null) {
            ConversationFacade.instance = new ConversationFacade();
        }
        return ConversationFacade.instance;
    }

    public boolean sendMessage(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        if (this.conversation == null) {
            this.createConversation(this.);
        }
        OrdUser[] users =this.conversation.getUsers();
        if (users[0].getId() == users[1].getId()) {
            return false;
        }
        IUser currentUser = UserSessionFacade.getCurrentUser();
        if(users[0].getId() == currentUser.getId() || users[1].getId() == currentUser.getId()) {
            int userId;
            int otherUserId;
            if (users[0].getId() == currentUser.getId()) {
                userId = users[0].getId();
                otherUserId = users[1].getId();
            }
            else {
                userId = users[1].getId();
                otherUserId = users[0].getId();
            }
            ConversationDAO conversationDAO = AbstractFactory.getInstance().createConversationDAO();
            return conversationDAO.sendMessage(otherUserId, userId, content);
        }
        else {
            return false;
        }
    }

    private boolean createConversation(int otherUserId) {
        int id_user1 = UserSessionFacade.getCurrentUser().getId();
        ConversationDAO conversationDAO = AbstractFactory.getInstance().createConversationDAO();
        boolean result = conversationDAO.createConversation(id_user1, otherUserId);
    }

    public ArrayList<Message> getMessages() {
        ConversationDAO conversationDAO = AbstractFactory.getInstance().createConversationDAO();
        return conversationDAO.getMessagesByConversationId(conversation.getId());
    }

    public boolean getConversationByUser(int otherUserId) {
        int id_user1 = UserSessionFacade.getCurrentUser().getId();
        ConversationDAO conversationDAO = AbstractFactory.getInstance().createConversationDAO();
        conversation = conversationDAO.getConversationByUser(id_user1, otherUserId);
        return conversation != null;
    }

    private void run() {
        while (running) {
            System.out.println("Checking for new messages");
            ArrayList<Message> messages = getMessages();
            IPrivateMessageontroller controller = this.getPrivateMessageController();
            controller.receiveMessage(messages);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void start() {
        System.out.println("Starting conversation thread");
        if (thread == null || !running) {
            running = true;
            thread = new Thread(this::run);
            thread.start();
        }
    }

    public void stop() {
        running = false;
        if (thread != null) {
            System.out.println("Stopping conversation thread");
            ConversationFacade.getInstace().setConversation(null);
            thread.interrupt();
        }
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setPrivateMessageController(IPrivateMessageontroller controller) {
        this.controller = controller;
    }

    public IPrivateMessageontroller getPrivateMessageController() {
        return getPrivateMessageController();
    }



}
