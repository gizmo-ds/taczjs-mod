package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.client.gameplay.LocalPlayerReload;
import dev.aika.taczjs.forge.events.ModClientEvents;
import dev.aika.taczjs.forge.events.client.LocalPlayerReloadEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(value = LocalPlayerReload.class, remap = false)
public abstract class LocalPlayerReloadMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Shadow public abstract void cancelReload();

    @Inject(method = "reload", at = @At("HEAD"), cancellable = true)
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
