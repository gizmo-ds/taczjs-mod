package dev.aika.taczjs.events.shooter;

import com.tacz.guns.api.item.IGun;
import dev.latvian.mods.kubejs.entity.LivingEntityEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("unused")
public abstract class AbstractShooterEvent extends LivingEntityEventJS {
    private final LivingEntity entity;
    private final ItemStack gunItem;
    private Boolean cancelled = false;

    public AbstractShooterEvent(LivingEntity entity, ItemStack gunItem) {
        this.entity = entity;
        this.gunItem = gunItem;
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

    public ResourceLocation getGunId() {
        if (gunItem.getItem() instanceof IGun iGun) return iGun.getGunId(gunItem);
        return null;
    }
}
