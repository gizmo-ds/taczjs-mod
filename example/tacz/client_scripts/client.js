TaCZClientEvents.gunIndexLoad((event) => {
    const gunId = event.getGunId().toString();
    // RPG-7火箭筒 使用原版交互
    if (gunId === "tacz:rpg7") {
        event.setVanillaInteract(true);
    }
})

TaCZClientEvents.playerAim((event) => {
    const gunId = event.getGunId().toString();
    // 禁止 RPG-7火箭筒 进行瞄准
    if (gunId === "tacz:rpg7") {
        return event.cancelAim()
    }
})

TaCZClientEvents.playerShoot((event) => {
    const gunId = event.getGunId().toString();
    // 禁止 RPG-7火箭筒 进行射击
    if (gunId === "tacz:rpg7") {
        return event.cancelShoot()
    }
})

TaCZClientEvents.playerMelee((event) => {
    const gunId = event.getGunId().toString();
    // 禁止 RPG-7火箭筒 进行近战
    if (gunId === "tacz:rpg7") {
        return event.cancelMelee()
    }
})

TaCZClientEvents.playerReload((event) => {
    const gunId = event.getGunId().toString();
    // 禁止 RPG-7火箭筒 进行换弹
    if (gunId === "tacz:rpg7") {
        return event.cancelReload()
    }
})
