package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import waveon.waveon.bl.UserSessionFacade;
import waveon.waveon.core.*;
import waveon.waveon.bl.SearchFacade;


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
    public ListView<String> myMusic;

    @FXML
    public ListView<String> myFollowers;

    @FXML
    public Label result;

    @FXML
    public Accordion information;

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
                    information.setVisible(false);
                }
                else {
                    Artist artist = (Artist) currentUser;
                    SearchFacade searchFacade = new SearchFacade();
                    if(searchFacade.getAllInfoArtistById(artist.getId())) {
                        for (Music music : artist.getMusics()) {
                            myMusic.getItems().add(music.getTitle());
                        }
                        for (OrdUser follower : artist.getSubscribers()) {
                            myFollowers.getItems().add(follower.getUsername());
                        }
                    };

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
