package dev.aika.taczjs.fabric.mixin.client;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.client.gameplay.LocalPlayerMelee;
import dev.aika.taczjs.fabric.events.ModClientEvents;
import dev.aika.taczjs.fabric.events.client.LocalPlayerMeleeEvent;
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
@Mixin(LocalPlayerMelee.class)
public abstract class LocalPlayerMeleeMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Inject(remap = false, method = "melee", at = @At("HEAD"), cancellable = true)
    private void melee(CallbackInfo ci) {
        var mainHandItem = this.player.getMainHandItem();
        if (mainHandItem.getItem() instanceof AbstractGunItem gun) {
            var event = new LocalPlayerMeleeEvent(gun.getGunId(mainHandItem));
            ModClientEvents.PLAYER_MELEE_REGISTER.post(event);
            if (event.isCancelled()) ci.cancel();
        }
    }
}
