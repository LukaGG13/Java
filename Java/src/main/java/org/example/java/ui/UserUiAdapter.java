package org.example.java.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.example.java.entity.admin.Admin;
import org.example.java.entity.guest.Guest;
import org.example.java.entity.user.User;
import org.example.java.ui.interfaces.UiComponent;

import java.util.Set;
import java.util.function.Consumer;

//TODO refactor into two classes
public class UserUiAdapter implements UiComponent {
    private final User user;
    private final TextField tfName = new TextField();
    private final TextField tfAge = new TextField();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty age = new SimpleStringProperty();
    private VBox vbox = null;

    private final Button btDelete = new Button("Delete");
    private final Button btSave = new Button("Save");
    public UserUiAdapter(User user, Consumer<UserUiAdapter> update, Consumer<UserUiAdapter> delete) {
        this.user = user;
        btSave.setOnAction(_ ->  update.accept(this));
        btDelete.setOnAction(_ -> delete.accept(this));

        name.set(user.getName());
        age.set(user.getAge().toString());

        tfName.textProperty().bindBidirectional(name);
        tfAge.textProperty().bindBidirectional(age);
    }

    User toUser() {
        return switch (user) {
            case Admin _ -> new Admin(user.getId(), name.get(), Integer.valueOf(age.get()));
            case Guest _ -> new Guest(user.getId(), name.get(), Integer.valueOf(age.get()));
            case null, default -> throw new IllegalStateException("User is not set");
        };
    }

    @Override
    public VBox display() {
        if (vbox == null || true) {
            var gp = new GridPane();
            gp.add(new Label("Name"), 0, 0);
            gp.add(tfName, 1, 0);

            gp.add(new Label("Age"), 0, 1);
            gp.add(tfAge, 1, 1);

            gp.setHgap(4.0d);
            gp.setVgap(8.0d);

            VBox.setVgrow(gp, Priority.ALWAYS);
            VBox.setMargin( gp, new Insets(40.0d) );

            ButtonBar buttons = new ButtonBar();

            ButtonBar.setButtonData(btSave, ButtonBar.ButtonData.OTHER);

            buttons.getButtons().setAll(btDelete, btSave);
            //buttons.getButtons().add(btDelete)
            //buttons.getButtons().add(btSave);
            buttons.setPadding(new Insets(10.0d) );

            //TODO: chat kaze da je kod garbo idk vjv je
            vbox = new VBox(
                    gp,
                    new Separator(),
                    buttons
            );
            return vbox;

        } else {
            return vbox;
        }
    }

    @Override
    public Set<Pair<String, String>> getKeyWord() {
        return Set.of(new Pair<>("Class", "User"), new Pair<String, String>("Name", user.getName()), new Pair<String, String>("Age", user.getAge().toString()));
    }
}
