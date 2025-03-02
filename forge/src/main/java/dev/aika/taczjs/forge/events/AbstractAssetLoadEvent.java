package dev.aika.taczjs.forge.events;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public abstract class AbstractAssetLoadEvent extends AbstractIndexLoadEvent {
    public AbstractAssetLoadEvent(ResourceLocation id, JsonElement json) {
        super(id, json);
    }
}
