package dev.aika.taczjs.events.client;

import com.tacz.guns.client.resource.index.ClientGunIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class LocalPlayerShootEvent extends AbstractClientGunEvent {
    public LocalPlayerShootEvent(ResourceLocation gunId, ClientGunIndex gunIndex) {
        super(gunId, gunIndex);
    }

    public void cancelShoot() {
        setCancelled();
    }
}
