module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    opens com.handen.lab to javafx.fxml;
    exports com.handen.lab;
    exports com.handen.lab.controller;
    exports com.handen.lab.data;
    exports com.handen.lab.model;
    exports com.handen.lab.utils;
    exports com.handen.lab.view;
    opens com.handen.lab.controller to javafx.fxml;

}