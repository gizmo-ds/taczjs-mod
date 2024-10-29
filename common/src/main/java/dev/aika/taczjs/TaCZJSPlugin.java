package dev.aika.taczjs;

import com.tacz.guns.client.sound.SoundPlayManager;
import dev.aika.taczjs.events.JSEvents;
import dev.aika.taczjs.events.client.AbstractClientGunEvent;
import dev.aika.taczjs.platform.ModPlatformHelper;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class TaCZJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        JSEvents.GROUP.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        if (ModPlatformHelper.isClientSide()) {
            event.add("SoundPlayManager", SoundPlayManager.class);
            event.add("AnimationPlayType", AbstractClientGunEvent.AnimationPlayType.class);
        }
    }
}
