package dev.aika.taczjs.fabric.mixin.shooter;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.entity.shooter.LivingEntityMelee;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import dev.aika.taczjs.fabric.events.ModServerEvents;
import dev.aika.taczjs.fabric.events.shooter.LivingEntityMeleeEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityMelee.class)
public abstract class LivingEntityMeleeMixin {
    @Shadow(remap = false)
    @Final
    private ShooterDataHolder data;

    @Shadow
    @Final
    private LivingEntity shooter;

    @Inject(remap = false, method = "melee", at = @At("HEAD"), cancellable = true)
    private void onMelee(CallbackInfo ci) {
        if (this.data.currentGunItem == null || !(this.data.currentGunItem.get().getItem() instanceof IGun)) return;
        var event = new LivingEntityMeleeEvent(this.shooter, this.data.currentGunItem.get());
        ModServerEvents.ENTITY_MELEE_REGISTER.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}
