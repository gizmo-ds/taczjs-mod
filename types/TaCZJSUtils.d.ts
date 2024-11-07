declare class TaCZJSUtils {
    static AnimationPlayType: typeof AnimationPlayType;
    static SoundPlayManager: SoundPlayManager;
    static getGunIndex(gunId: ResourceLocation): CommonGunIndex;
    static getAmmoIndex(ammoId: ResourceLocation): CommonAmmoIndex;
    static getAttachmentIndex(attachmentId: ResourceLocation): CommonAttachmentIndex;
    static getRecipe(recipeId: ResourceLocation): GunSmithTableRecipe;
}

enum AnimationPlayType {
    PLAY_ONCE_HOLD,
    PLAY_ONCE_STOP,
    LOOP
}

/** net.minecraft.resources.ResourceLocation */
type ResourceLocation = any;
/** com.tacz.guns.client.sound.SoundPlayManager */
type SoundPlayManager = any;
/** com.tacz.guns.resource.index.CommonGunIndex */
type CommonGunIndex = any;
/** com.tacz.guns.resource.index.CommonAmmoIndex */
type CommonAmmoIndex = any;
/** com.tacz.guns.resource.index.CommonAttachmentIndex */
type CommonAttachmentIndex = any;
/** com.tacz.guns.resource.index.GunSmithTableRecipe */
type GunSmithTableRecipe = any;
