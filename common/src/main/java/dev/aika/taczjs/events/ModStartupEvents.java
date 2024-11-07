package dev.aika.taczjs.events;

import dev.aika.taczjs.events.asset.*;
import dev.aika.taczjs.events.index.AmmoIndexLoadEvent;
import dev.aika.taczjs.events.index.AttachmentIndexLoadEvent;
import dev.aika.taczjs.events.index.GunIndexLoadEvent;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface ModStartupEvents {
    EventGroup GROUP = EventGroup.of("TaCZStartupEvents");

    EventHandler RECIPE_LOAD_REGISTER = GROUP.startup("recipeLoad", () -> RecipeLoadEvent.class);
    EventHandler RECIPE_LOAD_BEGIN_REGISTER = GROUP.startup("recipeLoadBegin", () -> RecipeLoadBeginEvent.class);
    EventHandler RECIPE_LOAD_END_REGISTER = GROUP.startup("recipeLoadEnd", () -> RecipeLoadEndEvent.class);

    EventHandler GUN_DATA_LOAD_REGISTER = GROUP.startup("gunDataLoad", () -> GunDataLoadEvent.class);
    EventHandler ATTACHMENT_DATA_LOAD_REGISTER = GROUP.startup("attachmentDataLoad", () -> AttachmentDataLoadEvent.class);
    EventHandler ATTACHMENT_TAGS_LOAD_REGISTER = GROUP.startup("attachmentTagsLoad", () -> AttachmentTagsLoadEvent.class);

    EventHandler GUN_INDEX_LOAD_REGISTER = GROUP.startup("gunIndexLoad", () -> GunIndexLoadEvent.class);
    EventHandler AMMO_INDEX_LOAD_REGISTER = GROUP.startup("ammoIndexLoad", () -> AmmoIndexLoadEvent.class);
    EventHandler ATTACHMENT_INDEX_LOAD_REGISTER = GROUP.startup("attachmentIndexLoad", () -> AttachmentIndexLoadEvent.class);
}
