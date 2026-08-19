package com.advancedredeem.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public final class VaultEconomyProvider
        implements EconomyProvider {

    private final Economy economy;

    public VaultEconomyProvider(
            Economy economy
    ) {
        this.economy = economy;
    }

    @Override
    public String id() {
        return "vault";
    }

    @Override
    public boolean isAvailable() {
        return economy != null;
    }

    @Override
    public double balance(Player player) {
        return economy.getBalance(player);
    }

    @Override
    public boolean has(
            Player player,
            double amount
    ) {
        return economy.has(
                player,
                amount
        );
    }
}