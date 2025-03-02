package dev.aika.taczjs.forge.events.shooter;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public class LivingEntityMeleeEvent extends AbstractShooterEvent {
    public LivingEntityMeleeEvent(LivingEntity entity, ItemStack gunItem) {
        super(entity, gunItem);
    }

    public void cancelMelee() {
        setCancelled();
    }
}
