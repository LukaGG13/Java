package org.example.java.ui.interfaces;

import javafx.scene.Node;

public interface Displayable {
    <T extends Node> T display();
}
