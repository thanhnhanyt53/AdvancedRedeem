package com.advancedredeem.condition;

import org.bukkit.entity.Player;

public final class PermissionCondition
        implements RedeemCondition {

    private final String permission;

    public PermissionCondition(
            String permission
    ) {
        this.permission = permission;
    }

    @Override
    public String type() {
        return "permission";
    }

    @Override
    public boolean check(Player player) {
        return player.hasPermission(permission);
    }

    @Override
    public String description() {
        return "Permission: " + permission;
    }
}