package dev.aika.taczjs.events.shooter;

import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class LivingEntityShootEvent extends AbstractShooterEvent {
    public LivingEntityShootEvent(LivingEntity entity) {
        super(entity);
    }

    public void cancelShoot() {
        setCancelled();
    }
}
