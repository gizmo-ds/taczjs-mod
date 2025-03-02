package dev.aika.taczjs.forge.events.asset;

import com.google.gson.JsonElement;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import dev.aika.taczjs.forge.events.AbstractAssetLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class GunDataLoadEvent extends AbstractAssetLoadEvent {
    public GunDataLoadEvent(ResourceLocation id, JsonElement json) {
        super(id, json);
    }

    public GunData getGunData() {
        return CommonAssetsManager.GSON.fromJson(this.getJson(), GunData.class);
    }

    public void removeGunData() {
        this.setRemove(true);
    }
}
