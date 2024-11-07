package dev.aika.taczjs;

import dev.aika.taczjs.events.ModClientEvents;
import dev.aika.taczjs.events.ModServerEvents;
import dev.aika.taczjs.events.ModStartupEvents;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

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
