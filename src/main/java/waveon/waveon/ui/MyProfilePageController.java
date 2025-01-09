package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import waveon.waveon.bl.UserSessionFacade;
import waveon.waveon.core.IUser;
import waveon.waveon.core.User;


import java.io.IOException;

public class MyProfilePageController {

    @FXML
    public HBox nav;

    @FXML
    public TextField usernameField;

    @FXML
    public TextField emailField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TitledPane myMusic;

    @FXML
    public TitledPane myFollowers;
    public Label result;

    @FXML
    public void initialize() throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/HomeButton.fxml"));
            nav.getChildren().add(loader.load());

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/LogoutButton.fxml"));
            nav.getChildren().add(loader2.load());


            IUser currentUser = UserSessionFacade.getCurrentUser();
            if(currentUser != null) {
                usernameField.setText(currentUser.getUsername());
                emailField.setText(currentUser.getEmail());
                if (!currentUser.isArtist()) {
                    myMusic.setVisible(false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveModification() {
        result.setText("");

        UserSessionFacade userSessionFacade = UserSessionFacade.getInstance();

        if(userSessionFacade.enregistrerModification(usernameField.getText(), emailField.getText(), passwordField.getText())) {
            result.setText("Modification enregistrée");
        } else {
            result.setText("Erreur lors de l'enregistrement des modifications");
        }
    }

}
