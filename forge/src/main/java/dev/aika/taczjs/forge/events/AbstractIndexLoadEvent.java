package dev.aika.taczjs.forge.events;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

@SuppressWarnings("unused")
public abstract class AbstractIndexLoadEvent extends EventJS {
    private final ResourceLocation id;
    private Boolean cancelled;
    private String newJson;
    private String json;
    private final JsonElement jsonElement;

    public AbstractIndexLoadEvent(ResourceLocation id, JsonElement json) {
        this.id = id;
        this.json = null;
        this.newJson = null;
        this.jsonElement = json;
        this.cancelled = false;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getJson() {
        if (this.newJson != null) return this.newJson;
        if (this.json != null) return this.json;
        if (this.jsonElement != null) {
            this.json = GsonHelper.toStableString(this.jsonElement);
            return this.json;
        }
        return null;
    }

    public String getStdJson() {
        return getJson();
    }

    public void setJson(String json) {
        this.newJson = json;
    }

    @HideFromJS
    public Boolean isModified() {
        return this.newJson != null || this.json != null;
    }

    @HideFromJS
    public Boolean isRemove() {
        return this.cancelled;
    }

    @HideFromJS
    public void setRemove(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
