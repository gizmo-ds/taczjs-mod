package dev.aika.taczjs;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.crafting.GunSmithTableRecipe;
import com.tacz.guns.resource.index.CommonAmmoIndex;
import com.tacz.guns.resource.index.CommonAttachmentIndex;
import com.tacz.guns.resource.index.CommonGunIndex;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class TaCZJSUtils {
    @Environment(EnvType.CLIENT)
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

    @Environment(EnvType.CLIENT)
    public static class SoundPlayManager extends com.tacz.guns.client.sound.SoundPlayManager {
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

    public static GunSmithTableRecipe getRecipe(ResourceLocation recipeId) {
        return TimelessAPI.getRecipe(recipeId).orElse(null);
    }
}
