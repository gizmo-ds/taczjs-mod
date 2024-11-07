package dev.aika.taczjs.events.shooter;

import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public abstract class AbstractShooterEvent extends LivingEntityEventJS {
    private final LivingEntity entity;
    private Boolean cancelled = false;

    public AbstractShooterEvent(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }

    public LivingEntity getShooter() {
        return this.getEntity();
    }

    @HideFromJS
    public boolean isCancelled() {
        return cancelled;
    }

    @HideFromJS
    public void setCancelled() {
        cancelled = true;
    }
}
