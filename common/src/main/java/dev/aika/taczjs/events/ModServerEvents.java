package dev.aika.taczjs.events;

import dev.aika.taczjs.events.asset.RecipeLoadBeginEvent;
import dev.aika.taczjs.events.shooter.LivingEntityAimEvent;
import dev.aika.taczjs.events.shooter.LivingEntityMeleeEvent;
import dev.aika.taczjs.events.shooter.LivingEntityReloadEvent;
import dev.aika.taczjs.events.shooter.LivingEntityShootEvent;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface ModServerEvents {
    EventGroup GROUP = EventGroup.of("TaCZServerEvents");

    EventHandler ENTITY_SHOOT_REGISTER = GROUP.server("entityShoot", () -> LivingEntityShootEvent.class);
    EventHandler ENTITY_AIM_REGISTER = GROUP.server("entityAim", () -> LivingEntityAimEvent.class);
    EventHandler ENTITY_MELEE_REGISTER = GROUP.server("entityMelee", () -> LivingEntityMeleeEvent.class);
    EventHandler ENTITY_RELOAD_REGISTER = GROUP.server("entityReload", () -> LivingEntityReloadEvent.class);

    EventHandler RECIPES_REGISTER = GROUP.server("recipes", () -> RecipeLoadBeginEvent.class);
}
