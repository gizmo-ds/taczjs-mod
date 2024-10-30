package dev.aika.taczjs.platform.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public class ModPlatformHelperImpl {
    public static boolean isClientSide() {
        return  FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
