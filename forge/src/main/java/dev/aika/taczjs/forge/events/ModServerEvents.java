package dev.aika.taczjs.forge.events;

import dev.aika.taczjs.forge.events.asset.AttachmentDataLoadEvent;
import dev.aika.taczjs.forge.events.asset.AttachmentTagsLoadEvent;
import dev.aika.taczjs.forge.events.asset.GunDataLoadEvent;
import dev.aika.taczjs.forge.events.index.AmmoIndexLoadEvent;
import dev.aika.taczjs.forge.events.index.AttachmentIndexLoadEvent;
import dev.aika.taczjs.forge.events.index.GunIndexLoadEvent;
import dev.aika.taczjs.forge.events.shooter.*;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface ModServerEvents {
    EventGroup GROUP = EventGroup.of("TaCZServerEvents");

    EventHandler ENTITY_SHOOT_REGISTER = GROUP.server("entityShoot", () -> LivingEntityShootEvent.class);
    EventHandler ENTITY_AIM_REGISTER = GROUP.server("entityAim", () -> LivingEntityAimEvent.class);
    EventHandler ENTITY_MELEE_REGISTER = GROUP.server("entityMelee", () -> LivingEntityMeleeEvent.class);
    EventHandler ENTITY_RELOAD_REGISTER = GROUP.server("entityReload", () -> LivingEntityReloadEvent.class);

    EventHandler GUN_INDEX_LOAD_REGISTER = GROUP.server("gunIndexLoad", () -> GunIndexLoadEvent.class);
    EventHandler AMMO_INDEX_LOAD_REGISTER = GROUP.server("ammoIndexLoad", () -> AmmoIndexLoadEvent.class);
    EventHandler ATTACHMENT_INDEX_LOAD_REGISTER = GROUP.server("attachmentIndexLoad", () -> AttachmentIndexLoadEvent.class);

    EventHandler GUN_DATA_LOAD_REGISTER = GROUP.server("gunDataLoad", () -> GunDataLoadEvent.class);
    EventHandler ATTACHMENT_DATA_LOAD_REGISTER = GROUP.server("attachmentDataLoad", () -> AttachmentDataLoadEvent.class);
    EventHandler ATTACHMENT_TAGS_LOAD_REGISTER = GROUP.server("attachmentTagsLoad", () -> AttachmentTagsLoadEvent.class);
}
