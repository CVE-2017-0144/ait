package dev.amble.ait.compat;

import com.mojang.blaze3d.platform.GlUtil;
import dev.amble.lib.platform.Platform;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class DependencyChecker {

    private static final boolean HAS_PORTALS = doesModExist("imm_ptl_core");
    private static final boolean HAS_GRAVITY = true;
    private static final boolean HAS_IRIS = doesModExist("iris");
    private static final boolean HAS_INDIUM = doesModExist("indium");
    private static final boolean HAS_PERMISSION_API = doesModExist("fabric-permissions-api");

    private static Boolean NVIDIA_CARD;
    private static Boolean MAC_OS;

    public static boolean doesModExist(String modid) {
        return Platform.isModLoaded(modid);
    }

    public static boolean hasPortals() {
        return HAS_PORTALS;
    }

    public static boolean hasIris() {
        return HAS_IRIS;
    }

    public static boolean hasGravity() {
        return HAS_GRAVITY;
    }

    public static boolean hasIndium() {
        return HAS_INDIUM;
    }

    public static boolean hasPermissionApi() {
        return HAS_PERMISSION_API;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean hasNvidiaCard() {
        if (NVIDIA_CARD == null)
            NVIDIA_CARD = GlUtil.getVendor().toLowerCase().contains("nvidia");

        return NVIDIA_CARD;
    }

    public static boolean hasMacOs() {
        if (MAC_OS == null) {
            String os = System.getProperty("os.name");
            MAC_OS = os != null && (os.contains("mac") || os.contains("darwin"));
        }

        return MAC_OS;
    }
}
