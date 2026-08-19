package com.advancedredeem.storage;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.code.RedeemCode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class CodeManager {

    private final AdvancedRedeemPlugin plugin;

    private final Map<String, RedeemCode> codes =
            new ConcurrentHashMap<>();

    private final Set<String> dirty =
            ConcurrentHashMap.newKeySet();

    private final File file;

    public CodeManager(
            AdvancedRedeemPlugin plugin
    ) {

        this.plugin = plugin;

        file = new File(
                plugin.getDataFolder(),
                "codes.yml"
        );
    }

    public synchronized void load() {

        codes.clear();
        dirty.clear();

        if (!file.exists()) {
            return;
        }

        YamlConfiguration yaml =
                YamlConfiguration.loadConfiguration(
                        file
                );

        ConfigurationSection root =
                yaml.getConfigurationSection(
                        "codes"
                );

        if (root == null) {
            return;
        }

        for (String key :
                root.getKeys(false)) {

            ConfigurationSection section =
                    root.getConfigurationSection(key);

            if (section == null) {
                continue;
            }

            RedeemCode code =
                    new RedeemCode(key);

            code.setExpiresAt(
                    section.getLong(
                            "expires-at"
                    )
            );

            code.setMaxUses(
                    section.getInt(
                            "max-uses"
                    )
            );

            code.setMaxUsesPerPlayer(
                    section.getInt(
                            "max-uses-per-player",
                            1
                    )
            );

            restoreUses(
                    code,
                    section
            );

            codes.put(
                    normalize(key),
                    code
            );
        }
    }

    private void restoreUses(
            RedeemCode code,
            ConfigurationSection section
    ) {

        ConfigurationSection users =
                section.getConfigurationSection(
                        "player-uses"
                );

        if (users == null) {
            return;
        }

        for (String uuidString :
                users.getKeys(false)) {

            try {

                UUID uuid =
                        UUID.fromString(uuidString);

                int amount =
                        Math.max(
                                0,
                                users.getInt(
                                        uuidString
                                )
                        );

                if (amount > 0) {
                    code.getPlayerUses()
                            .put(
                                    uuid,
                                    amount
                            );
                }

            } catch (IllegalArgumentException ignored) {
            }
        }

        /*
         * totalUses is reconstructed from
         * player usage when possible.
         */
        int total =
                code.getPlayerUses()
                        .values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum();

        /*
         * Codes may have console/API redemptions
         * that are not associated with a UUID.
         *
         * A separate total-uses value is therefore
         * stored.
         */
        int storedTotal =
                section.getInt(
                        "total-uses",
                        total
                );

        setTotalUses(
                code,
                Math.max(
                        storedTotal,
                        total
                )
        );
    }

    /*
     * The field is intentionally private in RedeemCode.
     * This method will be replaced by a package-level
     * restore method in the final model.
     */
    private void setTotalUses(
            RedeemCode code,
            int total
    ) {

        code.restoreTotalUses(total);
    }

    public synchronized void save() {

        YamlConfiguration yaml =
                new YamlConfiguration();

        ConfigurationSection root =
                yaml.createSection(
                        "codes"
                );

        for (RedeemCode code :
                codes.values()) {

            ConfigurationSection section =
                    root.createSection(
                            code.getCode()
                    );

            section.set(
                    "expires-at",
                    code.getExpiresAt()
            );

            section.set(
                    "max-uses",
                    code.getMaxUses()
            );

            section.set(
                    "total-uses",
                    code.getTotalUses()
            );

            section.set(
                    "max-uses-per-player",
                    code.getMaxUsesPerPlayer()
            );

            for (var entry :
                    code.getPlayerUses()
                            .entrySet()) {

                section.set(
                        "player-uses."
                                + entry.getKey(),
                        entry.getValue()
                );
            }
        }

        try {

            yaml.save(file);

            dirty.clear();

        } catch (IOException exception) {

            plugin.getLogger().severe(
                    "Could not save codes.yml: "
                            + exception.getMessage()
            );
        }
    }

    public void markDirty(
            RedeemCode code
    ) {

        dirty.add(
                normalize(code.getCode())
        );

        /*
         * Save on the server thread shortly after
         * successful redemption. This avoids doing
         * disk IO inside the critical transaction.
         */
        plugin.getServer()
                .getScheduler()
                .runTask(
                        plugin,
                        () -> {
                            if (!dirty.isEmpty()) {
                                save();
                            }
                        }
                );
    }

    public RedeemCode get(
            String code
    ) {

        if (code == null) {
            return null;
        }

        return codes.get(
                normalize(code)
        );
    }

    public void create(
            RedeemCode code
    ) {

        codes.put(
                normalize(code.getCode()),
                code
        );

        dirty.add(
                normalize(code.getCode())
        );
    }

    public RedeemCode delete(
            String code
    ) {

        RedeemCode removed =
                codes.remove(
                        normalize(code)
                );

        if (removed != null) {
            dirty.add(
                    normalize(code)
            );
        }

        return removed;
    }

    public Collection<RedeemCode> all() {

        return Collections.unmodifiableCollection(
                codes.values()
        );
    }

    public String generateUniqueCode() {

        String result;

        do {

            result =
                    CodeGenerator.generate(
                            10
                    );

        } while (get(result) != null);

        return result;
    }

    private String normalize(
            String value
    ) {

        return value.toLowerCase(
                Locale.ROOT
        );
    }
}