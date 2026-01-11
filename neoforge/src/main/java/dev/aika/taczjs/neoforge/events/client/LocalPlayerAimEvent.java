package dev.aika.taczjs.neoforge.events.client;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class LocalPlayerAimEvent extends AbstractClientGunEvent {
    private final boolean isAim;

    public LocalPlayerAimEvent(boolean isAim, ResourceLocation gunId) {
        super(gunId);
        this.isAim = isAim;
    }

    public boolean isAim() {
        return isAim;
    }

    public void cancelAim() {
        setCancelled();
    }
}
