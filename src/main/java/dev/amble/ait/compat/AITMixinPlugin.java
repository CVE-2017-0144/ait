package dev.amble.ait.compat;

import java.util.List;
import java.util.Set;

import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

@SuppressWarnings("unused")
public class AITMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_PACKAGE_ROOT = "dev.amble.ait.mixin.compat.";
    private final Logger logger = LogManager.getLogger("AIT");

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE_ROOT))
            return true;

        String[] parts = mixinClassName.split("\\.");

        String id = parts[5];
        if (id.equals("portals"))
            return DependencyChecker.hasPortals();

        if (id.equals("ipsodium"))
            return DependencyChecker.hasPortals() && hasSodium08();

        return true;
    }

    public static boolean hasSodium08() {
        ModFileInfo sodium = LoadingModList.get().getModFileById("sodium");
        return sodium != null && sodium.getMods().get(0).getVersion().compareTo(new DefaultArtifactVersion("0.8")) >= 0;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
