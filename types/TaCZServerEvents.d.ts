declare class TaCZServerEvents {
    static entityShoot(event: LivingEntityShootEvent);
    static entityAim(event: LivingEntityAimEvent);
    static entityMelee(event: LivingEntityMeleeEvent);
    static entityReload(event: LivingEntityReloadEvent);
    static recipes(event: RecipeLoadBeginEvent);
}

/** net.minecraft.world.entity.LivingEntity */
type LivingEntity = any;
/** net.minecraft.resources.ResourceLocation */
type ResourceLocation = any;
/** net.minecraft.world.item.ItemStack */
type ItemStack = any;
// https://kubejs.com/wiki/tutorials/recipes#removing-recipes
type RecipeFilter = any;

interface AbstractShooterEvent {
    getEntity(): LivingEntity;
    getShooter(): LivingEntity;
    getGunId(): ResourceLocation;
    getGunItem(): ItemStack;
}

interface LivingEntityShootEvent extends AbstractShooterEvent {
    cancelShoot(): void;
}

interface LivingEntityAimEvent extends AbstractShooterEvent {
    cancelAim(): void;
}

interface LivingEntityMeleeEvent extends AbstractShooterEvent {
    cancelMelee(): void;
}

interface LivingEntityReloadEvent extends AbstractShooterEvent {
    cancelReload(): void;
}

interface RecipeLoadBeginEvent {
    remove(filter: RecipeFilter): void;
    addRecipe(id: ResourceLocation, json: string): void;
}
