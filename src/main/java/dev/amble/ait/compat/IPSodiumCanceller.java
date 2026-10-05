package dev.amble.ait.compat;

import java.util.List;

import com.bawnorton.mixinsquared.api.MixinCanceller;

// ip 6.0.7 targets sodium 0.6, mixin.compat.ipsodium redoes these two for 0.8
public class IPSodiumCanceller implements MixinCanceller {

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        return (mixinClassName.equals("qouteall.imm_ptl.core.compat.mixin.sodium.MixinSodiumOcclusionCuller")
                || mixinClassName.equals("qouteall.imm_ptl.core.compat.mixin.sodium.MixinSodiumViewport"))
                && AITMixinPlugin.hasSodium08();
    }
}
