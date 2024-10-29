package dev.aika.taczjs.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ModPlatformHelper {
    @ExpectPlatform
    public static boolean isClientSide() {
        throw new AssertionError();
    }
}
