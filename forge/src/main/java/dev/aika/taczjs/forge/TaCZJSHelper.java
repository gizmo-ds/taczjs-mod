package dev.aika.taczjs.forge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tacz.guns.init.ModRecipe;
import com.tacz.guns.resource.PackConvertor;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.forge.events.AbstractIndexLoadEvent;
import dev.aika.taczjs.forge.events.ModServerEvents;
import dev.aika.taczjs.forge.events.ModStartupEvents;
import dev.aika.taczjs.forge.events.asset.AttachmentDataLoadEvent;
import dev.aika.taczjs.forge.events.asset.AttachmentTagsLoadEvent;
import dev.aika.taczjs.forge.events.asset.GunDataLoadEvent;
import dev.aika.taczjs.forge.events.index.AmmoIndexLoadEvent;
import dev.aika.taczjs.forge.events.index.AttachmentIndexLoadEvent;
import dev.aika.taczjs.forge.events.index.GunIndexLoadEvent;
import net.minecraft.resources.ResourceLocation;

public class TaCZJSHelper {
    public static final String GunSmithTableRecipeType = ModRecipe.GUN_SMITH_TABLE_CRAFTING.getId().toString();

    public static JsonObject toJsonObject(String json) {
        var object = PackConvertor.GSON.fromJson(json, JsonObject.class);
        if (!object.has("type")) object.addProperty("type", TaCZJSHelper.GunSmithTableRecipeType);
        return object;
    }

    public static AbstractIndexLoadEvent getLoadEventHandler(DataType type, ResourceLocation id, JsonElement json) {
        switch (type) {
            case GUN_INDEX: {
                var event = new GunIndexLoadEvent(id, json);
                ModStartupEvents.GUN_INDEX_LOAD_REGISTER.post(event);
                ModServerEvents.GUN_INDEX_LOAD_REGISTER.post(event);
                return event;
            }
            case AMMO_INDEX: {
                var event = new AmmoIndexLoadEvent(id, json);
                ModStartupEvents.AMMO_INDEX_LOAD_REGISTER.post(event);
                ModServerEvents.AMMO_INDEX_LOAD_REGISTER.post(event);
                return event;
            }
            case ATTACHMENT_INDEX: {
                var event = new AttachmentIndexLoadEvent(id, json);
                ModStartupEvents.ATTACHMENT_INDEX_LOAD_REGISTER.post(event);
                ModServerEvents.ATTACHMENT_INDEX_LOAD_REGISTER.post(event);
                return event;
            }
            case GUN_DATA: {
                var event = new GunDataLoadEvent(id, json);
                ModStartupEvents.GUN_DATA_LOAD_REGISTER.post(event);
                ModServerEvents.GUN_DATA_LOAD_REGISTER.post(event);
                return event;
            }
            case ATTACHMENT_DATA: {
                var event = new AttachmentDataLoadEvent(id, json);
                ModStartupEvents.ATTACHMENT_DATA_LOAD_REGISTER.post(event);
                ModServerEvents.ATTACHMENT_DATA_LOAD_REGISTER.post(event);
                return event;
            }
            case ATTACHMENT_TAGS: {
                var event = new AttachmentTagsLoadEvent(id, json);
                ModStartupEvents.ATTACHMENT_TAGS_LOAD_REGISTER.post(event);
                ModServerEvents.ATTACHMENT_TAGS_LOAD_REGISTER.post(event);
                return event;
            }
            default:
                return null;
        }
    }
}
