package waveon.waveon.ui.button;

import waveon.waveon.ui.LoginController;
import waveon.waveon.ui.stage.mainStage;

import javafx.stage.Stage;


public class PathLoginButtonController {

    public PathLoginButtonController() {}

    public void goToLogin(){
        LoginController loginPage = new LoginController();
        Stage primaryStage = mainStage.getPrimaryStage();
        try {
            loginPage.start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
