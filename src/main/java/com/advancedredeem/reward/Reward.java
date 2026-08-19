package com.advancedredeem.reward;

import org.bukkit.entity.Player;

import java.util.Map;

public interface Reward {

    String type();

    boolean give(Player player);

    default Map<String, Object> serialize() {
        return Map.of();
    }
}