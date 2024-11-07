package dev.aika.taczjs.mixin.index;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.index.CommonAmmoIndex;
import com.tacz.guns.resource.loader.index.CommonAmmoIndexLoader;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.events.ModStartupEvents;
import dev.aika.taczjs.events.index.AmmoIndexLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CommonAmmoIndexLoader.class, remap = false)
public abstract class CommonAmmoIndexLoaderMixin {
    @Inject(method = "loadAmmoFromJsonString", at = @At("HEAD"), cancellable = true)
    private static void load(ResourceLocation id, String json, CallbackInfo ci) {
        var event = new AmmoIndexLoadEvent(id, json);
        ModStartupEvents.AMMO_INDEX_LOAD_REGISTER.post(event);
        if (event.isRemove()) {
            ci.cancel();
            return;
        }
        var newJson = event.getJson();
        if (newJson.equals(json)) return;
        CommonGunPackLoader.AMMO_INDEX.put(event.getId(), CommonAmmoIndex.getInstance(event.getPOJO()));
        CommonGunPackNetwork.addData(DataType.AMMO_INDEX, event.getId(), event.getJson());
        ci.cancel();
    }
}
