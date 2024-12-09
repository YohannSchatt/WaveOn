module waveon.waveon {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens waveon.waveon to javafx.fxml;
    exports waveon.waveon;
}