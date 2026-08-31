package dev.amble.ait.core.util;

import net.minecraft.util.StringRepresentable;

public enum MonitorStateUtil implements StringRepresentable {
    DEFAULT("default"),
    BLAZE("blaze");

    private final String name;

    private MonitorStateUtil(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}