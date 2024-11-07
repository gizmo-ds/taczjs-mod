declare class TaCZJSUtils {
    static AnimationPlayType: typeof AnimationPlayType;
    static SoundPlayManager: SoundPlayManager;
}

enum AnimationPlayType {
    PLAY_ONCE_HOLD,
    PLAY_ONCE_STOP,
    LOOP
}

/** com.tacz.guns.client.sound.SoundPlayManager */
type SoundPlayManager = any;
