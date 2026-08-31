package dev.amble.ait.core.item.part;

import net.minecraft.world.item.Item;

public abstract class AbstractMachinePartItem<T extends Enum<T>> extends Item {

    private final T type;

    public AbstractMachinePartItem(T type, Properties settings) {
        super(settings);
        this.type = type;
    }

    public T getType() {
        return type;
    }
}
