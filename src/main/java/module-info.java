module com.alesandro.olimpiadas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.alesandro.olimpiadas to javafx.fxml;
    exports com.alesandro.olimpiadas;
    exports com.alesandro.olimpiadas.controller;
    exports com.alesandro.olimpiadas.model;
    exports com.alesandro.olimpiadas.dao;
    opens com.alesandro.olimpiadas.controller to javafx.fxml;
    exports com.alesandro.olimpiadas.language;
    opens com.alesandro.olimpiadas.language to javafx.fxml;
}