package com.advancedredeem.reward;

import org.bukkit.entity.Player;

public interface Reward {

    String type();

    /**
     * Gives the reward.
     *
     * @return true if successfully processed
     */
    boolean give(Player player);
}