declare class TaCZServerEvents {
    static entityShoot(event: LivingEntityShootEvent);
    static entityAim(event: LivingEntityAimEvent);
    static entityMelee(event: LivingEntityMeleeEvent);
    static entityReload(event: LivingEntityReloadEvent);
}

/** net.minecraft.world.entity.LivingEntity */
type LivingEntity = any;

interface AbstractShooterEvent {
    getEntity(): LivingEntity;
    getShooter(): LivingEntity;
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
