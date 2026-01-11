package dev.aika.taczjs.neoforge.events.client;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class ClientGunIndexLoadEvent extends AbstractClientGunEvent {
    public ClientGunIndexLoadEvent(ResourceLocation gunId) {
        super(gunId);
    }
}
