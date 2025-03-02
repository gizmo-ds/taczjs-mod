// 实体使用武器进行瞄准的事件
TaCZServerEvents.entityAim(event => {
    const shooter = event.getShooter()
    const gunId = event.getGunId().toString();
    // 如果实体使用 RPG-7火箭筒 进行瞄准, 杀死实体
    if (gunId === "tacz:rpg7") {
        shooter.kill()
    }
})
