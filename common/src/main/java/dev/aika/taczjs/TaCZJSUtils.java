package dev.aika.taczjs;

import com.tacz.guns.api.client.animation.ObjectAnimation;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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
}
