package waveon.waveon.ui;

import waveon.waveon.bl.NotificationFacade;

public interface INotificationController {
    void openNotificationCenter();
    void closeNotificationCenter();
    void backToNotificationCenter();
    void createNotification();
    void deleteNotification();
    void closeNotification();
    void openNotification();

}
