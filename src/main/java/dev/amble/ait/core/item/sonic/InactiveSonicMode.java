package dev.amble.ait.core.item.sonic;

import dev.amble.ait.data.schema.sonic.SonicSchema;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class InactiveSonicMode extends SonicMode {

    protected InactiveSonicMode() {
        super(-1);
    }

    @Override
    public Component text() {
        return Component.translatable("sonic.ait.mode.inactive").withStyle(ChatFormatting.GRAY, ChatFormatting.BOLD);
    }

    @Override
    public int maxTime() {
        return 0;
    }

    @Override
    public ResourceLocation model(SonicSchema.Models models) {
        return models.inactive();
    }

    @Override
    public SonicMode next() {
        return Modes.get(0);
    }

    @Override
    public SonicMode previous() {
        return Modes.get(Modes.VALUES.length - 1);
    }
}
