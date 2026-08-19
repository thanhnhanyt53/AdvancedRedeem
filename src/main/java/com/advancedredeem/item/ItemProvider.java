package com.advancedredeem.item;

import org.bukkit.inventory.ItemStack;

public interface ItemProvider {

    String id();

    boolean isAvailable();

    ItemStack create(String identifier);
}