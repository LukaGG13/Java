package org.example.java.components;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class AuthorizedTab extends Tab {
    private final Integer allowedPermissionLevel;
    private final Tab tab;


    public AuthorizedTab(String title, Tab tab, Integer allowedPermissionLevel) {
        super(title);
        this.tab = tab;
        this.allowedPermissionLevel = allowedPermissionLevel;
    }


    public final void fuckYouApiSetContent(Node content) {
        tab.setContent(content);
    }

    public Integer getAllowedPermissionLevel () {
        return allowedPermissionLevel;
    }
}
