package dev.aika.taczjs.mixin.client;

import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.gameplay.LocalPlayerShoot;
import dev.aika.taczjs.events.JSEvents;
import dev.aika.taczjs.events.client.LocalPlayerShootEvent;
import dev.aika.taczjs.helper.ModClientHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = LocalPlayerShoot.class, remap = false)
public abstract class LocalPlayerShootMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Inject(method = "shoot", at = @At("HEAD"), cancellable = true)
    private void shoot(CallbackInfoReturnable<ShootResult> cir) {
        var mainHandItem = this.player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IGun iGun) {
            var gunId = iGun.getGunId(mainHandItem);
            ModClientHelper.getClientGunIndex(gunId).ifPresent(gunIndex -> {
                var event = new LocalPlayerShootEvent(gunId, gunIndex);
                JSEvents.CLIENT_LOCAL_PLAYER_SHOOT_REGISTER.post(event);
                if (event.isCancelled()) cir.setReturnValue(ShootResult.SUCCESS);
            });
        }
    }
}
