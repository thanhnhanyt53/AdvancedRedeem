package com.advancedredeem.reward;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public final class ItemReward
        implements Reward {

    private final ItemStack item;

    public ItemReward(ItemStack item) {
        if (item == null ||
                item.getType().isAir()) {
            throw new IllegalArgumentException(
                    "Invalid item"
            );
        }

        this.item = item.clone();
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

        if (!leftovers.isEmpty()) {

            leftovers.values().forEach(
                    leftover ->
                            player.getWorld()
                                    .dropItemNaturally(
                                            player.getLocation(),
                                            leftover
                                    )
            );
        }

        return true;
    }

    public ItemStack item() {
        return item.clone();
    }
}