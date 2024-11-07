package dev.aika.taczjs.events.shooter;

import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public class LivingEntityReloadEvent extends AbstractShooterEvent {
    public LivingEntityReloadEvent(LivingEntity entity) {
        super(entity);
    }

    public void cancelReload() {
        setCancelled();
    }
}
