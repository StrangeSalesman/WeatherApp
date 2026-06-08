module de.tha.prog2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.tha.prog2 to javafx.fxml;
    exports de.tha.prog2;
    exports de.tha.prog2.model;
    opens de.tha.prog2.model to javafx.fxml;
}