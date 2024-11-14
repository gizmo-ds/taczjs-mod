package dev.aika.taczjs.events;

import com.tacz.guns.resource.CommonGunPackLoader;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractLoadEvent extends EventJS {
    private final ResourceLocation id;
    private String json;
    private Boolean cancelled;

    public AbstractLoadEvent(ResourceLocation id, String json) {
        this.id = id;
        this.json = json;
        this.cancelled = false;
    }

    public ResourceLocation getId() {
        return id;
    }

    @Info("The returned data may not conform to standard JSON format.")
    public String getJson() {
        return json;
    }

    @Info("Get the JSON data in standard format.")
    public String getStdJson() {
        return CommonGunPackLoader.GSON.toJson(CommonGunPackLoader.GSON.fromJson(json, Object.class));
    }

    public void setJson(String json) {
        this.json = json;
    }

    @HideFromJS
    public Boolean isRemove() {
        return cancelled;
    }

    @HideFromJS
    public void setRemove(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
