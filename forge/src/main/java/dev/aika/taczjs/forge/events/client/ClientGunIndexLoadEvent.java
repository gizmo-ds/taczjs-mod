package dev.aika.taczjs.forge.events.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class ClientGunIndexLoadEvent extends AbstractClientGunEvent {
    public ClientGunIndexLoadEvent(ResourceLocation gunId) {
        super(gunId);
    }
}
