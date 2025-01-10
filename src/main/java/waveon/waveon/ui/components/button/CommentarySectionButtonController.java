package waveon.waveon.ui.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CommentarySectionButtonController {

    @FXML
    private Button commentarySectionButton;

    @FXML
    private void initialize() {
        assert commentarySectionButton != null;
        commentarySectionButton.setOnMouseClicked(e -> loadMusicCommentsPage());
    }

    private void loadMusicCommentsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/MusicCommentsPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) commentarySectionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}