package com.advancedredeem.reward;

import com.advancedredeem.item.ItemProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ExternalItemReward
        implements Reward {

    private final String providerId;
    private final String identifier;
    private final ItemProvider provider;

    public ExternalItemReward(
            String providerId,
            String identifier,
            ItemProvider provider
    ) {
        this.providerId = providerId;
        this.identifier = identifier;
        this.provider = provider;
    }

    @Override
    public String type() {
        return "external-item";
    }

    @Override
    public boolean give(Player player) {

        if (provider == null ||
                !provider.isAvailable()) {
            return false;
        }

        ItemStack item =
                provider.create(identifier);

        if (item == null ||
                item.getType().isAir()) {
            return false;
        }

        player.getInventory()
                .addItem(item);

        return true;
    }

    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> data =
                new LinkedHashMap<>();

        data.put(
                "type",
                type()
        );

        data.put(
                "provider",
                providerId
        );

        data.put(
                "identifier",
                identifier
        );

        return data;
    }
}