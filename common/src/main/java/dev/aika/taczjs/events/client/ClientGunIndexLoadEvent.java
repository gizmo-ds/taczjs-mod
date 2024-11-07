package dev.aika.taczjs.events.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class ClientGunIndexLoadEvent extends AbstractClientGunEvent {
    public ClientGunIndexLoadEvent(ResourceLocation gunId) {
        super(gunId);
    }
}
