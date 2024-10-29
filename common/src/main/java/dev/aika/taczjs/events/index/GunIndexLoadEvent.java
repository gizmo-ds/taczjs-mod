package dev.aika.taczjs.events.index;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.GunIndexPOJO;
import dev.aika.taczjs.events.AbstractLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class GunIndexLoadEvent extends AbstractLoadEvent {
    public GunIndexLoadEvent(ResourceLocation id, String json) {
        super(id, json);
    }

    public GunIndexPOJO getPOJO() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), GunIndexPOJO.class);
    }

    public void removeGun() {
        this.setRemove(true);
    }
}
