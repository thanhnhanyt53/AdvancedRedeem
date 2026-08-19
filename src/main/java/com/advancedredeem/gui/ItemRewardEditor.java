package com.advancedredeem.gui;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.item.ItemProvider;
import com.advancedredeem.reward.ItemReward;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ItemRewardEditor {

    private final AdvancedRedeemPlugin plugin;

    public ItemRewardEditor(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;
    }

    public boolean addHeldItem(
            Player player,
            String codeName
    ) {

        ItemStack item =
                player.getInventory()
                        .getItemInMainHand();

        if (item.getType().isAir()) {
            return false;
        }

        var code =
                plugin.codes().get(codeName);

        if (code == null) {
            return false;
        }

        code.getRewards().add(
                new ItemReward(item)
        );

        plugin.codes().markDirty(code);

        return true;
    }

    public boolean addCustomItem(
            Player player,
            String codeName,
            String providerId,
            String identifier
    ) {

        ItemProvider provider =
                plugin.items()
                        .get(providerId);

        if (provider == null ||
                !provider.isAvailable()) {

            player.sendMessage(
                    ChatColor.RED
                            + "Item provider không khả dụng."
            );

            return false;
        }

        ItemStack item;

        try {
            item =
                    provider.create(identifier);
        } catch (Throwable throwable) {

            player.sendMessage(
                    ChatColor.RED
                            + "Provider lỗi khi tạo item."
            );

            return false;
        }

        if (item == null ||
                item.getType().isAir()) {

            player.sendMessage(
                    ChatColor.RED
                            + "Không tìm thấy custom item."
            );

            return false;
        }

        var code =
                plugin.codes().get(codeName);

        if (code == null) {
            return false;
        }

        code.getRewards().add(
                new ItemReward(item)
        );

        plugin.codes().markDirty(code);

        return true;
    }
}