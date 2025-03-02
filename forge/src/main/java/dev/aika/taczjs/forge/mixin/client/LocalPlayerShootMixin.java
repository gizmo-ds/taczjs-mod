package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.client.gameplay.LocalPlayerShoot;
import dev.aika.taczjs.forge.events.ModClientEvents;
import dev.aika.taczjs.forge.events.client.LocalPlayerShootEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(value = LocalPlayerShoot.class, remap = false)
public abstract class LocalPlayerShootMixin {
    @Shadow
    @Final
    private LocalPlayer player;

    @Inject(method = "shoot", at = @At("HEAD"), cancellable = true)
    private void shoot(CallbackInfoReturnable<ShootResult> cir) {
        var mainHandItem = this.player.getMainHandItem();
        if (mainHandItem.getItem() instanceof AbstractGunItem gun) {
            var event = new LocalPlayerShootEvent(gun.getGunId(mainHandItem));
            ModClientEvents.PLAYER_SHOOT_REGISTER.post(event);
            if (event.isCancelled())
                cir.setReturnValue(ShootResult.SUCCESS);
        }
    }
}
