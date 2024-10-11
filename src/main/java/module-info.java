module com.alesandro.proyecto1olimpiadas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.alesandro.proyecto1olimpiadas to javafx.fxml;
    exports com.alesandro.proyecto1olimpiadas;
    exports com.alesandro.proyecto1olimpiadas.controller;
    opens com.alesandro.proyecto1olimpiadas.controller to javafx.fxml;
}