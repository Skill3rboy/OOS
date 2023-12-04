module OOS.P5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;

    opens com.oos.praktikum5 to javafx.fxml;
    opens bank;
    opens bank.exceptions;
    exports bank.exceptions;
    exports bank;
    exports com.oos.praktikum5;
}