declare class TaCZStartupEvents {
    static recipeLoad(event: RecipeLoadEvent);
    static recipeLoadBegin(event: RecipeLoadBeginEvent);
    static recipeLoadEnd(event: RecipeLoadEndEvent);

    static gunDataLoad(event: GunDataLoadEvent);
    static attachmentDataLoad(event: AttachmentDataLoadEvent);
    static attachmentTagsLoad(event: AttachmentTagsLoadEvent);

    static gunIndexLoad(event: GunIndexLoadEvent);
    static ammoIndexLoad(event: AmmoIndexLoadEvent);
    static attachmentIndexLoad(event: AttachmentIndexLoadEvent);
}

/** net.minecraft.resources.ResourceLocation */
type ResourceLocation = any;
/** com.tacz.guns.resource.pojo.data.recipe.TableRecipe */
type TableRecipe = any;
/** com.tacz.guns.resource.pojo.data.gun.GunData */
type GunData = any;
/** com.tacz.guns.resource.pojo.data.attachment.AttachmentData */
type AttachmentData = any;
/** com.tacz.guns.resource.pojo.GunIndexPOJO */
type GunIndexPOJO = any;
/** com.tacz.guns.resource.pojo.AmmoIndexPOJO */
type AmmoIndexPOJO = any;
/** com.tacz.guns.resource.pojo.AttachmentIndexPOJO */
type AttachmentIndexPOJO = any;

interface AbstractLoadEvent {
    getId(): ResourceLocation;
    getJson(): string;
    getStdJson(): string;
    setJson(json: string): void;
}
interface RecipeLoadEvent extends AbstractLoadEvent {
    getTableRecipe(): TableRecipe;
    removeRecipe(): void;
}
interface RecipeLoadEndEvent {
    removeAllRecipes(): void;
    addRecipe(id: ResourceLocation, json: string): void;
}
interface RecipeLoadBeginEvent extends RecipeLoadEndEvent {}
interface GunDataLoadEvent extends AbstractLoadEvent {
    getGunData(): GunData;
    removeGunData(): void;
}
interface AttachmentDataLoadEvent extends AbstractLoadEvent {
    getAttachmentData(): AttachmentData;
    removeAttachmentData(): void;
}
interface AttachmentTagsLoadEvent extends AbstractLoadEvent {
    getAttachmentTags(): string[];
    removeAttachmentTags(): void;
}
interface GunIndexLoadEvent extends AbstractLoadEvent {
    getPOJO(): GunIndexPOJO;
    removeGun(): void;
}
interface AmmoIndexLoadEvent extends AbstractLoadEvent {
    getPOJO(): AmmoIndexPOJO;
    removeAmmo(): void;
}
interface AttachmentIndexLoadEvent extends AbstractLoadEvent {
    getPOJO(): AttachmentIndexPOJO;
    removeAttachment(): void;
}
