package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private TextField musicFileField;

    @FXML
    private TextField coverImageFileField;  // Champ pour le fichier d'image

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
        String filePath = musicFileField.getText();
        String coverImagePath = coverImageFileField.getText();

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
                if (!coverImagePath.isEmpty()) {
                    File coverFile = new File(coverImagePath);
                    if (coverFile.exists() && coverFile.isFile()) {
                        coverImage = Files.readAllBytes(coverFile.toPath());
                    } else {
                        uploadStatusLabel.setText("Invalid cover image file.");
                        return;
                    }
                }

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
            uploadStatusLabel.setText("Invalid music file path.");
        }
    }

    @FXML
    private void handleSelectMusicFile() {
        Stage stage = (Stage) musicTitleField.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            musicFileField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleSelectCoverImage() {
        Stage stage = (Stage) musicTitleField.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            coverImageFileField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void goToHome(MouseEvent mouseEvent) {
        try {
            // Charger le fichier FXML de la page d'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/MainPage.fxml"));
            Parent root = loader.load();

            // Obtenir la fenêtre actuelle
            Stage stage = (Stage) musicTitleField.getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
