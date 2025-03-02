package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.client.resource.ClientIndexManager;
import com.tacz.guns.resource.index.CommonGunIndex;
import dev.aika.taczjs.forge.events.ModClientEvents;
import dev.aika.taczjs.forge.events.client.ClientGunIndexLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = ClientIndexManager.class, remap = false)
public abstract class ClientIndexManagerMixin {
    @Inject(method = "lambda$loadGunIndex$1", at = @At(
            value = "INVOKE",
            target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            shift = At.Shift.AFTER))
    private static void loadGunIndex(Map.Entry<ResourceLocation, CommonGunIndex> index, CallbackInfo ci) {
        var event = new ClientGunIndexLoadEvent(index.getKey());
        ModClientEvents.GUN_INDEX_LOAD_REGISTER.post(event);
    }
}
