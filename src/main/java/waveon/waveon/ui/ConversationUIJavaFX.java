package waveon.waveon.ui;

import javafx.scene.control.TextField;
import waveon.waveon.bl.ConversationFacade;
import waveon.waveon.core.Conversation;
import waveon.waveon.core.IUser;
import waveon.waveon.core.Message;
import waveon.waveon.core.Music;

import java.io.File;

public class ConversationUIJavaFX implements IConversationController {
    private TextField brouillon;
    private File[] attachments = new File[100];
    private TextField searchBarConversation;
    private ConversationFacade conversationFacade = new ConversationFacade();
    private IUser otherUser;

    @Override
    public void createConversation(IUser withUser) {
        conversationFacade.addConversation(withUser);
        otherUser = withUser;
        System.out.println("Conversation créée avec " + withUser.getUsername());
    }

    @Override
    public void sendMessage() {
        String messageText = brouillon.getText();
        if (messageText != null && !messageText.trim().isEmpty()) {
            // Créer un message avec le texte
            Message message = new Message(messageText); // Assurez-vous que Message a un constructeur avec du texte

            if (otherUser != null) {
                // Envoyer le message via ConversationFacade
                conversationFacade.sendMessage(otherUser, message);
                System.out.println("Message envoyé à " + otherUser.getUsername() + " : " + messageText);
            } else {
                System.out.println("Aucun utilisateur spécifié pour envoyer le message.");
            }
        } else {
            System.out.println("Le message est vide, veuillez saisir un message.");
        }
    }

    @Override
    public void moveToTrash() {
        boolean success = conversationFacade.moveConversationToTrash();
        if (success) {
            System.out.println("Conversation déplacée vers la corbeille.");
        } else {
            System.out.println("Erreur lors du déplacement de la conversation.");
        }
    }

    public void shareMusic() {
        // Partager un fichier musical
        Music musicFile = new Music(); // Assurez-vous que Music a un constructeur par défaut
        conversationFacade.shareFile(musicFile);
        System.out.println("Fichier musical partagé.");
    }

    @Override
    public void searchConversation() {
        String searchQuery = searchBarConversation.getText();
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            Conversation result = conversationFacade.searchConversation(searchQuery);
            if (result != null) {
                System.out.println("Conversation trouvée : " + result);
            } else {
                System.out.println("Aucune conversation trouvée pour : " + searchQuery);
            }
        } else {
            System.out.println("Veuillez entrer un texte de recherche.");
        }
    }
}