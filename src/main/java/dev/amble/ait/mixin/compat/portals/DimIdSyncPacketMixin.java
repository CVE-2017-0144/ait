package dev.amble.ait.mixin.compat.portals;

import java.util.List;
import java.util.Set;

import de.nick1st.imm_ptl.events.DimensionEvents;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.q_misc_util.MiscNetworking;
import qouteall.q_misc_util.dimension.DimensionIntId;
import qouteall.q_misc_util.mixin.client.IEClientPacketListener_Misc;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

@Mixin(value = MiscNetworking.DimIdSyncPacket.class, remap = false)
public class DimIdSyncPacketMixin {

    // ip on neo has no dimlib, nothing refreshes client levels
    @Inject(method = "handleOnNetworkingThread", at = @At("TAIL"))
    private void updateLevels(CallbackInfo ci) {
        Set<ResourceKey<Level>> dims = DimensionIntId.clientRecord.getDimIdSet();

        ((IEClientPacketListener_Misc) Minecraft.getInstance().getConnection()).ip_setLevels(dims);
        NeoForge.EVENT_BUS.post(new DimensionEvents.CLIENT_DIMENSION_UPDATE_EVENT(List.copyOf(dims)));
    }
}
