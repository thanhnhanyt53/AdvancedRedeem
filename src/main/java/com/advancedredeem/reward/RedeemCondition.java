package com.advancedredeem.condition;

import org.bukkit.entity.Player;

public interface RedeemCondition {

    String type();

    boolean check(Player player);

    String description();
}