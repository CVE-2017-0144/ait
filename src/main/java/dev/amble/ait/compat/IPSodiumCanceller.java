package dev.amble.ait.compat;

import java.util.List;

import com.bawnorton.mixinsquared.api.MixinCanceller;

// replaced by mixin.compat.ipsodium: ip's culler mixin turns sodium's cave culling off everywhere, and both break on sodium 0.8
public class IPSodiumCanceller implements MixinCanceller {

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        return mixinClassName.equals("qouteall.imm_ptl.core.compat.mixin.sodium.MixinSodiumOcclusionCuller")
                || mixinClassName.equals("qouteall.imm_ptl.core.compat.mixin.sodium.MixinSodiumViewport") && AITMixinPlugin.hasSodium08();
    }
}
