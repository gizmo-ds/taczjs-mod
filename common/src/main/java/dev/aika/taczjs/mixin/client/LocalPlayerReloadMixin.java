package dev.aika.taczjs.mixin.client;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.gameplay.LocalPlayerReload;
import dev.aika.taczjs.events.ModClientEvents;
import dev.aika.taczjs.events.client.LocalPlayerReloadEvent;
import dev.aika.taczjs.helper.ModClientHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = LocalPlayerReload.class, remap = false)
public abstract class LocalPlayerReloadMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Inject(method = "reload", at = @At("HEAD"), cancellable = true)
    private void reload(CallbackInfo ci) {
        var mainHandItem = this.player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IGun iGun) {
            var gunId = iGun.getGunId(mainHandItem);
            ModClientHelper.getClientGunIndex(gunId).ifPresent(gunIndex -> {
                var event = new LocalPlayerReloadEvent(gunId, gunIndex);
                ModClientEvents.PLAYER_RELOAD_REGISTER.post(event);
                if (event.isCancelled()) ci.cancel();
            });
        }
    }
}
