package com.advancedredeem.reward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class CommandReward
        implements Reward {

    private final String command;

    public CommandReward(String command) {
        if (command == null ||
                command.isBlank()) {
            throw new IllegalArgumentException(
                    "Command cannot be empty"
            );
        }

        this.command = command;
    }

    public String command() {
        return command;
    }

    @Override
    public String type() {
        return "command";
    }

    @Override
    public boolean give(Player player) {

        String parsed = command
                .replace(
                        "%player%",
                        player.getName()
                )
                .replace(
                        "%uuid%",
                        player.getUniqueId()
                                .toString()
                );

        return Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                parsed
        );
    }
}