package dev.aika.taczjs.mixin.index;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.loader.index.CommonGunIndexLoader;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.events.JSEvents;
import dev.aika.taczjs.events.index.GunIndexLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CommonGunIndexLoader.class, remap = false)
public abstract class CommonGunIndexLoaderMixin {
    @Inject(method = "loadGunFromJsonString", at = @At("HEAD"), cancellable = true)
    private static void load(ResourceLocation id, String json, CallbackInfo ci) {
        var event = new GunIndexLoadEvent(id, json);
        JSEvents.GUN_INDEX_LOAD_REGISTER.post(event);
        if (event.isRemove()) {
            ci.cancel();
            return;
        }
        var newJson = event.getJson();
        if (newJson.equals(json)) return;
        CommonGunPackLoader.GUN_INDEX.put(event.getId(), CommonGunIndex.getInstance(event.getPOJO()));
        CommonGunPackNetwork.addData(DataType.GUN_INDEX, event.getId(), event.getJson());
        ci.cancel();
    }
}
