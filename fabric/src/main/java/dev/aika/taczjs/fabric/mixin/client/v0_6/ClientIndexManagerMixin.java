package dev.aika.taczjs.fabric.mixin.client.v0_6;

import com.tacz.guns.client.resource.ClientIndexManager;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import dev.aika.taczjs.fabric.events.ModClientEvents;
import dev.aika.taczjs.fabric.events.client.ClientGunIndexLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ClientIndexManager.class)
public abstract class ClientIndexManagerMixin {
    @Inject(remap = false, method = "lambda$loadGunIndex$0", at = @At(
            value = "INVOKE", shift = At.Shift.AFTER,
            target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static void loadGunIndex(Map.Entry<ResourceLocation, ClientGunIndex> index, CallbackInfo ci) {
        var event = new ClientGunIndexLoadEvent(index.getKey());
        ModClientEvents.GUN_INDEX_LOAD_REGISTER.post(event);
    }
}