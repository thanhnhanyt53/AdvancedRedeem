package com.advancedredeem.code;

import com.advancedredeem.condition.RedeemCondition;
import com.advancedredeem.reward.Reward;

import java.util.*;

public final class RedeemCode {

    private final String code;

    private volatile long expiresAt;

    private volatile int maxUses;

    private int totalUses;

    private volatile int maxUsesPerPlayer;

    private final Map<UUID, Integer> playerUses =
            new HashMap<>();

    private final List<Reward> rewards =
            new ArrayList<>();

    private final List<RedeemCondition> conditions =
            new ArrayList<>();

    public RedeemCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException(
                    "Code cannot be empty"
            );
        }

        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = Math.max(0L, expiresAt);
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = Math.max(0, maxUses);
    }

    public int getTotalUses() {
        return totalUses;
    }

    public int getMaxUsesPerPlayer() {
        return maxUsesPerPlayer;
    }

    public void setMaxUsesPerPlayer(
            int maxUsesPerPlayer
    ) {
        this.maxUsesPerPlayer =
                Math.max(0, maxUsesPerPlayer);
    }

    public Map<UUID, Integer> getPlayerUses() {
        return playerUses;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public List<RedeemCondition> getConditions() {
        return conditions;
    }

    public boolean isExpired() {
        return expiresAt > 0L
                && System.currentTimeMillis()
                >= expiresAt;
    }

    public boolean hasUsesLeft() {
        return maxUses <= 0
                || totalUses < maxUses;
    }

    public int getPlayerUses(UUID uuid) {
        return playerUses.getOrDefault(
                uuid,
                0
        );
    }

    public boolean canPlayerRedeem(UUID uuid) {
        return maxUsesPerPlayer <= 0
                || getPlayerUses(uuid)
                < maxUsesPerPlayer;
    }

    /**
     * Must only be called while the code is locked.
     */
    public void registerUse(UUID uuid) {
        totalUses++;

        playerUses.merge(
                uuid,
                1,
                Integer::sum
        );
    }

    /**
     * Must only be called while the code is locked.
     */
    public void rollbackUse(UUID uuid) {
        if (totalUses > 0) {
            totalUses--;
        }

        int current =
                playerUses.getOrDefault(
                        uuid,
                        0
                );

        if (current <= 1) {
            playerUses.remove(uuid);
        } else {
            playerUses.put(
                    uuid,
                    current - 1
            );
        }
    }
}