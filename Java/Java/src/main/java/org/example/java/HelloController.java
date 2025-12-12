package org.example.java;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.java.entity.repository.Repository;

public class HelloController {
    @FXML
    private TextField textField;
    @FXML
    private TableView<Object> tableViewId;
    private final Repository repository;

    public HelloController(Repository repository) {
       this.repository = repository;
    }

    public void initialize() {
    }
}
