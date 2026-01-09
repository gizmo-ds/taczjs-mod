package dev.aika.taczjs.fabric.mixin.client;

import com.tacz.guns.client.resource.index.ClientGunIndex;
import dev.aika.taczjs.fabric.interfaces.client.IClientGun;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Implements(@Interface(iface = IClientGun.class, prefix = "taczjs$"))
@Mixin(ClientGunIndex.class)
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
