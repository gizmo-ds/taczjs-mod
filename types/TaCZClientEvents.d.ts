import TaCZJSUtils from "./TaCZJSUtils"

declare class TaCZClientEvents {
    static gunIndexLoad(event: ClientGunIndexLoadEvent);
    static playerAim(event: LocalPlayerAimEvent);
    static playerShoot(event: LocalPlayerShootEvent);
    static playerMelee(event: LocalPlayerMeleeEvent);
    static playerReload(event: LocalPlayerReloadEvent);
}

/** net.minecraft.resources.ResourceLocation */
type ResourceLocation = any;
/** com.tacz.guns.client.resource.index.ClientGunIndex */
type ClientGunIndex = any;
/** com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator */
type IClientPlayerGunOperator = any;
/** net.minecraft.world.phys.BlockHitResult */
type BlockHitResult = any;
/** net.minecraft.world.phys.EntityHitResult */
type EntityHitResult = any;
type float = number;

interface AbstractClientGunEvent {
    getGunId(): ResourceLocation;
    getGunIndex(): ClientGunIndex;
    setVanillaInteract(v: boolean): void;
    isVanillaInteract(): boolean;
    getGunOperator(): IClientPlayerGunOperator;
    runMovementAnimation(animationName: string, type: TaCZJSUtils.AnimationPlayType, transitionTimeS: float);
    runMaimAnimation(animationName: string, type: TaCZJSUtils.AnimationPlayType, transitionTimeS: float);
    getBlockHitResult(): BlockHitResult;
    getEntityHitResult(): EntityHitResult;
    canInteractEntity(): boolean;
}

interface ClientGunIndexLoadEvent extends AbstractClientGunEvent {}
interface LocalPlayerAimEvent extends AbstractClientGunEvent {
    isAim(): boolean;
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
