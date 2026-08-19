package com.advancedredeem.item;

import com.advancedredeem.AdvancedRedeemPlugin;

import java.util.*;

public final class ItemProviderManager {

    private final AdvancedRedeemPlugin plugin;

    private final Map<String, ItemProvider>
            providers = new HashMap<>();

    public ItemProviderManager(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;
    }

    public void register(
            ItemProvider provider
    ) {

        providers.put(
                provider.id()
                        .toLowerCase(Locale.ROOT),
                provider
        );
    }

    public ItemProvider get(String id) {

        return providers.get(
                id.toLowerCase(Locale.ROOT)
        );
    }

    public void registerBukkitProvider() {

        register(
                new BukkitItemProvider()
        );
    }
}