module org.example.java {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires net.datafaker;
    requires org.junit.jupiter.api;

    opens org.example.java to org.junit.jupiter.engine, javafx.fxml;
    opens org.example.java.entity.interfaces to org.junit.jupiter.engine;

    exports org.example.java;
    exports org.example.java.entity.interfaces;
}