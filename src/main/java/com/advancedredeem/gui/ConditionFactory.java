package com.advancedredeem.gui;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.condition.*;
import com.advancedredeem.economy.EconomyProvider;

public final class ConditionFactory {

    private ConditionFactory() {
    }

    public static RedeemCondition playtime(
            String value
    ) {

        return new PlaytimeCondition(
                Long.parseLong(value)
        );
    }

    public static RedeemCondition kills(
            String value
    ) {

        return new StatisticCondition(
                "kills",
                org.bukkit.Statistic.PLAYER_KILLS,
                Integer.parseInt(value)
        );
    }

    public static RedeemCondition deaths(
            String value
    ) {

        return new StatisticCondition(
                "deaths",
                org.bukkit.Statistic.DEATHS,
                Integer.parseInt(value)
        );
    }

    public static RedeemCondition experience(
            String value
    ) {

        return new ExperienceCondition(
                Integer.parseInt(value)
        );
    }

    public static RedeemCondition permission(
            String value
    ) {

        return new PermissionCondition(value);
    }

    public static RedeemCondition economy(
            AdvancedRedeemPlugin plugin,
            String providerId,
            String value
    ) {

        EconomyProvider provider =
                plugin.economies()
                        .get(providerId);

        if (provider == null) {
            throw new IllegalArgumentException(
                    "Unknown economy provider: "
                            + providerId
            );
        }

        return new EconomyCondition(
                providerId,
                provider,
                Double.parseDouble(value)
        );
    }
}