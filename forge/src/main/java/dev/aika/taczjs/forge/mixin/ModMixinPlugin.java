package dev.aika.taczjs.forge.mixin;

import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.VersionRange;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ModMixinPlugin implements IMixinConfigPlugin {
    private static String taczVersion;

    @Override
    public void onLoad(String mixinPackage) {
        taczVersion = FMLLoader.getLoadingModList().getModFileById("tacz")
                .versionString();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.endsWith(".InteractKeyTextOverlayMixin")) {
            if (isTaczVersionInRange("[1.2.0,)")) return mixinClassName.contains(".v120.");
            if (isTaczVersionInRange("[1.1.8,)")) return mixinClassName.contains(".v118.");
            return !mixinClassName.contains(".v118.") && !mixinClassName.contains(".v120.");
        }
        if (mixinClassName.endsWith(".ClientIndexManagerMixin") || mixinClassName.endsWith(".LivingEntityShootMixin")) {
            return isTaczVersionInRange("[1.1.8,)") && mixinClassName.contains(".v118.");
        }
        return true;
    }

    private static boolean isTaczVersionInRange(String spec) {
        try {
            return VersionRange.createFromVersionSpec(spec).containsVersion(new DefaultArtifactVersion(taczVersion));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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