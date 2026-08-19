package com.advancedredeem.gui;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.code.RedeemCode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class RedeemGui {

    private final AdvancedRedeemPlugin plugin;

    public RedeemGui(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;
    }

    public void openEditor(
            Player player,
            String codeName
    ) {

        RedeemCode code =
                plugin.codes().get(codeName);

        if (code == null) {
            player.sendMessage(
                    ChatColor.RED
                            + "Code không tồn tại."
            );
            return;
        }

        Inventory inventory =
                Bukkit.createInventory(
                        new GuiHolder(
                                MenuType.EDITOR
                        ),
                        54,
                        ChatColor.DARK_GRAY
                                + "Code: "
                                + code.getCode()
                );

        inventory.setItem(
                10,
                GuiItem.create(
                        GuiItems.EXPIRATION,
                        "&eThời gian hết hạn",
                        "&7Click để chỉnh"
                )
        );

        inventory.setItem(
                12,
                GuiItem.create(
                        GuiItems.MAX_USES,
                        "&eTổng lượt nhập",
                        "&7Hiện tại: &f"
                                + code.getMaxUses()
                )
        );

        inventory.setItem(
                14,
                GuiItem.create(
                        GuiItems.PLAYER_USES,
                        "&eLượt nhập mỗi người",
                        "&7Hiện tại: &f"
                                + code.getMaxUsesPerPlayer()
                )
        );

        inventory.setItem(
                28,
                GuiItem.create(
                        GuiItems.REWARDS,
                        "&aPhần thưởng",
                        "&7Đang có: &f"
                                + code.getRewards().size()
                )
        );

        inventory.setItem(
                30,
                GuiItem.create(
                        GuiItems.CONDITIONS,
                        "&bĐiều kiện",
                        "&7Đang có: &f"
                                + code.getConditions().size()
                   )
        );

        inventory.setItem(
                49,
                GuiItem.create(
                        GuiItems.CLOSE,
                        "&cĐóng"
                )
        );

        player.openInventory(inventory);
    }

    public void openRewards(
            Player player,
            RedeemCode code
    ) {

        Inventory inventory =
                Bukkit.createInventory(
                        new GuiHolder(
                                MenuType.REWARDS
                        ),
                        54,
                        ChatColor.DARK_GREEN
                                + "Rewards: "
                                + code.getCode()
                );

        inventory.setItem(
                10,
                GuiItem.create(
                        GuiItems.COMMAND,
                        "&eThưởng bằng lệnh",
                        "&7Click để thêm command"
                )
        );

        inventory.setItem(
                12,
                GuiItem.create(
                        GuiItems.ITEM,
                        "&bThưởng bằng item",
                        "&7Click để thêm item"
                )
        );

        int slot = 18;

        for (int i = 0;
             i < code.getRewards().size()
                     && slot < 45;
             i++, slot++) {

            inventory.setItem(
                    slot,
                    GuiItem.create(
                            org.bukkit.Material.PAPER,
                            "&fReward #" + (i + 1),
                            "&7Type: &f"
                                    + code.getRewards()
                                    .get(i)
                                    .type(),
                            "&cClick để xóa"
                    )
            );
        }

        inventory.setItem(
                49,
                GuiItem.create(
                        GuiItems.BACK,
                        "&eQuay lại"
                )
        );

        player.openInventory(inventory);
    }

    public void openConditions(
            Player player,
            RedeemCode code
    ) {

        Inventory inventory =
                Bukkit.createInventory(
                        new GuiHolder(
                                MenuType.CONDITIONS
                        ),
                        54,
                        ChatColor.DARK_AQUA
                                + "Conditions"
                );

        inventory.setItem(
                10,
                GuiItem.create(
                        GuiItems.ADD,
                        "&aThêm điều kiện"
                )
        );

        int slot = 18;

        for (int i = 0;
             i < code.getConditions().size()
                     && slot < 45;
             i++, slot++) {

            inventory.setItem(
                    slot,
                    GuiItem.create(
                            org.bukkit.Material.PAPER,
                            "&bCondition #"
                                    + (i + 1),
                            "&7"
                                    + code.getConditions()
                                    .get(i)
                                    .description(),
                            "&cClick để xóa"
                    )
            );
        }

        inventory.setItem(
                49,
                GuiItem.create(
                        GuiItems.BACK,
                        "&eQuay lại"
                )
        );

        player.openInventory(inventory);
    }
}