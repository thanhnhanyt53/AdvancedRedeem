package com.advancedredeem.command;

import com.advancedredeem.reward.CommandReward;

public final class RewardCommandParser {

    private RewardCommandParser() {
    }

    public static CommandReward parse(
            String input
    ) {

        if (input == null) {
            throw new IllegalArgumentException(
                    "Command is null"
            );
        }

        String command =
                input.trim();

        if (command.startsWith("/")) {
            command =
                    command.substring(1);
        }

        if (command.isBlank()) {
            throw new IllegalArgumentException(
                    "Command cannot be empty"
            );
        }

        return new CommandReward(command);
    }
}