package dev.aika.taczjs.events.asset;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import dev.aika.taczjs.events.AbstractLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class GunDataLoadEvent extends AbstractLoadEvent {
    public GunDataLoadEvent(ResourceLocation id, String json) {
        super(id, json);
    }

    public GunData getGunData() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), GunData.class);
    }

    public void removeGunData() {
        this.setRemove(true);
    }
}
