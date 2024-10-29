package dev.aika.taczjs.mixin.client;

import com.tacz.guns.client.resource.index.ClientGunIndex;
import dev.aika.taczjs.interfaces.IClientGun;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.*;

@Environment(EnvType.CLIENT)
@Implements(@Interface(iface = IClientGun.class, prefix = "taczjs$"))
@Mixin(value = ClientGunIndex.class, remap = false)
public abstract class ClientGunIndexMixin {
    @Unique
    private boolean taczjs$isVanillaInteract = false;

    public boolean taczjs$isVanillaInteract() {
        return taczjs$isVanillaInteract;
    }

    public void taczjs$setVanillaInteract(boolean v) {
        this.taczjs$isVanillaInteract = v;
    }
}
