module waveon.waveon {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires javafx.media;

    opens waveon.waveon to javafx.fxml;
    exports waveon.waveon.persist;
    opens waveon.waveon.persist to javafx.fxml;
    exports waveon.waveon.core;
    opens waveon.waveon.core to javafx.fxml;
    exports waveon.waveon.ui;
    opens waveon.waveon.ui to javafx.fxml;
    exports waveon.waveon.bl;
    opens waveon.waveon.bl to javafx.fxml;
}