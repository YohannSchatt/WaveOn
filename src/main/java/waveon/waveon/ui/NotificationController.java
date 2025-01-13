package waveon.waveon.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import waveon.waveon.bl.NotificationFacade;
import waveon.waveon.core.Notification;

import java.util.ArrayList;

public class NotificationController implements INotificationController {

    @FXML
    private ListView<HBox> notificationListView = new ListView<>();
    private final NotificationFacade notificationFacade = new NotificationFacade();

    @FXML
    public void initialize() {
        System.out.println(notificationListView);
        assert notificationListView != null;
        loadNotifications();
    }

    @FXML
    private VBox notificationBand;

    void loadNotifications() {
        ArrayList<Notification> notifications = notificationFacade.getNotificationsList();
        notificationListView.getItems().clear();

        for (Notification notification : notifications) {
            HBox hBox = new HBox();
            Label label = new Label(notification.getContent());
            Button deleteButton = new Button("✖");
            deleteButton.setOnAction(event -> {
                notificationFacade.clearNotification(notification.getId());
                loadNotifications();
            });

            hBox.getChildren().addAll(label, deleteButton);
            notificationListView.getItems().add(hBox);
        }
    }

    @FXML
    public void openNotificationCenter() {
        notificationBand.setVisible(true);
    }

    @FXML
    public void closeNotificationCenter() {
        notificationBand.setVisible(false);
    }

    @Override
    public void deleteNotification() {
    }

    @Override
    public void openNotification() {
        // Handle the notification click event
        HBox selectedNotification = notificationListView.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            System.out.println("Notification clicked: " + selectedNotification);
        }
    }
}
