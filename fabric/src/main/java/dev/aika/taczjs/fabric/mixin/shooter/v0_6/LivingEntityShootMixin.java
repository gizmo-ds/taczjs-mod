package dev.aika.taczjs.fabric.mixin.shooter.v0_6;

import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.entity.shooter.LivingEntityShoot;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import dev.aika.taczjs.fabric.events.ModServerEvents;
import dev.aika.taczjs.fabric.events.shooter.LivingEntityShootEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(LivingEntityShoot.class)
public abstract class LivingEntityShootMixin {
    @Shadow(remap = false)
    @Final
    private ShooterDataHolder data;

    @Shadow
    @Final
    private LivingEntity shooter;

    @Inject(remap = false, method = "shoot(Ljava/util/function/Supplier;Ljava/util/function/Supplier;JFZ)Lcom/tacz/guns/api/entity/ShootResult;", at = @At("HEAD"), cancellable = true)
    private void onShoot(Supplier<Float> pitch, Supplier<Float> yaw, long timestamp, float chargeProgress, boolean hasChargeContext, CallbackInfoReturnable<ShootResult> cir) {
        if (this.data.currentGunItem == null || !(this.data.currentGunItem.get().getItem() instanceof IGun)) return;
        var event = new LivingEntityShootEvent(this.shooter, this.data.currentGunItem.get());
        ModServerEvents.ENTITY_SHOOT_REGISTER.post(event);
        if (event.isCancelled()) cir.setReturnValue(ShootResult.NOT_GUN);
    }
}