package com.advancedredeem.gui;

import com.advancedredeem.AdvancedRedeemPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.entity.Player;

public final class MenuListener
        implements Listener {

    private final AdvancedRedeemPlugin plugin;

    private final MenuSessionManager sessions =
            new MenuSessionManager();

    public MenuListener(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(
            InventoryClickEvent event
    ) {

        if (!(event.getWhoClicked()
                instanceof Player player)) {
            return;
        }

        MenuSession session =
                sessions.get(
                        player.getUniqueId()
                );

        if (session == null) {
            return;
        }

        /*
         * Only GUI top inventory is protected.
         * Player inventory remains usable where appropriate.
         */
        if (event.getRawSlot()
                < event.getView()
                        .getTopInventory()
                        .getSize()) {

            event.setCancelled(true);

            handle(
                    player,
                    session,
                    event
            );
        }
    }

    @EventHandler
    public void onClose(
            InventoryCloseEvent event
    ) {

        if (event.getPlayer()
                instanceof Player player) {

            sessions.close(
                    player.getUniqueId()
            );
        }
    }

    private void handle(
            Player player,
            MenuSession session,
            InventoryClickEvent event
    ) {

        /*
         * Dispatch to the appropriate GUI controller.
         *
         * CREATE
         * EDITOR
         * REWARDS
         * ITEM_REWARD
         * COMMAND_REWARD
         * CONDITIONS
         * CONDITION_EDITOR
         * ECONOMY
         */
    }

    public MenuSessionManager sessions() {
        return sessions;
    }
}