package com.advancedredeem.storage;

import com.advancedredeem.AdvancedRedeemPlugin;
import com.advancedredeem.code.RedeemCode;
import com.advancedredeem.condition.RedeemCondition;
import com.advancedredeem.reward.Reward;

import java.util.*;

public final class CodeSerializer {

    private CodeSerializer() {
    }

    public static Map<String, Object> serialize(
            RedeemCode code
    ) {

        Map<String, Object> result =
                new LinkedHashMap<>();

        result.put(
                "expires-at",
                code.getExpiresAt()
        );

        result.put(
                "max-uses",
                code.getMaxUses()
        );

        result.put(
                "total-uses",
                code.getTotalUses()
        );

        result.put(
                "max-uses-per-player",
                code.getMaxUsesPerPlayer()
        );

        Map<String, Object> playerUses =
                new LinkedHashMap<>();

        code.getPlayerUses().forEach(
                (uuid, amount) ->
                        playerUses.put(
                                uuid.toString(),
                                amount
                        )
        );

        result.put(
                "player-uses",
                playerUses
        );

        List<Map<String, Object>> rewards =
                new ArrayList<>();

        for (Reward reward :
                code.getRewards()) {

            rewards.add(
                    RewardSerializer.serialize(
                            reward
                    )
            );
        }

        result.put(
                "rewards",
                rewards
        );

        List<Map<String, Object>> conditions =
                new ArrayList<>();

        for (RedeemCondition condition :
                code.getConditions()) {

            conditions.add(
                    ConditionSerializer.serialize(
                            condition
                    )
            );
        }

        result.put(
                "conditions",
                conditions
        );

        return result;
    }

    public static RedeemCode deserialize(
            AdvancedRedeemPlugin plugin,
            String codeName,
            Map<?, ?> raw
    ) {

        RedeemCode code =
                new RedeemCode(codeName);

        code.setExpiresAt(
                longValue(
                        raw.get("expires-at"),
                        0L
                )
        );

        code.setMaxUses(
                intValue(
                        raw.get("max-uses"),
                        0
                )
        );

        code.restoreTotalUses(
                intValue(
                        raw.get("total-uses"),
                        0
                )
        );

        code.setMaxUsesPerPlayer(
                intValue(
                        raw.get(
                                "max-uses-per-player"
                        ),
                        1
                )
        );

        Object usesObject =
                raw.get("player-uses");

        if (usesObject instanceof Map<?, ?> uses) {

            uses.forEach(
                    (uuidObject, amountObject) -> {

                        try {

                            UUID uuid =
                                    UUID.fromString(
                                            uuidObject.toString()
                                    );

                            int amount =
                                    intValue(
                                            amountObject,
                                            0
                                    );

                            if (amount > 0) {
                                code.getPlayerUses()
                                        .put(
                                                uuid,
                                                amount
                                        );
                            }

                        } catch (
                                IllegalArgumentException ignored
                        ) {
                        }
                    }
            );
        }

        Object rewardsObject =
                raw.get("rewards");

        if (rewardsObject instanceof Iterable<?> list) {

            for (Object object : list) {

                if (!(object instanceof Map<?, ?> map)) {
                    continue;
                }

                try {

                    code.getRewards()
                            .add(
                                    RewardSerializer
                                            .deserialize(map)
                            );

                } catch (Exception exception) {

                    plugin.getLogger().warning(
                            "Could not deserialize reward for code "
                                    + codeName
                                    + ": "
                                    + exception.getMessage()
                    );
                }
            }
        }

        Object conditionsObject =
                raw.get("conditions");

        if (conditionsObject instanceof Iterable<?> list) {

            for (Object object : list) {

                if (!(object instanceof Map<?, ?> map)) {
                    continue;
                }

                try {

                    code.getConditions()
                            .add(
                                    ConditionSerializer
                                            .deserialize(
                                                    plugin,
                                                    map
                                            )
                            );

                } catch (Exception exception) {

                    plugin.getLogger().warning(
                            "Could not deserialize condition for code "
                                    + codeName
                                    + ": "
                                    + exception.getMessage()
                    );
                }
            }
        }

        return code;
    }

    private static int intValue(
            Object object,
            int fallback
    ) {

        if (object instanceof Number number) {
            return number.intValue();
        }

        try {
            return Integer.parseInt(
                    String.valueOf(object)
            );
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static long longValue(
            Object object,
            long fallback
    ) {

        if (object instanceof Number number) {
            return number.longValue();
        }

        try {
            return Long.parseLong(
                    String.valueOf(object)
            );
        } catch (Exception ignored) {
            return fallback;
        }
    }
}