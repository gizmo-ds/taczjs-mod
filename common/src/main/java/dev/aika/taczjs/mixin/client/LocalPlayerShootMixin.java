package dev.aika.taczjs.mixin.client;

import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.gameplay.LocalPlayerShoot;
import dev.aika.taczjs.events.ModClientEvents;
import dev.aika.taczjs.events.client.LocalPlayerShootEvent;
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
            var event = new LocalPlayerShootEvent(iGun.getGunId(mainHandItem));
            ModClientEvents.PLAYER_SHOOT_REGISTER.post(event);
            if (event.isCancelled()) cir.setReturnValue(ShootResult.SUCCESS);
        }
    }
}
