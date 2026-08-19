package com.advancedredeem.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class GuiItem {

    private GuiItem() {
    }

    public static ItemStack create(
            Material material,
            String name,
            String... lore
    ) {

        ItemStack item =
                new ItemStack(material);

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        name
                )
        );

        List<String> lines =
                new ArrayList<>();

        for (String line : lore) {

            lines.add(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            line
                    )
            );
        }

        meta.setLore(lines);

        item.setItemMeta(meta);

        return item;
    }
}