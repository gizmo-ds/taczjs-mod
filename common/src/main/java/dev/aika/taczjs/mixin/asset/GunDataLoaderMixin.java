package dev.aika.taczjs.mixin.asset;

import com.tacz.guns.resource.CommonAssetManager;
import com.tacz.guns.resource.loader.asset.GunDataLoader;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.events.JSEvents;
import dev.aika.taczjs.events.asset.GunDataLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = GunDataLoader.class, remap = false)
public abstract class GunDataLoaderMixin {
    @Inject(method = "loadFromJsonString", at = @At("HEAD"), cancellable = true)
    private static void load(ResourceLocation id, String json, CallbackInfo ci) {
        var event = new GunDataLoadEvent(id, json);
        JSEvents.GUN_DATA_LOAD_REGISTER.post(event);
        if (event.isRemove()) {
            ci.cancel();
            return;
        }
        var newJson = event.getJson();
        if (Objects.equals(newJson, "") || newJson.equals(json)) return;
        CommonAssetManager.INSTANCE.putGunData(id, event.getGunData());
        CommonGunPackNetwork.addData(DataType.GUN_DATA, id, newJson);
        ci.cancel();
    }
}
