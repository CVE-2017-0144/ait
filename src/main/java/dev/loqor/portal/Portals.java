package dev.loqor.portal;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class Portals {

    private Portals() {
    }

    public static UUID interiorId(UUID tardis) {
        return UUID.nameUUIDFromBytes(("ait:boti:interior:" + tardis).getBytes(StandardCharsets.UTF_8));
    }
}
