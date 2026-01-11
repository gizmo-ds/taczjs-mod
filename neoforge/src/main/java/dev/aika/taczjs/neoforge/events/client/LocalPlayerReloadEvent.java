package dev.aika.taczjs.neoforge.events.client;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class LocalPlayerReloadEvent extends AbstractClientGunEvent {
    public LocalPlayerReloadEvent(ResourceLocation gunId) {
        super(gunId);
    }

    public void cancelReload() {
        setCancelled();
    }
}
