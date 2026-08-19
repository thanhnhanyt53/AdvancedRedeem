package com.advancedredeem.reward;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public final class ItemReward implements Reward {

    private final ItemStack item;

    public ItemReward(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            throw new IllegalArgumentException(
                    "Invalid reward item"
            );
        }

        this.item = item.clone();
    }

    public ItemStack item() {
        return item.clone();
    }

    @Override
    public String type() {
        return "item";
    }

    @Override
    public boolean give(Player player) {

        ItemStack reward = item.clone();

        Map<Integer, ItemStack> leftovers =
                player.getInventory()
                        .addItem(reward);

        /*
         * Never silently destroy a reward.
         *
         * If inventory is full, drop the remaining
         * stack at the player's location.
         */
        for (ItemStack leftover :
                leftovers.values()) {

            player.getWorld().dropItemNaturally(
                    player.getLocation(),
                    leftover
            );
        }

        return true;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "type",
                type(),
                "item",
                item.clone()
        );
    }
}