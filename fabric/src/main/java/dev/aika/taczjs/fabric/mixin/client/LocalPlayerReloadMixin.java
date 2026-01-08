package dev.aika.taczjs.fabric.mixin.client;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.client.gameplay.LocalPlayerReload;
import dev.aika.taczjs.fabric.events.ModClientEvents;
import dev.aika.taczjs.fabric.events.client.LocalPlayerReloadEvent;
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
@Mixin(LocalPlayerReload.class)
public abstract class LocalPlayerReloadMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Shadow(remap = false)
    public abstract void cancelReload();

    @Inject(remap = false, method = "reload", at = @At("HEAD"), cancellable = true)
    private void reload(CallbackInfo ci) {
        var mainHandItem = this.player.getMainHandItem();
        if (mainHandItem.getItem() instanceof AbstractGunItem gun) {
            var event = new LocalPlayerReloadEvent(gun.getGunId(mainHandItem));
            ModClientEvents.PLAYER_RELOAD_REGISTER.post(event);
            if (event.isCancelled()) {
                this.cancelReload();
                ci.cancel();
            }
        }
    }
}
