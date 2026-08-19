package com.advancedredeem.economy;

import com.advancedredeem.AdvancedRedeemPlugin;
import net.milkbowl.vault.economy.Economy;

import java.util.*;

public final class EconomyManager {

    private final AdvancedRedeemPlugin plugin;

    private final Map<String, EconomyProvider>
            providers = new HashMap<>();

    public EconomyManager(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;
    }

    public void register(
            EconomyProvider provider
    ) {

        providers.put(
                provider.id()
                        .toLowerCase(Locale.ROOT),
                provider
        );
    }

    public EconomyProvider get(
            String id
    ) {

        return providers.get(
                id.toLowerCase(Locale.ROOT)
        );
    }

    public Collection<EconomyProvider>
    providers() {

        return Collections.unmodifiableCollection(
                providers.values()
        );
    }

    public void registerVaultProvider() {

        var registration =
                plugin.getServer()
                        .getServicesManager()
                        .getRegistration(
                                Economy.class
                        );

        if (registration == null) {
            return;
        }

        Economy economy =
                registration.getProvider();

        if (economy == null) {
            return;
        }

        register(
                new VaultEconomyProvider(economy)
        );
    }
}