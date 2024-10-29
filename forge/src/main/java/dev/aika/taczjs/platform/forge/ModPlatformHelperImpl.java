package dev.aika.taczjs.platform.forge;

import net.minecraftforge.fml.loading.FMLEnvironment;

@SuppressWarnings("unused")
public class ModPlatformHelperImpl {
    public static boolean isClientSide() {
        return FMLEnvironment.dist.isClient();
    }
}
