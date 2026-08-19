package com.advancedredeem.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class BukkitItemProvider
        implements ItemProvider {

    @Override
    public String id() {
        return "bukkit";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public ItemStack create(
            String identifier
    ) {

        Material material =
                Material.matchMaterial(
                        identifier
                );

        if (material == null ||
                material.isAir()) {
            return null;
        }

        return new ItemStack(material);
    }
}