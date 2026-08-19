package com.advancedredeem.economy;

import org.bukkit.entity.Player;

public interface EconomyProvider {

    String id();

    boolean isAvailable();

    double balance(Player player);

    boolean has(
            Player player,
            double amount
    );
}