package com.advancedredeem.storage;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.condition.*;
import com.advancedredeem.economy.EconomyProvider;
import org.bukkit.Statistic;

import java.util.*;

public final class ConditionSerializer {

    private ConditionSerializer() {
    }

    public static Map<String, Object> serialize(
            RedeemCondition condition
    ) {
        return new LinkedHashMap<>(
                condition.serialize()
        );
    }

    public static RedeemCondition deserialize(
            AdvancedRedeemPlugin plugin,
            Map<?, ?> raw
    ) {

        String type =
                Objects.toString(
                        raw.get("type"),
                        ""
                ).toLowerCase(Locale.ROOT);

        return switch (type) {

            case "playtime" ->
                    new PlaytimeCondition(
                            Long.parseLong(
                                    Objects.toString(
                                            raw.get(
                                                    "seconds"
                                            ),
                                            "0"
                                    )
                            )
                    );

            case "kills" ->
                    new StatisticCondition(
                            "kills",
                            Statistic.PLAYER_KILLS,
                            Integer.parseInt(
                                    Objects.toString(
                                            raw.get(
                                                    "value"
                                            ),
                                            "0"
                                    )
                            )
                    );

            case "deaths" ->
                    new StatisticCondition(
                            "deaths",
                            Statistic.DEATHS,
                            Integer.parseInt(
                                    Objects.toString(
                                            raw.get(
                                                    "value"
                                            ),
                                            "0"
                                    )
                            )
                    );

            case "experience" ->
                    new ExperienceCondition(
                            Integer.parseInt(
                                    Objects.toString(
                                            raw.get(
                                                    "value"
                                            ),
                                            "0"
                                    )
                            )
                    );

            case "permission" ->
                    new PermissionCondition(
                            Objects.toString(
                                    raw.get(
                                            "permission"
                                    ),
                                    ""
                            )
                    );

            case "placeholder" -> {

                String placeholder =
                        Objects.toString(
                                raw.get(
                                        "placeholder"
                                ),
                                ""
                        );

                String operator =
                        Objects.toString(
                                raw.get(
                                        "operator"
                                ),
                                "EQUALS"
                        );

                String expected =
                        Objects.toString(
                                raw.get(
                                        "expected"
                                ),
                                ""
                        );

                yield new PlaceholderCondition(
                        placeholder,
                        PlaceholderCondition.Operator
                                .valueOf(
                                        operator
                                                .toUpperCase(
                                                        Locale.ROOT
                                                )
                                ),
                        expected
                );
            }

            case "economy" -> {

                String providerId =
                        Objects.toString(
                                raw.get(
                                        "provider"
                                ),
                                ""
                        );

                EconomyProvider provider =
                        plugin.economies()
                                .get(providerId);

                if (provider == null) {
                    throw new IllegalArgumentException(
                            "Unknown economy provider: "
                                    + providerId
                    );
                }

                double value =
                        Double.parseDouble(
                                Objects.toString(
                                        raw.get("value"),
                                        "0"
                                )
                        );

                yield new EconomyCondition(
                        providerId,
                        provider,
                        value
                );
            }

            default -> {

                ConditionProvider provider =
                        plugin.conditions()
                                .get(type);

                if (provider == null) {
                    throw new IllegalArgumentException(
                            "Unknown condition type: "
                                    + type
                    );
                }

                Map<String, String> data =
                        new HashMap<>();

                raw.forEach(
                        (key, value) ->
                                data.put(
                                        key.toString(),
                                        Objects.toString(
                                                value,
                                                ""
                                        )
                                )
                );

                yield provider.create(data);
            }
        };
    }
}