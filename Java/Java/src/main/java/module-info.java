module org.example.java {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires net.datafaker;


    opens org.example.java to javafx.fxml;
    exports org.example.java;
}