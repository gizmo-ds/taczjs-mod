package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.client.resource.index.ClientGunIndex;
import dev.aika.taczjs.forge.interfaces.client.IClientGun;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@OnlyIn(Dist.CLIENT)
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
