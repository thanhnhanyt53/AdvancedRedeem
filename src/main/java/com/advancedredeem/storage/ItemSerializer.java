package com.advancedredeem.storage;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public final class ItemSerializer {

    private ItemSerializer() {
    }

    public static Map<String, Object> serialize(
            ItemStack item
    ) {

        if (item == null) {
            throw new IllegalArgumentException(
                    "Item cannot be null"
            );
        }

        return item.serialize();
    }

    public static ItemStack deserialize(
            Object object
    ) {

        if (object instanceof ItemStack item) {
            return item.clone();
        }

        if (object instanceof Map<?, ?> map) {

            @SuppressWarnings("unchecked")
            Map<String, Object> typed =
                    (Map<String, Object>) map;

            return ItemStack.deserialize(typed);
        }

        throw new IllegalArgumentException(
                "Invalid serialized ItemStack"
        );
    }
}