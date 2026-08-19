package com.advancedredeem.code;

import com.advancedredeem.condition.RedeemCondition;
import com.advancedredeem.reward.Reward;

import java.util.*;

public final class RedeemCode {

    private final String code;

    private volatile long expiresAt;
    private volatile int maxUses;
    private volatile int totalUses;
    private volatile int maxUsesPerPlayer;

    private final Map<UUID, Integer> playerUses =
            new HashMap<>();

    private final List<Reward> rewards =
            new ArrayList<>();

    private final List<RedeemCondition> conditions =
            new ArrayList<>();

    public RedeemCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be empty");
        }

        this.code = code;
        this.maxUsesPerPlayer = 1;
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

    public void setMaxUsesPerPlayer(int value) {
        this.maxUsesPerPlayer = Math.max(0, value);
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
                && System.currentTimeMillis() >= expiresAt;
    }

    public boolean hasUsesLeft() {
        return maxUses <= 0
                || totalUses < maxUses;
    }

    public int getPlayerUses(UUID uuid) {
        return playerUses.getOrDefault(uuid, 0);
    }

    public boolean canPlayerRedeem(UUID uuid) {
        return maxUsesPerPlayer <= 0
                || getPlayerUses(uuid) < maxUsesPerPlayer;
    }

    public void registerUse(UUID uuid) {
        totalUses++;

        playerUses.merge(
                uuid,
                1,
                Integer::sum
        );
    }

    public void rollbackUse(UUID uuid) {
        if (totalUses > 0) {
            totalUses--;
        }

        int current =
                playerUses.getOrDefault(uuid, 0);

        if (current <= 1) {
            playerUses.remove(uuid);
        } else {
            playerUses.put(
                    uuid,
                    current - 1
            );
        }
    }

    public void restoreTotalUses(int value) {
        this.totalUses = Math.max(0, value);
    }

    public RedeemCode copy() {
        RedeemCode copy =
                new RedeemCode(code);

        copy.expiresAt = expiresAt;
        copy.maxUses = maxUses;
        copy.totalUses = totalUses;
        copy.maxUsesPerPlayer = maxUsesPerPlayer;

        copy.playerUses.putAll(playerUses);
        copy.rewards.addAll(rewards);
        copy.conditions.addAll(conditions);

        return copy;
    }
}