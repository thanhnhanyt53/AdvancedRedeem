package com.advancedredeem.condition;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.economy.EconomyProvider;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Function;

public final class ConditionManager {

    private final AdvancedRedeemPlugin plugin;

    private final Map<String, ConditionProvider>
            providers = new HashMap<>();

    public ConditionManager(
            AdvancedRedeemPlugin plugin
    ) {
        this.plugin = plugin;

        registerBuiltin();

        registerPlaceholderAPI();
    }

    private void registerBuiltin() {

        register(new ConditionProvider() {

            @Override
            public String id() {
                return "playtime";
            }

            @Override
            public RedeemCondition create(
                    Map<String, String> data
            ) {

                return new PlaytimeCondition(
                        Long.parseLong(
                                data.getOrDefault(
                                        "seconds",
                                        "0"
                                )
                        )
                );
            }
        });

        register(new ConditionProvider() {

            @Override
            public String id() {
                return "kills";
            }

            @Override
            public RedeemCondition create(
                    Map<String, String> data
            ) {

                return new StatisticCondition(
                        "kills",
                        Statistic.PLAYER_KILLS,
                        Integer.parseInt(
                                data.getOrDefault(
                                        "value",
                                        "0"
                                )
                        )
                );
            }
        });

        register(new ConditionProvider() {

            @Override
            public String id() {
                return "deaths";
            }

            @Override
            public RedeemCondition create(
                    Map<String, String> data
            ) {

                return new StatisticCondition(
                        "deaths",
                        Statistic.DEATHS,
                        Integer.parseInt(
                                data.getOrDefault(
                                        "value",
                                        "0"
                                )
                        )
                );
            }
        });

        register(new ConditionProvider() {

            @Override
            public String id() {
                return "experience";
            }

            @Override
            public RedeemCondition create(
                    Map<String, String> data
            ) {

                return new ExperienceCondition(
                        Integer.parseInt(
                                data.getOrDefault(
                                        "value",
                                        "0"
                                )
                        )
                );
            }
        });

        register(new ConditionProvider() {

            @Override
            public String id() {
                return "permission";
            }

            @Override
            public RedeemCondition create(
                    Map<String, String> data
            ) {

                return new PermissionCondition(
                        data.get("permission")
                );
            }
        });
    }

    private void registerPlaceholderAPI() {

        if (!plugin.getServer()
                .getPluginManager()
                .isPluginEnabled("PlaceholderAPI")) {

            return;
        }

        register(new ConditionProvider() {

            @Override
            public String id() {
                return "placeholder";
            }

            @Override
            public RedeemCondition create(
                    Map<String, String> data
            ) {

                return new PlaceholderCondition(
                        data.get("placeholder"),
                        PlaceholderCondition.Operator
                                .valueOf(
                                        data.getOrDefault(
                                                "operator",
                                                "EQUALS"
                                        )
                                ),
                        data.getOrDefault(
                                "expected",
                                ""
                        )
                );
            }
        });
    }

    public void register(
            ConditionProvider provider
    ) {

        providers.put(
                provider.id().toLowerCase(Locale.ROOT),
                provider
        );
    }

    public ConditionProvider get(String id) {

        if (id == null) {
            return null;
        }

        return providers.get(
                id.toLowerCase(Locale.ROOT)
        );
    }

    public Collection<ConditionProvider> all() {
        return Collections.unmodifiableCollection(
                providers.values()
        );
    }

    public boolean checkAll(
            Player player,
            List<RedeemCondition> conditions
    ) {

        for (RedeemCondition condition :
                conditions) {

            if (!condition.check(player)) {
                return false;
            }
        }

        return true;
    }
}