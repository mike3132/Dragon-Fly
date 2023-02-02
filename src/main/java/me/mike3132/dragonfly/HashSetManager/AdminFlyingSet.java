package me.mike3132.dragonfly.HashSetManager;

import java.util.HashSet;
import java.util.UUID;

public class AdminFlyingSet {

    private final static HashSet<UUID> flyingAdmins = new HashSet<>();

    public static HashSet<UUID> getFlyingAdmins() {
        return flyingAdmins;
    }

    public static void addFlyingAdmins(UUID player) {
        getFlyingAdmins().add(player);
    }

    public static void removeFlyingAdmins(UUID player) {
        getFlyingAdmins().remove(player);
    }

}
