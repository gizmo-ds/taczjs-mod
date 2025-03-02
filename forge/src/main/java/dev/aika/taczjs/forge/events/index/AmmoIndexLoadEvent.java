package dev.aika.taczjs.forge.events.index;

import com.google.gson.JsonElement;
import dev.aika.taczjs.forge.events.AbstractIndexLoadEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

@SuppressWarnings("unused")
public class AmmoIndexLoadEvent extends AbstractIndexLoadEvent {
    public AmmoIndexLoadEvent(ResourceLocation id, JsonElement json) {
        super(id, json);
    }

    public Object getPOJO() {
        return GsonHelper.parse(this.getJson(), true);
    }

    public void removeAmmo() {
        this.setRemove(true);
    }
}
