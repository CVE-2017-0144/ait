package dev.amble.ait.core.item.link;

import net.minecraft.world.item.Item;

public abstract class AbstractLinkItem extends Item {

    private final Type linkType;

    public AbstractLinkItem(Type type, Properties settings) {
        super(settings);
        this.linkType = type;
    }

    public Type getType() {
        return linkType;
    }

    public abstract float unitsPerTick(Type type);

    public enum Type {
        ARTRON, DATA, VORTEX
    }
}
