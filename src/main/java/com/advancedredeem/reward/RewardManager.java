package com.advancedredeem.reward;

import com.advancedredeem.AdvancedRedeemPlugin;

import java.util.*;

public final class RewardManager {

    private final AdvancedRedeemPlugin plugin;

    private final Map<String, RewardProvider>
            providers = new HashMap<>();

    public RewardManager(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;

        register(
                "command",
                data -> new CommandReward(
                        data.getOrDefault(
                                "command",
                                ""
                        )
                )
        );
    }

    public void register(
            String id,
            RewardProvider provider
    ) {

        providers.put(
                id.toLowerCase(Locale.ROOT),
                provider
        );
    }

    public RewardProvider get(
            String id
    ) {

        return providers.get(
                id.toLowerCase(Locale.ROOT)
        );
    }
}