const $PlayerReviveServer = Java.tryLoadClass(
    "team.creative.playerrevive.server.PlayerReviveServer"
);

function isBleeding(player) {
    return $PlayerReviveServer.getBleeding(player).isBleeding();
}

TaCZServerEvents.entityShoot((event) => {
    // 倒地后禁止射击
    if (isBleeding(event.getShooter())) {
        return event.cancelShoot();
    }
});

TaCZServerEvents.entityMelee((event) => {
    // 倒地后禁用枪械近战
    if (isBleeding(event.getShooter())) {
        return event.cancelMelee();
    }
});
