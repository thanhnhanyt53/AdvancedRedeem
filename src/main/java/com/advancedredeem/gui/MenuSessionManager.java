package com.advancedredeem.gui;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class MenuSessionManager {

    private final ConcurrentHashMap<
            UUID,
            MenuSession
            > sessions =
            new ConcurrentHashMap<>();

    public void open(
            MenuSession session
    ) {

        sessions.put(
                session.player(),
                session
        );
    }

    public MenuSession get(
            UUID player
    ) {

        return sessions.get(player);
    }

    public void close(
            UUID player
    ) {

        sessions.remove(player);
    }
}