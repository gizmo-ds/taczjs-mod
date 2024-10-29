package dev.aika.taczjs.events.client;

import com.tacz.guns.client.resource.index.ClientGunIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class LocalPlayerReloadEvent extends AbstractClientGunEvent {
    public LocalPlayerReloadEvent(ResourceLocation gunId, ClientGunIndex gunIndex) {
        super(gunId, gunIndex);
    }

    public void cancelReload() {
        setCancelled();
    }
}
