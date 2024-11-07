package dev.aika.taczjs.events.shooter;

import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class LivingEntityAimEvent extends AbstractShooterEvent{
    public LivingEntityAimEvent(LivingEntity entity) {
        super(entity);
    }

    public void cancelAim() {
        setCancelled();
    }
}
