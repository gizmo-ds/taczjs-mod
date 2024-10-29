// 这是一个更改"机动军械师"中的近战武器操作方式的例子
// "机动军械师"的下载地址: https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero

const MeleeWeapons = [
    "create_armorer:special_melee_wrench",
    "create_armorer:special_melee_atomic",
];

TaCZJSEvents.onGunIndexLoad((event) => {
    if (MeleeWeapons.includes(event.getGunId().toString())) {
        // 为近战武器添加原版交互设置
        event.setVanillaInteract(true);
    }
});

TaCZJSEvents.onAim((event) => {
    if (MeleeWeapons.includes(event.getGunId().toString())) {
        // 只处理开始瞄准的事件
        if (!event.isAim()) return;

        // 如果是原版交互并且可以交互, 不播放动画
        if (event.isVanillaInteract() && event.canInteractEntity())
            return event.cancelAim();

        event.cancelAim();
        const gunIndex = event.getGunIndex();
        const am = gunIndex.getAnimationStateMachine();
        if (am === null) return;
        if (am.isPlayingRunIntroOrLoop()) {
            // 如果正在跑步, 播放 run_start 动画
            event.runMovementAnimation(
                "run_start",
                AnimationPlayType.PLAY_ONCE_HOLD,
                0.5
            );
        } else if (am.isPlayingIdleAnimation() || am.isPlayingWalkAnimation()) {
            // 如果正在站立或者走路, 播放武器检视动画
            SoundPlayManager.stopPlayGunSound();
            SoundPlayManager.playInspectSound(event.getPlayer(), gunIndex, false);
            am.onGunInspect();
        }
    }
});

TaCZJSEvents.onShoot((event) => {
    if (MeleeWeapons.includes(event.getGunId().toString())) {
        // 如果近战武器进行射击, 取消射击并执行近战
        event.cancelShoot();
        event.getGunOperator().melee();
    }
});
