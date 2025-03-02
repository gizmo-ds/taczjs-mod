package dev.aika.taczjs.forge;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.gui.GunRefitScreen;
import com.tacz.guns.crafting.GunSmithTableRecipe;
import com.tacz.guns.resource.index.CommonAmmoIndex;
import com.tacz.guns.resource.index.CommonAttachmentIndex;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.util.InputExtraCheck;
import dev.aika.taczjs.forge.interfaces.client.IClientGun;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("unused")
public class TaCZJSUtils {
    @OnlyIn(Dist.CLIENT)
    public enum AnimationPlayType {
        PLAY_ONCE_HOLD,
        PLAY_ONCE_STOP,
        LOOP;

        @HideFromJS
        public ObjectAnimation.PlayType getPlayType() {
            return switch (this) {
                case PLAY_ONCE_HOLD -> ObjectAnimation.PlayType.PLAY_ONCE_HOLD;
                case PLAY_ONCE_STOP -> ObjectAnimation.PlayType.PLAY_ONCE_STOP;
                case LOOP -> ObjectAnimation.PlayType.LOOP;
            };
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class SoundPlayManager extends com.tacz.guns.client.sound.SoundPlayManager {
    }

    @OnlyIn(Dist.CLIENT)
    public static void openRefitScreen() {
        if (!InputExtraCheck.isInGame()) return;
        var player = Minecraft.getInstance().player;
        if (player == null || player.isSpectator()) return;
        if (mainHandHoldGun(player)) {
            Minecraft.getInstance().setScreen(Minecraft.getInstance().screen == null ? new GunRefitScreen() : null);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @HideFromJS
    public static Optional<IClientGun> getClientGun(LocalPlayer player) {
        if (player == null || player.isSpectator()) return Optional.empty();
        var mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IGun iGun) {
            var gunId = iGun.getGunId(mainHandItem);
            return getClientGun(gunId);
        }
        return Optional.empty();
    }

    @OnlyIn(Dist.CLIENT)
    @HideFromJS
    public static Optional<IClientGun> getClientGun(ResourceLocation gunId) {
        var gunIndex = TimelessAPI.getClientGunIndex(gunId).orElse(null);
        if (gunIndex instanceof IClientGun) return Optional.of((IClientGun) gunIndex);
        return Optional.empty();
    }

    public static boolean mainHandHoldGun(LivingEntity livingEntity) {
        return IGun.mainhandHoldGun(livingEntity);
    }

    public static CommonGunIndex getGunIndex(ResourceLocation gunId) {
        return TimelessAPI.getCommonGunIndex(gunId).orElse(null);
    }

    public static CommonAmmoIndex getAmmoIndex(ResourceLocation ammoId) {
        return TimelessAPI.getCommonAmmoIndex(ammoId).orElse(null);
    }

    public static CommonAttachmentIndex getAttachmentIndex(ResourceLocation attachmentId) {
        return TimelessAPI.getCommonAttachmentIndex(attachmentId).orElse(null);
    }
}
