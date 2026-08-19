package com.advancedredeem.condition;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public final class PlaytimeCondition
        implements RedeemCondition {

    private final long seconds;

    public PlaytimeCondition(long seconds) {
        this.seconds = Math.max(0L, seconds);
    }

    @Override
    public String type() {
        return "playtime";
    }

    @Override
    public boolean check(Player player) {

        long ticks =
                player.getStatistic(
                        Statistic.PLAY_ONE_MINUTE
                );

        return ticks / 20L >= seconds;
    }

    @Override
    public String description() {
        return "Playtime >= " + seconds + " seconds";
    }
}