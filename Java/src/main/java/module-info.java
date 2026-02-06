module org.example.java {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires net.datafaker;
    requires org.junit.jupiter.api;
    requires javafx.base;
    requires java.sql;
    requires jakarta.json.bind;
    requires org.eclipse.yasson;
    requires jbcrypt;

    //requires org.example.java;

    opens org.example.java to org.junit.jupiter.engine, javafx.fxml, jakarta.json.bind;
    opens org.example.java.entity.interfaces to org.junit.jupiter.engine;
    //opens org.example.java.entity.user to javafx.base, jakarta.json.bind, org.eclipse.yasson;
    opens org.example.java.entity.user;




    exports org.example.java;
    exports org.example.java.entity.interfaces;
    exports org.example.java.controllers;
    opens org.example.java.controllers to javafx.fxml, org.junit.jupiter.engine;
}