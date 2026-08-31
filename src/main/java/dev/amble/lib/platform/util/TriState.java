package dev.amble.lib.platform.util;

public enum TriState {

    FALSE,
    DEFAULT,
    TRUE;

    public boolean get() {
        return this == TRUE;
    }
}
