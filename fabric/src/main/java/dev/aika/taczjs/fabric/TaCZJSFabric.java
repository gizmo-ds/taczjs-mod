package dev.aika.taczjs.fabric;

import dev.aika.taczjs.TaCZJS;
import net.fabricmc.api.ModInitializer;

public final class TaCZJSFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TaCZJS.init();
    }
}
