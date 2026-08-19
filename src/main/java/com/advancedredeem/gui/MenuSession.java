package com.advancedredeem.gui;

import java.util.UUID;

public final class MenuSession {

    private final UUID player;

    private String code;

    private MenuType type;

    private String inputAction;

    private int editingIndex = -1;

    public MenuSession(
            UUID player,
            String code,
            MenuType type
    ) {
        this.player = player;
        this.code = code;
        this.type = type;
    }

    public UUID player() {
        return player;
    }

    public String code() {
        return code;
    }

    public void code(String code) {
        this.code = code;
    }

    public MenuType type() {
        return type;
    }

    public void type(MenuType type) {
        this.type = type;
    }

    public String inputAction() {
        return inputAction;
    }

    public void inputAction(String inputAction) {
        this.inputAction = inputAction;
    }

    public int editingIndex() {
        return editingIndex;
    }

    public void editingIndex(int editingIndex) {
        this.editingIndex = editingIndex;
    }
}