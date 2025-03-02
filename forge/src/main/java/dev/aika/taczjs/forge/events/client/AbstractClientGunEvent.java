package dev.aika.taczjs.forge.events.client;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.config.util.InteractKeyConfigRead;
import dev.aika.taczjs.forge.TaCZJSUtils;
import dev.aika.taczjs.forge.interfaces.client.IClientGun;
import dev.latvian.mods.kubejs.client.ClientEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public abstract class AbstractClientGunEvent extends ClientEventJS {
    private Boolean cancelled = false;
    private final ResourceLocation gunId;

    AbstractClientGunEvent(ResourceLocation gunId) {
        this.gunId = gunId;
    }

    public ResourceLocation getGunId() {
        return gunId;
    }

    public ClientGunIndex getGunIndex() {
        return TimelessAPI.getClientGunIndex(gunId).orElse(null);
    }

    @HideFromJS
    public boolean isCancelled() {
        return cancelled;
    }

    @HideFromJS
    public void setCancelled() {
        cancelled = true;
    }

    public void setVanillaInteract(boolean v) {
        if (getGunIndex() instanceof IClientGun iClientGun)
            iClientGun.setVanillaInteract(v);
    }

    public boolean isVanillaInteract() {
        if (getGunIndex() instanceof IClientGun iClientGun)
            return iClientGun.isVanillaInteract();
        return false;
    }

    public IClientPlayerGunOperator getGunOperator() {
        return IClientPlayerGunOperator.fromLocalPlayer(this.getPlayer());
    }

    private ObjectAnimation.PlayType getPlayType(TaCZJSUtils.AnimationPlayType type) {
        return switch (type) {
            case PLAY_ONCE_HOLD -> ObjectAnimation.PlayType.PLAY_ONCE_HOLD;
            case PLAY_ONCE_STOP -> ObjectAnimation.PlayType.PLAY_ONCE_STOP;
            case LOOP -> ObjectAnimation.PlayType.LOOP;
        };
    }

    public BlockHitResult getBlockHitResult() {
        var hitResult = Minecraft.getInstance().hitResult;
        if (hitResult instanceof BlockHitResult result) return result;
        return null;
    }

    public EntityHitResult getEntityHitResult() {
        var hitResult = Minecraft.getInstance().hitResult;
        if (hitResult instanceof EntityHitResult result) return result;
        return null;
    }

    public boolean canInteractEntity() {
        var hitResult = Minecraft.getInstance().hitResult;
        if (hitResult instanceof EntityHitResult result)
            return InteractKeyConfigRead.canInteractEntity(result.getEntity());
        else if (hitResult instanceof BlockHitResult result)
            return InteractKeyConfigRead.canInteractBlock(this.getLevel().getBlockState(result.getBlockPos()));
        return false;
    }
}
