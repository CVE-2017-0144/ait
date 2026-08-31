package dev.amble.ait.core.tardis.util.network.s2c;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.Tardis;

public class BOTISyncS2CPacket implements FabricPacket {
    public static final PacketType<BOTISyncS2CPacket> TYPE = PacketType.create(AITMod.id("boti_sync"), BOTISyncS2CPacket::new);
    private final BlockPos pos;
    private final ResourceKey<Level> targetWorld;
    private final BlockPos targetPos;

    public BOTISyncS2CPacket(BlockPos pos, ResourceKey<Level> targetWorld, BlockPos targetPos) {
        this.pos = pos;
        this.targetWorld = targetWorld;
        this.targetPos = targetPos;
    }

    public BOTISyncS2CPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.targetWorld = buf.readResourceKey(Registries.DIMENSION);
        this.targetPos = buf.readBlockPos();
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeResourceKey(targetWorld);
        buf.writeBlockPos(targetPos);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @SuppressWarnings("unchecked")
    public <T> boolean handle(LocalPlayer source, PacketSender response) {
        Minecraft client = Minecraft.getInstance();
        Level world = client.level;
        if (world == null) return false;

        BlockEntity exterior = world.getBlockEntity(this.pos);

        if (exterior instanceof ExteriorBlockEntity exteriorBlockEntity) {
            if (!exteriorBlockEntity.isLinked()) return false;
            Tardis tardis = exteriorBlockEntity.tardis().get();
            // tardis.stats().setTargetWorld(exteriorBlockEntity, this.targetWorld, this.targetPos, false);
        }
        return true;
    }
}
