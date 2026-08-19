package com.advancedredeem.gui;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.code.RedeemCode;
import com.advancedredeem.condition.*;
import com.advancedredeem.reward.CommandReward;
import com.advancedredeem.util.DurationParser;
import com.advancedredeem.util.InputParser;
import com.advancedredeem.util.PlaceholderConditionParser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class EditorInputService {

    private final AdvancedRedeemPlugin plugin;

    private final RedeemGui gui;

    public EditorInputService(
            AdvancedRedeemPlugin plugin,
            RedeemGui gui
    ) {
        this.plugin = plugin;
        this.gui = gui;
    }

    public void setExpiration(
            Player player,
            String codeName,
            String input
    ) {

        RedeemCode code =
                plugin.codes().get(codeName);

        if (code == null) {
            return;
        }

        try {

            long seconds =
                    DurationParser.parseSeconds(
                            input
                    );

            if (seconds == 0) {
                code.setExpiresAt(0L);
            } else {
                code.setExpiresAt(
                        System.currentTimeMillis()
                                + seconds * 1000L
                );
            }

            plugin.codes().markDirty(code);

            player.sendMessage(
                    ChatColor.GREEN
                            + "Đã cập nhật thời gian hết hạn."
            );

            gui.openEditor(
                    player,
                    codeName
            );

        } catch (Exception exception) {

            player.sendMessage(
                    ChatColor.RED
                            + "Thời gian không hợp lệ."
            );
        }
    }

    public void setMaxUses(
            Player player,
            String codeName,
            String input
    ) {

        RedeemCode code =
                plugin.codes().get(codeName);

        if (code == null) {
            return;
        }

        Integer value =
                InputParser.integer(input);

        if (value == null || value < 0) {

            player.sendMessage(
                    ChatColor.RED
                            + "Số lượng không hợp lệ."
            );

            return;
        }

        code.setMaxUses(value);

        plugin.codes().markDirty(code);

        gui.openEditor(
                player,
                codeName
        );
    }

    public void setPlayerLimit(
            Player player,
            String codeName,
            String input
    ) {

        RedeemCode code =
                plugin.codes().get(codeName);

        if (code == null) {
            return;
        }

        Integer value =
                InputParser.integer(input);

        if (value == null || value < 0) {

            player.sendMessage(
                    ChatColor.RED
                            + "Số lượng không hợp lệ."
            );

            return;
        }

        code.setMaxUsesPerPlayer(value);

        plugin.codes().markDirty(code);

        gui.openEditor(
                player,
                codeName
        );
    }

    public void addCommandReward(
            Player player,
            String codeName,
            String command
    ) {

        RedeemCode code =
                plugin.codes().get(codeName);

        if (code == null) {
            return;
        }

        try {

            code.getRewards().add(
                    new CommandReward(command)
            );

            plugin.codes().markDirty(code);

            gui.openRewards(
                    player,
                    code
            );

        } catch (Exception exception) {

            player.sendMessage(
                    ChatColor.RED
                            + "Command không hợp lệ."
            );
        }
    }

    public void addHeldItemReward(
            Player player,
            String codeName
    ) {

        RedeemCode code =
                plugin.codes().get(codeName);

        if (code == null) {
            return;
        }

        ItemStack held =
                player.getInventory()
                        .getItemInMainHand();

        if (held.getType().isAir()) {

            player.sendMessage(
                    ChatColor.RED
                            + "Bạn phải cầm item."
            );

            return;
        }

        code.getRewards().add(
                new com.advancedredeem.reward.ItemReward(
                        held
                )
        );

        plugin.codes().markDirty(code);

        gui.openRewards(
                player,
                code
        );
    }

    public void addPlaceholderCondition(
            Player player,
            String codeName,
            String expression
    ) {

        RedeemCode code =
                plugin.codes().get(codeName);

        if (code == null) {
            return;
        }

        try {

            code.getConditions().add(
                    PlaceholderConditionParser
                            .parse(expression)
            );

            plugin.codes().markDirty(code);

            gui.openConditions(
                    player,
                    code
            );

        } catch (Exception exception) {

            player.sendMessage(
                    ChatColor.RED
                            + exception.getMessage()
            );
        }
    }
}