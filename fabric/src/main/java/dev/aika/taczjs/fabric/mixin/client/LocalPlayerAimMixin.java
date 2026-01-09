package dev.aika.taczjs.fabric.mixin.client;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.client.gameplay.LocalPlayerAim;
import dev.aika.taczjs.fabric.events.ModClientEvents;
import dev.aika.taczjs.fabric.events.client.LocalPlayerAimEvent;
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
@Mixin(LocalPlayerAim.class)
public abstract class LocalPlayerAimMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Inject(remap = false, method = "aim", at = @At("HEAD"), cancellable = true)
    private void aim(boolean isAim, CallbackInfo ci) {
        var mainHandItem = this.player.getMainHandItem();
        if (mainHandItem.getItem() instanceof AbstractGunItem gun) {
            var event = new LocalPlayerAimEvent(isAim, gun.getGunId(mainHandItem));
            ModClientEvents.PLAYER_AIM_REGISTER.post(event);
            if (event.isCancelled())
                ci.cancel();
        }
    }
}
