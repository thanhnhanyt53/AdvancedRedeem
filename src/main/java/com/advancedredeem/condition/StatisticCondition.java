package com.advancedredeem.condition;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public final class StatisticCondition
        implements RedeemCondition {

    private final String id;
    private final Statistic statistic;
    private final int required;

    public StatisticCondition(
            String id,
            Statistic statistic,
            int required
    ) {
        this.id = id;
        this.statistic = statistic;
        this.required =
                Math.max(0, required);
    }

    @Override
    public String type() {
        return id;
    }

    @Override
    public boolean check(Player player) {
        return player.getStatistic(statistic)
                >= required;
    }

    @Override
    public String description() {
        return id + " >= " + required;
    }
}