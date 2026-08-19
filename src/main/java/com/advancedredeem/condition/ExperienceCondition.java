package com.advancedredeem.condition;

import org.bukkit.entity.Player;

import java.util.Map;

public final class ExperienceCondition
        implements RedeemCondition {

    private final int required;

    public ExperienceCondition(int required) {
        this.required = Math.max(0, required);
    }

    public int required() {
        return required;
    }

    @Override
    public String type() {
        return "experience";
    }

    @Override
    public boolean check(Player player) {
        return player.getTotalExperience()
                >= required;
    }

    @Override
    public String description() {
        return "Experience >= " + required;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "type",
                type(),
                "value",
                required
        );
    }
}