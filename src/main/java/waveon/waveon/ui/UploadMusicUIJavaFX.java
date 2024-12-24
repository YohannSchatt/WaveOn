package waveon.waveon.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import waveon.waveon.bl.UploadMusicFacade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UploadMusicUIJavaFX extends Application implements IUploadMusicController {
    private final UploadMusicFacade uploadMusicFacade = new UploadMusicFacade();
    private static final int MAX_FILE_SIZE_MB = 10; // Maximum allowed size in MB
    private static final int MAX_IMAGE_SIZE_MB = 5; // Maximum image size in MB

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Upload Music");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Title input
        Label titleLabel = new Label("Title:");
        GridPane.setConstraints(titleLabel, 0, 0);
        TextField titleInput = new TextField();
        GridPane.setConstraints(titleInput, 1, 0);

        // Music file input
        Label fileLabel = new Label("File:");
        GridPane.setConstraints(fileLabel, 0, 1);
        TextField fileInput = new TextField();
        fileInput.setEditable(false); // Make the field read-only
        GridPane.setConstraints(fileInput, 1, 1);
        Button browseButton = new Button("Browse");
        GridPane.setConstraints(browseButton, 2, 1);


        browseButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Music File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.flac"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                if (selectedFile.length() <= MAX_FILE_SIZE_MB * 1024 * 1024) {
                    fileInput.setText(selectedFile.getAbsolutePath());
                } else {
                    showAlert("File too large", "The selected file exceeds the maximum size of " + MAX_FILE_SIZE_MB + " MB.");
                }
            }
        });

        // Cover image input
        Label coverLabel = new Label("Cover Image:");
        GridPane.setConstraints(coverLabel, 0, 2);
        TextField coverInput = new TextField();
        coverInput.setEditable(false); // Make the field read-only
        GridPane.setConstraints(coverInput, 1, 2);
        Button browseImageButton = new Button("Browse Image");
        GridPane.setConstraints(browseImageButton, 2, 2);

        browseImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Cover Image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                if (selectedFile.length() <= MAX_IMAGE_SIZE_MB * 1024 * 1024) {
                    coverInput.setText(selectedFile.getAbsolutePath());
                } else {
                    showAlert("Image too large", "The selected image exceeds the maximum size of " + MAX_IMAGE_SIZE_MB + " MB.");
                }
            }
        });

        // Upload button and result label
        Button uploadButton = new Button("Upload");
        GridPane.setConstraints(uploadButton, 1, 3);
        Label resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 1, 4);

        uploadButton.setOnAction(event -> {
            String title = titleInput.getText();
            String filePath = fileInput.getText();
            String coverPath = coverInput.getText();

            // Lire le fichier audio en binaire
            byte[] fileContent = null;
            try (FileInputStream fis = new FileInputStream(filePath)) {
                fileContent = fis.readAllBytes();
            } catch (IOException e) {
                showAlert("Error", "Failed to read the file.");
                return;
            }

            // Lire l'image de la pochette en binaire
            byte[] coverImage = null;
            try (FileInputStream fis = new FileInputStream(coverPath)) {
                coverImage = fis.readAllBytes();
            } catch (IOException e) {
                showAlert("Error", "Failed to read the cover image.");
                return;
            }

            // Upload du fichier and cover binaire
            if (uploadMusicFacade.uploadMusic(title, fileContent, coverImage)) {
                resultLabel.setText("Upload successful");
                goToMainPage(primaryStage); // Redirect to MainPageUI
            } else {
                resultLabel.setText("Upload failed");
            }
        });

        grid.getChildren().addAll(titleLabel, titleInput, fileLabel, fileInput, browseButton, coverLabel, coverInput, browseImageButton, uploadButton, resultLabel);

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void goToMainPage(Stage primaryStage) {
        MainpageUIJavaFX mainPageUI = new MainpageUIJavaFX();
        try {
            mainPageUI.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to navigate to the Main page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void launch() {
        launch();
    }

    @Override
    public void handleUpload() {
        // Already implemented via the JavaFX event handler
    }
}