package dev.aika.taczjs.fabric.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.impl.util.version.SemanticVersionImpl;
import net.fabricmc.loader.impl.util.version.VersionPredicateParser;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ModMixinPlugin implements IMixinConfigPlugin {
    private static String taczVersion;

    @Override
    public void onLoad(String mixinPackage) {
        //noinspection OptionalGetWithoutIsPresent
        taczVersion = FabricLoader.getInstance().getModContainer("tacz").get()
                .getMetadata().getVersion().toString();
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
                SemanticVersionImpl version = (SemanticVersionImpl) Version.parse(taczVersion);
                boolean containsVersion = VersionPredicateParser.parse(">=0.6.0")
                        .test(new SemanticVersionImpl(version.getVersionComponents(), null, null));
                return containsVersion && mixinClassName.contains(".v0_6.");
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