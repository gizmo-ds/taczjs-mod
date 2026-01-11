package dev.aika.taczjs.neoforge;

import dev.aika.taczjs.neoforge.events.ModClientEvents;
import dev.aika.taczjs.neoforge.events.ModServerEvents;
import dev.aika.taczjs.neoforge.events.ModStartupEvents;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

@SuppressWarnings("unused")
public class TaCZJSPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(ModStartupEvents.GROUP);
        registry.register(ModClientEvents.GROUP);
        registry.register(ModServerEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry event) {
        event.add("TaCZJSUtils", TaCZJSUtils.class);
    }
}
