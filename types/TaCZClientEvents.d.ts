declare class TaCZClientEvents {
    static gunIndexLoad(event: ClientGunIndexLoadEvent);
    static playerAim(event: LocalPlayerAimEvent);
    static playerShoot(event: LocalPlayerShootEvent);
    static playerMelee(event: LocalPlayerMeleeEvent);
    static playerReload(event: LocalPlayerReloadEvent);
}

declare enum AnimationPlayType {
    PLAY_ONCE_HOLD,
    PLAY_ONCE_STOP,
    LOOP,
}

// com.tacz.guns.client.sound.SoundPlayManager
declare class SoundPlayManager {}

type ResourceLocation = any; // net.minecraft.resources.ResourceLocation
type ClientGunIndex = any; // com.tacz.guns.client.resource.index.ClientGunIndex
type IClientPlayerGunOperator = any; // com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator
type BlockHitResult = any; // net.minecraft.world.phys.BlockHitResult
type EntityHitResult = any; // net.minecraft.world.phys.EntityHitResult
type float = number;

interface AbstractClientGunEvent {
    getGunId(): ResourceLocation;
    getGunIndex(): ClientGunIndex;
    setVanillaInteract(v: boolean): void;
    isVanillaInteract(): boolean;
    getGunOperator(): IClientPlayerGunOperator;
    runMovementAnimation(animationName: string, type: AnimationPlayType, transitionTimeS: float);
    runMaimAnimation(animationName: string, type: AnimationPlayType, transitionTimeS: float);
    getBlockHitResult(): BlockHitResult;
    getEntityHitResult(): EntityHitResult;
    canInteractEntity(): boolean;
}

interface ClientGunIndexLoadEvent extends AbstractClientGunEvent {}
interface LocalPlayerAimEvent extends AbstractClientGunEvent {
    cancelAim(): void;
}
interface LocalPlayerShootEvent extends AbstractClientGunEvent {
    cancelShoot(): void;
}
interface LocalPlayerMeleeEvent extends AbstractClientGunEvent {
    cancelMelee(): void;
}
interface LocalPlayerReloadEvent extends AbstractClientGunEvent {
    cancelReload(): void;
}
