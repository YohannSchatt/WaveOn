package waveon.waveon.ui.stage;

import javafx.stage.Stage;

public class mainStage {

    static private mainStage Instance;

    private Stage primaryStage = new Stage();

    private mainStage(){}

    static public mainStage getInstance(){
        if (Instance == null){
            Instance = new mainStage();
        }
        return Instance;
    }

    static public Stage getPrimaryStage(){
        if (Instance == null){
            Instance = new mainStage();
        }
        return Instance.primaryStage;
    }
}
