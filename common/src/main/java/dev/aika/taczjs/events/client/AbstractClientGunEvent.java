package dev.aika.taczjs.events.client;

import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.client.animation.statemachine.GunAnimationStateMachine;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.config.util.InteractKeyConfigRead;
import dev.aika.taczjs.interfaces.IClientGun;
import dev.latvian.mods.kubejs.client.ClientEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public abstract class AbstractClientGunEvent extends ClientEventJS {
    private Boolean cancelled = false;
    private final ResourceLocation gunId;
    private final ClientGunIndex gunIndex;

    AbstractClientGunEvent(ResourceLocation gunId, ClientGunIndex gunIndex) {
        this.gunId = gunId;
        this.gunIndex = gunIndex;
    }

    public ResourceLocation getGunId() {
        return gunId;
    }

    public ClientGunIndex getGunIndex() {
        return gunIndex;
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
        if (gunIndex instanceof IClientGun iClientGun)
            iClientGun.setVanillaInteract(v);
    }

    public boolean isVanillaInteract() {
        if (gunIndex instanceof IClientGun iClientGun)
            return iClientGun.isVanillaInteract();
        return false;
    }

    public IClientPlayerGunOperator getGunOperator() {
        return IClientPlayerGunOperator.fromLocalPlayer(this.getPlayer());
    }

    public enum AnimationPlayType {
        PLAY_ONCE_HOLD,
        PLAY_ONCE_STOP,
        LOOP;
    }

    private ObjectAnimation.PlayType getPlayType(AnimationPlayType type) {
        return switch (type) {
            case PLAY_ONCE_HOLD -> ObjectAnimation.PlayType.PLAY_ONCE_HOLD;
            case PLAY_ONCE_STOP -> ObjectAnimation.PlayType.PLAY_ONCE_STOP;
            case LOOP -> ObjectAnimation.PlayType.LOOP;
        };
    }

    public void runMovementAnimation(String animationName, AnimationPlayType type, float transitionTimeS) {
        var animationStateMachine = getGunIndex().getAnimationStateMachine();
        if (animationStateMachine == null) return;
        animationStateMachine.getController().runAnimation(
                GunAnimationStateMachine.MOVEMENT_TRACK,
                animationName,
                getPlayType(type),
                transitionTimeS);
    }

    public void runMaimAnimation(String animationName, AnimationPlayType type, float transitionTimeS) {
        var animationStateMachine = getGunIndex().getAnimationStateMachine();
        if (animationStateMachine == null) return;
        animationStateMachine.getController().runAnimation(
                GunAnimationStateMachine.MAIN_TRACK,
                animationName,
                getPlayType(type),
                transitionTimeS);
    }

//    public BlockHitResult getBlockHitResult() {
//        var hitResult = Minecraft.getInstance().hitResult;
//        if (hitResult instanceof BlockHitResult result) return result;
//        return null;
//    }
//
//    public EntityHitResult getEntityHitResult() {
//        var hitResult = Minecraft.getInstance().hitResult;
//        if (hitResult instanceof EntityHitResult result) return result;
//        return null;
//    }

    public boolean canInteractEntity() {
        var hitResult = Minecraft.getInstance().hitResult;
        if (hitResult instanceof EntityHitResult result)
            return InteractKeyConfigRead.canInteractEntity(result.getEntity());
        else if (hitResult instanceof BlockHitResult result)
            return InteractKeyConfigRead.canInteractBlock(this.getLevel().getBlockState(result.getBlockPos()));
        return false;
    }
}
