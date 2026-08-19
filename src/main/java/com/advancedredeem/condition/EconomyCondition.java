package com.advancedredeem.condition;

import com.advancedredeem.economy.EconomyProvider;
import org.bukkit.entity.Player;

import java.util.Map;

public final class EconomyCondition
        implements RedeemCondition {

    private final String providerId;
    private final EconomyProvider provider;
    private final double required;

    public EconomyCondition(
            String providerId,
            EconomyProvider provider,
            double required
    ) {
        this.providerId = providerId;
        this.provider = provider;
        this.required = Math.max(0D, required);
    }

    public String providerId() {
        return providerId;
    }

    public double required() {
        return required;
    }

    @Override
    public String type() {
        return "economy";
    }

    @Override
    public boolean check(Player player) {

        return provider != null
                && provider.isAvailable()
                && provider.has(
                        player,
                        required
                );
    }

    @Override
    public String description() {
        return providerId
                + " >= "
                + required;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "type",
                type(),
                "provider",
                providerId,
                "value",
                required
        );
    }
}