package dev.aika.taczjs.forge;

import dev.aika.taczjs.forge.events.ModClientEvents;
import dev.aika.taczjs.forge.events.ModServerEvents;
import dev.aika.taczjs.forge.events.ModStartupEvents;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

@SuppressWarnings("unused")
public class TaCZJSPlugin extends KubeJSPlugin {
    @Override
    public void registerEvents() {
        ModStartupEvents.GROUP.register();
        ModClientEvents.GROUP.register();
        ModServerEvents.GROUP.register();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("TaCZJSUtils", TaCZJSUtils.class);
    }
}
