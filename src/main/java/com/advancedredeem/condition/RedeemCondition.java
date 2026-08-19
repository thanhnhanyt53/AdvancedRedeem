package com.advancedredeem.condition;

import org.bukkit.entity.Player;

import java.util.Map;

public interface RedeemCondition {

    String type();

    boolean check(Player player);

    String description();

    default Map<String, Object> serialize() {
        return Map.of();
    }
}