package com.advancedredeem.api;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.code.RedeemCode;
import com.advancedredeem.condition.ConditionProvider;
import com.advancedredeem.economy.EconomyProvider;
import com.advancedredeem.item.ItemProvider;
import com.advancedredeem.reward.RewardProvider;
import com.advancedredeem.service.RedeemResult;
import org.bukkit.entity.Player;

public final class AdvancedRedeemAPI {

    private final AdvancedRedeemPlugin plugin;

    public AdvancedRedeemAPI(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;
    }

    public RedeemCode createCode(
            String code
    ) {

        RedeemCode result =
                new RedeemCode(code);

        plugin.codes().create(result);

        return result;
    }

    public RedeemCode getCode(
            String code
    ) {

        return plugin.codes().get(code);
    }

    public RedeemResult redeem(
            Player player,
            String code
    ) {

        return plugin.redeem()
                .redeem(
                        player,
                        code
                );
    }

    public void registerConditionProvider(
            ConditionProvider provider
    ) {

        plugin.conditions()
                .register(provider);
    }

    public void registerItemProvider(
            ItemProvider provider
    ) {

        plugin.items()
                .register(provider);
    }

    public void registerEconomyProvider(
            EconomyProvider provider
    ) {

        plugin.economies()
                .register(provider);
    }

    public void registerRewardProvider(
            String id,
            RewardProvider provider
    ) {

        plugin.rewards()
                .register(
                        id,
                        provider
                );
    }
}