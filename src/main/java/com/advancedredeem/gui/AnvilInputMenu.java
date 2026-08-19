package com.advancedredeem.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public final class AnvilInputMenu {

    private final Player player;

    private final String initialText;

    private final Consumer<String> callback;

    public AnvilInputMenu(
            Player player,
            String initialText,
            Consumer<String> callback
    ) {
        this.player = player;
        this.initialText = initialText;
        this.callback = callback;
    }

    public void open() {

        Inventory inventory =
                Bukkit.createInventory(
                        new GuiHolder(
                                MenuType.ANVIL_INPUT
                        ),
                        org.bukkit.event.inventory.InventoryType.ANVIL,
                        ChatColor.DARK_GRAY
                                + "Nhập giá trị"
                );

        ItemStack input =
                new ItemStack(Material.PAPER);

        ItemMeta meta =
                input.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(
                    initialText == null
                            ? ""
                            : initialText
            );

            input.setItemMeta(meta);
        }

        inventory.setItem(
                0,
                input
        );

        player.openInventory(inventory);
    }

    public static String read(
            Inventory inventory
    ) {

        if (!(inventory
                instanceof AnvilInventory anvil)) {
            return "";
        }

        String text =
                anvil.getRenameText();

        return text == null
                ? ""
                : text;
    }

    public void complete(
            String value
    ) {

        if (value == null) {
            return;
        }

        callback.accept(value);
    }
}