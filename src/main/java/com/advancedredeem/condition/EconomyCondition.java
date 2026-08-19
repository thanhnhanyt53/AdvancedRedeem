package com.advancedredeem.condition;

import com.advancedredeem.economy.EconomyProvider;
import org.bukkit.entity.Player;

public final class EconomyCondition
        implements RedeemCondition {

    private final EconomyProvider provider;
    private final double required;

    public EconomyCondition(
            EconomyProvider provider,
            double required
    ) {
        this.provider = provider;
        this.required = required;
    }

    @Override
    public String type() {
        return "economy";
    }

    @Override
    public boolean check(Player player) {

        return provider.isAvailable()
                && provider.has(
                        player,
                        required
                );
    }

    @Override
    public String description() {
        return provider.id()
                + " >= "
                + required;
    }
}