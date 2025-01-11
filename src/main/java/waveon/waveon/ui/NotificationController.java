package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import waveon.waveon.bl.NotificationFacade;

public class NotificationController implements INotificationController {

    NotificationFacade notificationFacade = new NotificationFacade();

    @FXML
    private VBox notificationBand;

    @FXML
    private ListView<String> notificationListView;


    @FXML
    public void openNotificationCenter() {


        notificationBand.setVisible(true);
    }

    @FXML
    public void closeNotificationCenter() {
        notificationBand.setVisible(false);

    }

    @Override
    public void backToNotificationCenter() {

    }

    @Override
    public void createNotification() {

    }

    @Override
    public void deleteNotification() {

    }

    @Override
    public void closeNotification() {

    }

    @Override
    public void openNotification() {
        // Handle the notification click event
        String selectedNotification = notificationListView.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            System.out.println("Notification clicked: " + selectedNotification);
        }
    }
}
