package dev.aika.taczjs.events.client;

import com.tacz.guns.client.resource.index.ClientGunIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class LocalPlayerAimEvent extends AbstractClientGunEvent {
    private final boolean isAim;

    public LocalPlayerAimEvent(boolean isAim, ResourceLocation gunId, ClientGunIndex gunIndex) {
        super(gunId, gunIndex);
        this.isAim = isAim;
    }

    public boolean isAim() {
        return isAim;
    }

    public void cancelAim() {
        setCancelled();
    }
}
