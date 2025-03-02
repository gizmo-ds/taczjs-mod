package dev.aika.taczjs.forge.events;

import dev.aika.taczjs.forge.events.shooter.*;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface ModServerEvents {
    EventGroup GROUP = EventGroup.of("TaCZServerEvents");

    EventHandler ENTITY_SHOOT_REGISTER = GROUP.server("entityShoot", () -> LivingEntityShootEvent.class);
    EventHandler ENTITY_AIM_REGISTER = GROUP.server("entityAim", () -> LivingEntityAimEvent.class);
    EventHandler ENTITY_MELEE_REGISTER = GROUP.server("entityMelee", () -> LivingEntityMeleeEvent.class);
    EventHandler ENTITY_RELOAD_REGISTER = GROUP.server("entityReload", () -> LivingEntityReloadEvent.class);
}
