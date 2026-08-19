package com.advancedredeem.storage;

import com.advancedredeem.reward.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class RewardSerializer {

    private RewardSerializer() {
    }

    public static Map<String, Object> serialize(
            Reward reward
    ) {
        return new LinkedHashMap<>(
                reward.serialize()
        );
    }

    public static Reward deserialize(
            Map<?, ?> raw
    ) {

        Object typeObject =
                raw.get("type");

        if (typeObject == null) {
            throw new IllegalArgumentException(
                    "Reward type missing"
            );
        }

        String type =
                typeObject.toString()
                        .toLowerCase(Locale.ROOT);

        return switch (type) {

            case "command" -> {

                String command =
                        Objects.toString(
                                raw.get("command"),
                                ""
                        );

                yield new CommandReward(command);
            }

            case "item" -> {

                Object object =
                        raw.get("item");

                if (!(object instanceof ItemStack item)) {
                    throw new IllegalArgumentException(
                            "Item reward contains invalid ItemStack"
                    );
                }

                yield new ItemReward(item);
            }

            default ->
                    throw new IllegalArgumentException(
                            "Unknown reward type: "
                                    + type
                    );
        };
    }
}