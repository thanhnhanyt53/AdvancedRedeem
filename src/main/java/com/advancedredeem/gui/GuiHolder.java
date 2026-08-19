package com.advancedredeem.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class GuiHolder
        implements InventoryHolder {

    private final MenuType type;

    public GuiHolder(MenuType type) {
        this.type = type;
    }

    public MenuType type() {
        return type;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}