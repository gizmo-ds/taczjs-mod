const $PlayerReviveServer = Java.tryLoadClass(
    "team.creative.playerrevive.server.PlayerReviveServer"
);

function isBleeding(player) {
    return $PlayerReviveServer.getBleeding(player).isBleeding();
}

TaCZClientEvents.playerShoot((event) => {
    // 倒地后禁止射击
    if (isBleeding(event.getGunOperator())) {
        return event.cancelShoot();
    }
});

TaCZClientEvents.playerMelee((event) => {
    // 倒地后禁用枪械近战
    if (isBleeding(event.getGunOperator())) {
        return event.cancelMelee();
    }
});
