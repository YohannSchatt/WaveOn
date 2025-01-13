package waveon.waveon.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import waveon.waveon.core.Conversation;
import waveon.waveon.core.Message;
import waveon.waveon.bl.SearchFacade;
import waveon.waveon.bl.ConversationFacade;
import waveon.waveon.core.OrdUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PrivateMessagePageController implements IPrivateMessageontroller {

    @FXML
    private TextField brouillon;

    @FXML
    private Button send;

    @FXML
    private ListView<Message> conversation;

    @FXML
    private TextField searchField;

    @FXML
    private Button search;

    @FXML
    private ListView<String> listUser;


    Map<String,Integer> userMap = new HashMap<String,Integer>();

    @FXML
    public void initialize() {
        listUser.setOnMouseClicked(this::handleUserClick);
        ConversationFacade conversationFacade = ConversationFacade.getInstace();
        assert conversationFacade != null;
        conversationFacade.setPrivateMessageController(this);
    }

    public void sendMessage() {
        ConversationFacade conversationFacade = ConversationFacade.getInstace();
        boolean result = conversationFacade.sendMessage(brouillon.getText());
        if (result) {
            conversation.getItems().add(new Message(brouillon.getText(), true));
            brouillon.clear();
            displayMessage();
        }
    }

    public void receiveMessage(ArrayList<Message> listMessage) {
        for (Message message : listMessage) {
            String display = message.Author + " : " + message.content;
            conversation.getItems().add(message);
        }
        displayMessage();
    }

    private void displayMessage() {
        ObservableList<Message> messages = FXCollections.observableArrayList();
        for (Message message : conversation.getItems()) {
            messages.add(message);
        }
        conversation.setItems(messages);
    }

    public void searchUser() {
        SearchFacade searchFacade = new SearchFacade();
        searchFacade.searchOrdUser(searchField.getText());
        ArrayList<OrdUser> listUser = searchFacade.getCurrentOrdUserSearch();
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (OrdUser user : listUser) {
            userMap.put(user.getUsername(), user.getId());
            userNames.add(user.getUsername());
        }
        this.listUser.setItems(userNames);
    }

    private void handleUserClick(MouseEvent event) {
        System.out.println("User clicked");
        String selectedUsername = (String) listUser.getSelectionModel().getSelectedItem();
        if (selectedUsername != null) {
            System.out.println("Selected user: " + selectedUsername);
            Integer userId = userMap.get(selectedUsername);
            if (userId != null) {
                executeQueryWithUserId(userId);
            }
        }
    }

    private void executeQueryWithUserId(Integer userId) {
        ConversationFacade conversationFacade = ConversationFacade.getInstace();
        boolean conversationExist = conversationFacade.getConversationByUser(userId);
        if (conversationExist) {
            conversationFacade.start();
        }
    }

    @FXML
    public void onClose(WindowEvent event) {
        ConversationFacade conversationFacade = ConversationFacade.getInstace();
        if (conversationFacade != null) {
            conversationFacade.stop();
        }
    }
}
