package dev.aika.taczjs.events.shooter;

import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class LivingEntityMeleeEvent extends AbstractShooterEvent{
    public LivingEntityMeleeEvent(LivingEntity entity) {
        super(entity);
    }

    public void cancelMelee() {
        setCancelled();
    }
}
