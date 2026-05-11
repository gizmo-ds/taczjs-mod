package dev.aika.taczjs.neoforge.mixin;

import net.neoforged.fml.loading.FMLLoader;
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
        if (mixinClassName.endsWith(".ClientIndexManagerMixin")
                || mixinClassName.endsWith(".InteractKeyTextOverlayMixin")
                || mixinClassName.endsWith(".LivingEntityShootMixin")) {

            try {
                boolean containsVersion = VersionRange.createFromVersionSpec("[1.1.8,)")
                        .containsVersion(new DefaultArtifactVersion(taczVersion));
                return containsVersion && mixinClassName.contains(".v118.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
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