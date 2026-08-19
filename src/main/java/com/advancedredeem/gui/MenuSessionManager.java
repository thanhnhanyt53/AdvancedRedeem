package com.advancedredeem.gui;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class MenuSessionManager {

    private final ConcurrentHashMap<
            UUID,
            MenuSession
            > sessions =
            new ConcurrentHashMap<>();

    public void open(MenuSession session) {
        sessions.put(
                session.player(),
                session
        );
    }

    public MenuSession get(UUID uuid) {
        return sessions.get(uuid);
    }

    public void close(UUID uuid) {
        sessions.remove(uuid);
    }

    public void clear() {
        sessions.clear();
    }
}