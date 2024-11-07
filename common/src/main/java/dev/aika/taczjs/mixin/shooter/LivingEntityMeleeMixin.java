package dev.aika.taczjs.mixin.shooter;

import com.tacz.guns.entity.shooter.LivingEntityMelee;
import dev.aika.taczjs.events.ModServerEvents;
import dev.aika.taczjs.events.shooter.LivingEntityMeleeEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityMelee.class, remap = false)
public abstract class LivingEntityMeleeMixin {
    @Shadow
    @Final
    private LivingEntity shooter;

    @Inject(method = "melee", at = @At("HEAD"), cancellable = true)
    private void onMelee(CallbackInfo ci) {
        var event = new LivingEntityMeleeEvent(this.shooter);
        ModServerEvents.ENTITY_MELEE_REGISTER.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}