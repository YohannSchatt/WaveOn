package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import waveon.waveon.bl.SearchFacade;
import waveon.waveon.bl.UserSessionFacade;
import waveon.waveon.core.Artist;
import waveon.waveon.core.Music;
import waveon.waveon.core.OrdUser;
import waveon.waveon.core.User;

import java.io.IOException;
import java.util.List;

public class ProfilePageController {

    SearchFacade searchFacade = new SearchFacade();

    @FXML
    public HBox hbox;

    @FXML
    public ListView<String> listMusic;

    @FXML
    public Button subscribe;

    @FXML
    public Label nameArtist;

    @FXML
    public Label followers;


    public void initialize() {
        Artist artist = SearchFacade.getProfilePageInfo();
        System.out.println("Artist: " + artist.username);

        nameArtist.setText(nameArtist.getText() + " " + artist.username);
        followers.setText(followers.getText() + " " + artist.getNbSubscribers());
        switchButtonSubscriber();
        updateListMusic();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waveon/waveon/components/button/homeButton.fxml"));
            hbox.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateSubscribers(){
        followers.setText("Followers : " + (SearchFacade.getProfilePageInfo().getNbSubscribers()));
    }

    public void switchButtonSubscriber(){
        if(UserSessionFacade.getCurrentUser() instanceof Artist){
            subscribe.setVisible(false);
        }
        else {
            if ((UserSessionFacade.getCurrentUser()).isSubscribe(SearchFacade.getProfilePageInfo())) {
                subscribe.setText("Unsubscribe");
                subscribe.setStyle("-fx-background-color: red");
                subscribe.setOnAction(event -> {
                    UserSessionFacade userSessionFacade = UserSessionFacade.getInstance();
                    userSessionFacade.removeFollow(SearchFacade.getProfilePageInfo().id);
                    this.searchFacade.getAllInfoArtistById(SearchFacade.getProfilePageInfo().id);
                    updateSubscribers();
                    switchButtonSubscriber();
                });
            } else {
                subscribe.setText("Subscribe");
                subscribe.setStyle("-fx-background-color: green");
                subscribe.setOnAction(event -> {
                    UserSessionFacade userSessionFacade = UserSessionFacade.getInstance();
                    userSessionFacade.addFollow(SearchFacade.getProfilePageInfo().id);
                    this.searchFacade.getAllInfoArtistById(SearchFacade.getProfilePageInfo().id);
                    updateSubscribers();
                    switchButtonSubscriber();
                });
            }
        }
    }

    private void updateListMusic() {
        listMusic.getItems().clear();
        List<Music> musics = SearchFacade.getProfilePageInfo().getMusics();
        List<String> musicsName = new java.util.ArrayList<>();
        for (Music music : musics) {
            musicsName.add(music.getTitle());
        }
        for (String music : musicsName) {
            listMusic.getItems().add(music);
        }
    }

}
