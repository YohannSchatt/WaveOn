package waveon.waveon.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import waveon.waveon.bl.MusicCommentaryFacade;
import waveon.waveon.core.Comments;
import waveon.waveon.core.Music;

import java.util.List;

public class MusicCommentsPageController {
    @FXML
    private ComboBox<String> musicComboBox;
    @FXML
    private ListView<HBox> commentsListView;
    @FXML
    private TextField commentTextField;

    private MusicCommentaryFacade musicCommentaryFacade = new MusicCommentaryFacade();

    @FXML
    public void initialize() {
        // Load music titles into the ComboBox
        List<Music> allMusic = musicCommentaryFacade.getAllMusic();
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
            Music selectedMusic = musicCommentaryFacade.getMusicByTitle(selectedMusicTitle);
            if (selectedMusic != null) {
                List<Comments> comments = musicCommentaryFacade.getCommentariesForMusic(selectedMusic.getId());
                commentsListView.setItems(FXCollections.observableArrayList());
                for (Comments comment : comments) {
                    HBox hBox = new HBox();
                    Text commentText = new Text(comment.getContent());
                    hBox.getChildren().add(commentText);
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
            Music selectedMusic = musicCommentaryFacade.getMusicByTitle(selectedMusicTitle);
            if (selectedMusic != null) {
                // Assuming userId is available, here we use a dummy userId (e.g., 1)
                int userId = 1;
                musicCommentaryFacade.addCommentary(commentContent, userId, selectedMusic.getId());
                loadComments(); // Reload comments to show the new comment
                commentTextField.clear(); // Clear the text field after adding the comment
            }
        }
    }

    //Version avec les likes mais pas implementé jusqu'au bout
//    @FXML
//    private void loadComments() {
//        String selectedMusicTitle = musicComboBox.getValue();
//        if (selectedMusicTitle != null) {
//            Music selectedMusic = musicCommentaryFacade.getMusicByTitle(selectedMusicTitle);
//            if (selectedMusic != null) {
//                List<Comments> comments = musicCommentaryFacade.getCommentariesForMusic(selectedMusic.getId());
//                commentsListView.setItems(FXCollections.observableArrayList());
//                for (Comments comment : comments) {
//                    HBox hBox = new HBox();
//                    Text commentText = new Text(comment.getContent());
//                    Button likeButton = new Button("Like (" + comment.getNumberLike() + ")");
//                    if (likedComments.contains(comment.getId())) {
//                        likeButton.setText("Unlike (" + comment.getNumberLike() + ")");
//                    }
//                    likeButton.setOnAction(event -> {
//                        if (likedComments.contains(comment.getId())) {
//                            comment.setNumberLike(comment.getNumberLike() - 1);
//                            likedComments.remove(comment.getId());
//                            likeButton.setText("Like (" + comment.getNumberLike() + ")");
//                        } else {
//                            comment.setNumberLike(comment.getNumberLike() + 1);
//                            likedComments.add(comment.getId());
//                            likeButton.setText("Unlike (" + comment.getNumberLike() + ")");
//                        }
//                    });
//                    hBox.getChildren().addAll(commentText, likeButton);
//                    commentsListView.getItems().add(hBox);
//                }
//            }
//        }
//    }

}


