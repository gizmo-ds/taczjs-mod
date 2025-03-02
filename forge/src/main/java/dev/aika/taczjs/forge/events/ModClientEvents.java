package dev.aika.taczjs.forge.events;

import dev.aika.taczjs.forge.events.client.*;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface ModClientEvents {
    EventGroup GROUP = EventGroup.of("TaCZClientEvents");

    EventHandler GUN_INDEX_LOAD_REGISTER = GROUP.client("gunIndexLoad", () -> ClientGunIndexLoadEvent.class);

    EventHandler PLAYER_AIM_REGISTER = GROUP.client("playerAim", () -> LocalPlayerAimEvent.class);
    EventHandler PLAYER_SHOOT_REGISTER = GROUP.client("playerShoot", () -> LocalPlayerShootEvent.class);
    EventHandler PLAYER_MELEE_REGISTER = GROUP.client("playerMelee", () -> LocalPlayerMeleeEvent.class);
    EventHandler PLAYER_RELOAD_REGISTER = GROUP.client("playerReload", () -> LocalPlayerReloadEvent.class);
}
