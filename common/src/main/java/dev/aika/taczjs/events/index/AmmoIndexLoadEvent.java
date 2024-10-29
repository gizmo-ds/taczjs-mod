package dev.aika.taczjs.events.index;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.AmmoIndexPOJO;
import dev.aika.taczjs.events.AbstractLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class AmmoIndexLoadEvent extends AbstractLoadEvent {
    public AmmoIndexLoadEvent(ResourceLocation id, String json) {
        super(id, json);
    }

    public AmmoIndexPOJO getPOJO() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), AmmoIndexPOJO.class);
    }

    public void removeAmmo() {
        this.setRemove(true);
    }
}
