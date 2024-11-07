package dev.aika.taczjs.mixin.shooter;

import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.entity.shooter.LivingEntityShoot;
import dev.aika.taczjs.events.ModServerEvents;
import dev.aika.taczjs.events.shooter.LivingEntityShootEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(value = LivingEntityShoot.class, remap = false)
public abstract class LivingEntityShootMixin {
    @Shadow
    @Final
    private LivingEntity shooter;

    @Inject(method = "shoot", at = @At("HEAD"), cancellable = true)
    private void onShoot(Supplier<Float> pitch, Supplier<Float> yaw, CallbackInfoReturnable<ShootResult> cir) {
        var event = new LivingEntityShootEvent(this.shooter);
        ModServerEvents.ENTITY_SHOOT_REGISTER.post(event);
        if (event.isCancelled()) cir.setReturnValue(ShootResult.NOT_GUN);
    }
}
