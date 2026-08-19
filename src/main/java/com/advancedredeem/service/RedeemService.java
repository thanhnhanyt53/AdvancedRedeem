package com.advancedredeem.service;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.code.RedeemCode;
import com.advancedredeem.condition.ConditionManager;
import com.advancedredeem.reward.Reward;
import com.advancedredeem.reward.RewardManager;
import com.advancedredeem.storage.CodeManager;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class RedeemService {

    private final AdvancedRedeemPlugin plugin;

    private final CodeManager codeManager;

    private final RewardManager rewardManager;

    private final ConditionManager conditionManager;

    private final Map<String, Object> locks =
            new ConcurrentHashMap<>();

    public RedeemService(
            AdvancedRedeemPlugin plugin,
            CodeManager codeManager,
            RewardManager rewardManager,
            ConditionManager conditionManager
    ) {

        this.plugin = plugin;
        this.codeManager = codeManager;
        this.rewardManager = rewardManager;
        this.conditionManager = conditionManager;
    }

    public RedeemResult redeem(
            Player player,
            String input
    ) {

        RedeemCode code =
                codeManager.get(input);

        if (code == null) {
            return RedeemResult.NOT_FOUND;
        }

        Object lock =
                locks.computeIfAbsent(
                        code.getCode(),
                        key -> new Object()
                );

        synchronized (lock) {

            return redeemLocked(
                    player,
                    code
            );
        }
    }

    private RedeemResult redeemLocked(
            Player player,
            RedeemCode code
    ) {

        if (code.isExpired()) {
            return RedeemResult.EXPIRED;
        }

        if (!code.hasUsesLeft()) {
            return RedeemResult.GLOBAL_LIMIT;
        }

        UUID uuid =
                player.getUniqueId();

        if (!code.canPlayerRedeem(uuid)) {
            return RedeemResult.PLAYER_LIMIT;
        }

        if (!conditionManager.checkAll(
                player,
                code.getConditions()
        )) {
            return RedeemResult.CONDITION_FAILED;
        }

        /*
         * Give rewards first.
         *
         * Usage is registered only after
         * every reward has been processed.
         */
        for (Reward reward :
                code.getRewards()) {

            boolean success;

            try {
                success = reward.give(player);
            } catch (Throwable throwable) {

                plugin.getLogger().severe(
                        "Reward failed for code "
                                + code.getCode()
                                + ": "
                                + throwable.getMessage()
                );

                return RedeemResult.REWARD_FAILED;
            }

            if (!success) {
                return RedeemResult.REWARD_FAILED;
            }
        }

        code.registerUse(uuid);

        codeManager.markDirty(code);

        return RedeemResult.SUCCESS;
    }
}