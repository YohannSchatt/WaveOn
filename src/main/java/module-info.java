module waveon.waveon {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens waveon.waveon to javafx.fxml;
    exports waveon.waveon;
}