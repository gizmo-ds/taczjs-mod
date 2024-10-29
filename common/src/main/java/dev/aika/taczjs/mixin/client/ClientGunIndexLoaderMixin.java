package dev.aika.taczjs.mixin.client;

import com.tacz.guns.client.resource.ClientGunPackLoader;
import com.tacz.guns.client.resource.loader.index.ClientGunIndexLoader;
import com.tacz.guns.resource.index.CommonGunIndex;
import dev.aika.taczjs.events.JSEvents;
import dev.aika.taczjs.events.client.ClientGunIndexLoadEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(value = ClientGunIndexLoader.class, remap = false)
public abstract class ClientGunIndexLoaderMixin {
    @Inject(method = "lambda$loadGunIndex$0", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            shift = At.Shift.AFTER))
    private static void loadGunIndex(Map.Entry<ResourceLocation, CommonGunIndex> index, CallbackInfo ci) {
        var id = index.getKey();
        var gunIndex = ClientGunPackLoader.GUN_INDEX.get(id);
        var event = new ClientGunIndexLoadEvent(id, gunIndex);
        JSEvents.CLIENT_GUN_INDEX_LOAD_REGISTER.post(event);
    }
}
