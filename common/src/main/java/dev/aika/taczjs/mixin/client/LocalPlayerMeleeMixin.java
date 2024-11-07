package dev.aika.taczjs.mixin.client;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.gameplay.LocalPlayerMelee;
import dev.aika.taczjs.events.ModClientEvents;
import dev.aika.taczjs.events.client.LocalPlayerMeleeEvent;
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
@Mixin(value = LocalPlayerMelee.class, remap = false)
public abstract class LocalPlayerMeleeMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Inject(method = "melee", at = @At("HEAD"), cancellable = true)
    private void melee(CallbackInfo ci) {
        var mainHandItem = this.player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IGun iGun) {
            var gunId = iGun.getGunId(mainHandItem);
            ModClientHelper.getClientGunIndex(gunId).ifPresent(gunIndex -> {
                var event = new LocalPlayerMeleeEvent(gunId, gunIndex);
                ModClientEvents.PLAYER_MELEE_REGISTER.post(event);
                if (event.isCancelled()) ci.cancel();
            });
        }
    }
}
