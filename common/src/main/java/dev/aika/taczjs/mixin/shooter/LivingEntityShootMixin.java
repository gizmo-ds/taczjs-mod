package dev.aika.taczjs.mixin.shooter;

import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.entity.shooter.LivingEntityShoot;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
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

    @Shadow
    @Final
    private ShooterDataHolder data;

    @Inject(method = "shoot", at = @At(value = "INVOKE",
            target = "Lcom/tacz/guns/api/item/IGun;getGunId(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/resources/ResourceLocation;"),
            cancellable = true)
    private void onShoot(Supplier<Float> pitch, Supplier<Float> yaw, CallbackInfoReturnable<ShootResult> cir) {
        assert this.data.currentGunItem != null;
        var event = new LivingEntityShootEvent(this.shooter, this.data.currentGunItem.get());
        ModServerEvents.ENTITY_SHOOT_REGISTER.post(event);
        if (event.isCancelled()) cir.setReturnValue(ShootResult.NOT_GUN);
    }
}
