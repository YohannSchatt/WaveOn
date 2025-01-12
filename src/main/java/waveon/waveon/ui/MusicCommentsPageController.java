package waveon.waveon.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import waveon.waveon.bl.MusicCommentsFacade;
import waveon.waveon.persist.OrdUserDAOPG;
import waveon.waveon.core.Comments;
import waveon.waveon.core.Music;

import java.io.IOException;
import java.util.ArrayList;

public class MusicCommentsPageController {
    @FXML
    private ComboBox<String> musicComboBox;
    @FXML
    private ListView<HBox> commentsListView;
    @FXML
    private TextField commentTextField;
    @FXML
    private Button backToMainPageButton;

    private MusicCommentsFacade musicCommentsFacade = new MusicCommentsFacade();
    private int userId = 2; // Assuming userId is available, here we use a dummy userId (e.g., 2)

    @FXML
    public void initialize() {
        // Load music titles into the ComboBox
        ArrayList<Music> allMusic = musicCommentsFacade.getAllMusics();
        for (Music music : allMusic) {
            musicComboBox.getItems().add(music.getTitle());
        }

        // Add listener to auto-load comments when a music item is selected
        musicComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> loadComments());
    }

    @FXML
    private void loadComments() {
        String selectedMusicTitle = musicComboBox.getValue();
        if (selectedMusicTitle != null) {
            Music selectedMusic = musicCommentsFacade.getMusicByTitle(selectedMusicTitle);
            if (selectedMusic != null) {
                ArrayList<Comments> comments = musicCommentsFacade.getCommentsForMusic(selectedMusic.getId());
                commentsListView.setItems(FXCollections.observableArrayList());
                for (Comments comment : comments) {
                    HBox hBox = new HBox();
                    String username = OrdUserDAOPG.getUserById(comment.getIDUser()).getUsername();
                    Text commentText = new Text(comment.getContent() + " - " + username);
                    hBox.getChildren().add(commentText);

                    if (comment.getIDUser() == userId) {
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> {
                            musicCommentsFacade.deleteCommentary(comment.getId());
                            loadComments(); // Reload comments to reflect the deletion
                        });
                        hBox.getChildren().add(deleteButton);
                    }

                    commentsListView.getItems().add(hBox);
                }
            }
        }
    }

    @FXML
    private void addComment() {
        String selectedMusicTitle = musicComboBox.getValue();
        String commentContent = commentTextField.getText();
        if (selectedMusicTitle != null && commentContent != null && !commentContent.trim().isEmpty()) {
            Music selectedMusic = musicCommentsFacade.getMusicByTitle(selectedMusicTitle);
            if (selectedMusic != null) {
                musicCommentsFacade.addCommentary(commentContent, userId, selectedMusic.getId());
                loadComments(); // Reload comments to show the new comment
                commentTextField.clear(); // Clear the text field after adding the comment
            }
        }
    }

    @FXML
    private void handleBackToMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/MainPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) backToMainPageButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}