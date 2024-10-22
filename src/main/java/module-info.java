module com.alesandro.proyecto1olimpiadas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.alesandro.proyecto1olimpiadas to javafx.fxml;
    exports com.alesandro.proyecto1olimpiadas;
    exports com.alesandro.proyecto1olimpiadas.controller;
    exports com.alesandro.proyecto1olimpiadas.model;
    exports com.alesandro.proyecto1olimpiadas.dao;
    opens com.alesandro.proyecto1olimpiadas.controller to javafx.fxml;
    exports com.alesandro.proyecto1olimpiadas.language;
    opens com.alesandro.proyecto1olimpiadas.language to javafx.fxml;
}