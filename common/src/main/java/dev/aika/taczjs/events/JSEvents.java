package dev.aika.taczjs.events;

import dev.aika.taczjs.events.asset.*;
import dev.aika.taczjs.events.client.*;
import dev.aika.taczjs.events.index.AmmoIndexLoadEvent;
import dev.aika.taczjs.events.index.AttachmentIndexLoadEvent;
import dev.aika.taczjs.events.index.GunIndexLoadEvent;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface JSEvents {
    EventGroup GROUP = EventGroup.of("TaCZJSEvents");

    EventHandler RECIPE_LOAD_REGISTER = GROUP.startup("onRecipeLoad", () -> RecipeLoadEvent.class);
    EventHandler RECIPE_LOAD_BEGIN_REGISTER = GROUP.startup("onRecipeLoadBegin", () -> RecipeLoadBeginEvent.class);
    EventHandler RECIPE_LOAD_END_REGISTER = GROUP.startup("onRecipeLoadEnd", () -> RecipeLoadEndEvent.class);

    EventHandler GUN_DATA_LOAD_REGISTER = GROUP.startup("onGunDataLoad", () -> GunDataLoadEvent.class);
    EventHandler ATTACHMENT_DATA_LOAD_REGISTER = GROUP.startup("onAttachmentDataLoad", () -> AttachmentDataLoadEvent.class);
    EventHandler ATTACHMENT_TAGS_LOAD_REGISTER = GROUP.startup("onAttachmentTagsLoad", () -> AttachmentTagsLoadEvent.class);

    EventHandler GUN_INDEX_LOAD_REGISTER = GROUP.startup("onGunIndexLoad", () -> GunIndexLoadEvent.class);
    EventHandler AMMO_INDEX_LOAD_REGISTER = GROUP.startup("onAmmoIndexLoad", () -> AmmoIndexLoadEvent.class);
    EventHandler ATTACHMENT_INDEX_LOAD_REGISTER = GROUP.startup("onAttachmentIndexLoad", () -> AttachmentIndexLoadEvent.class);

    EventHandler CLIENT_GUN_INDEX_LOAD_REGISTER = GROUP.client("onGunIndexLoad", () -> ClientGunIndexLoadEvent.class);
    EventHandler CLIENT_LOCAL_PLAYER_AIM_REGISTER = GROUP.client("onAim", () -> LocalPlayerAimEvent.class);
    EventHandler CLIENT_LOCAL_PLAYER_SHOOT_REGISTER = GROUP.client("onShoot", () -> LocalPlayerShootEvent.class);
    EventHandler CLIENT_LOCAL_PLAYER_MELEE_REGISTER = GROUP.client("onMelee", () -> LocalPlayerMeleeEvent.class);
    EventHandler CLIENT_LOCAL_PLAYER_RELOAD_REGISTER = GROUP.client("onReload", () -> LocalPlayerReloadEvent.class);
}
