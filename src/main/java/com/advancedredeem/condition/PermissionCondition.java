package com.advancedredeem.condition;

import org.bukkit.entity.Player;

import java.util.Map;

public final class PermissionCondition
        implements RedeemCondition {

    private final String permission;

    public PermissionCondition(String permission) {
        if (permission == null || permission.isBlank()) {
            throw new IllegalArgumentException(
                    "Permission cannot be empty"
            );
        }

        this.permission = permission;
    }

    public String permission() {
        return permission;
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

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "type",
                type(),
                "permission",
                permission
        );
    }
}