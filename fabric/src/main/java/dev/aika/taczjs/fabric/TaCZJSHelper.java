package dev.aika.taczjs.fabric;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tacz.guns.init.ModRecipe;
import com.tacz.guns.resource.PackConvertor;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.fabric.events.AbstractIndexLoadEvent;
import dev.aika.taczjs.fabric.events.ModServerEvents;
import dev.aika.taczjs.fabric.events.ModStartupEvents;
import dev.aika.taczjs.fabric.events.asset.AttachmentDataLoadEvent;
import dev.aika.taczjs.fabric.events.asset.AttachmentTagsLoadEvent;
import dev.aika.taczjs.fabric.events.asset.GunDataLoadEvent;
import dev.aika.taczjs.fabric.events.index.AmmoIndexLoadEvent;
import dev.aika.taczjs.fabric.events.index.AttachmentIndexLoadEvent;
import dev.aika.taczjs.fabric.events.index.GunIndexLoadEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class TaCZJSHelper {
    public static final String GunSmithTableRecipeType = Objects.requireNonNull(BuiltInRegistries.RECIPE_TYPE.getKey(ModRecipe.GUN_SMITH_TABLE_CRAFTING)).toString();

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
