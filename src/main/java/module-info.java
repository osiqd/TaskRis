module app.decorator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens app.decorator to javafx.fxml;
    exports app.decorator;
}