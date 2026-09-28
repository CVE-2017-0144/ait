package dev.drtheo.portal;

import java.util.UUID;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import dev.amble.ait.AITMod;

public record PortalInitS2CPacket(UUID id, ResourceKey<Level> dimension,
                                  ResourceKey<DimensionType> dimensionType) {

    public static final ResourceLocation TYPE = AITMod.id("portal_init");

    public static PortalInitS2CPacket read(FriendlyByteBuf buf) {
        return new PortalInitS2CPacket(buf.readUUID(),
                buf.readResourceKey(Registries.DIMENSION),
                buf.readResourceKey(Registries.DIMENSION_TYPE));
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(this.id);
        buf.writeResourceKey(this.dimension);
        buf.writeResourceKey(this.dimensionType);
    }
}
