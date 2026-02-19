package org.example.java.controllers;

import org.example.java.entity.room.Room;
import org.example.java.ui.RoomUiAdapter;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

//OVA star nije contorller nego nesto sta kontorller prima hmm i onda controller vec ima definirano da prduca to
//TODO: rename
public abstract class EditAndCreateController<T,U>  {

    private final Function<T,U> entityToAdapter;
    private final Supplier<T> entitySupplier;
    private final Consumer<U> buttonAction; //can be save or update

    public EditAndCreateController(Function<T, U> entityToAdapter, Supplier<T> entitySupplier, Consumer<U> buttonAction ) {

        this.entityToAdapter = entityToAdapter;
        this.entitySupplier = entitySupplier;
        this.buttonAction = buttonAction;

    }
    public class Control {
       public Control(EditAndCreateController<Room, RoomUiAdapter> handle) {
         //za edit function -> uvijek sebe;
           // za stvranje uvjek novi sa binding
           // i action
           // za review treba nacin da se dobije users i rooms -> build pattern
       }
    }
}
