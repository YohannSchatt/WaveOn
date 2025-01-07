package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import waveon.waveon.bl.UploadMusicFacade;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadMusicUIJavaFX {

    @FXML
    private TextField musicTitleField;

    @FXML
    private TextField musicArtistField;

    @FXML
    private TextField musicFileField;

    @FXML
    private Label uploadStatusLabel;

    private UploadMusicFacade uploadMusicFacade = new UploadMusicFacade();

    @FXML
    public void initialize() {
        // Vous pouvez initialiser des éléments si nécessaire
    }

    @FXML
    private void handleUploadMusic() {
        String title = musicTitleField.getText();
        String artist = musicArtistField.getText();  // Cette information n'est plus nécessaire, car l'artiste est récupéré automatiquement
        String filePath = musicFileField.getText();

        if (title.isEmpty() || filePath.isEmpty()) {
            uploadStatusLabel.setText("Please fill all fields.");
            return;
        }

        // Lire le fichier de musique
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());

                // Si l'utilisateur a téléchargé une image de couverture
                byte[] coverImage = null;
                // Vous pourriez ajouter un champ ou un FileChooser pour la couverture si nécessaire

                // Utilisation de la façade pour uploader la musique
                boolean success = uploadMusicFacade.uploadMusic(title, fileContent, coverImage);
                if (success) {
                    uploadStatusLabel.setText("Music uploaded successfully.");
                } else {
                    uploadStatusLabel.setText("Failed to upload music.");
                }
            } catch (IOException e) {
                uploadStatusLabel.setText("Error reading file.");
            }
        } else {
            uploadStatusLabel.setText("Invalid file path.");
        }
    }

    @FXML
    private void handleSelectFile() {
        Stage stage = (Stage) musicTitleField.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            musicFileField.setText(selectedFile.getAbsolutePath());
        }
    }

    public void goToHome(MouseEvent mouseEvent) {
        // Vous pouvez ajouter une méthode pour retourner à la page d'accueil si nécessaire
    }
}
