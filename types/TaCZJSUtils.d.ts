declare class TaCZJSUtils {
    /** client only */
    static AnimationPlayType: typeof AnimationPlayType;
    /** client only */
    static SoundPlayManager: SoundPlayManager;
    /** client only */
    static openRefitScreen(): void;
    static mainHandHoldGun(livingEntity: LivingEntity): boolean;
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
/** net.minecraft.world.entity.LivingEntity */
type LivingEntity = any;
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
