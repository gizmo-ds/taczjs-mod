package dev.aika.taczjs.forge;

import dev.aika.taczjs.TaCZJS;
import net.minecraftforge.fml.common.Mod;

@Mod(TaCZJS.MOD_ID)
public final class TaCZJSForge {
    public TaCZJSForge() {
        TaCZJS.init();
    }
}
