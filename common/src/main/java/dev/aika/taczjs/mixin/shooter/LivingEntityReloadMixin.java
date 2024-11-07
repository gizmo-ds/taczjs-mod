package dev.aika.taczjs.mixin.shooter;

import com.tacz.guns.entity.shooter.LivingEntityReload;
import dev.aika.taczjs.events.ModServerEvents;
import dev.aika.taczjs.events.shooter.LivingEntityReloadEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityReload.class, remap = false)
public abstract class LivingEntityReloadMixin {
    @Shadow
    @Final
    private LivingEntity shooter;

    @Inject(method = "reload", at = @At("HEAD"), cancellable = true)
    private void onReload(CallbackInfo ci) {
        var event = new LivingEntityReloadEvent(this.shooter);
        ModServerEvents.ENTITY_RELOAD_REGISTER.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}
