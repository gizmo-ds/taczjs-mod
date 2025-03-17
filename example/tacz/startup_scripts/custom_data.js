TaCZStartupEvents.gunDataLoad((event) => {
    const id = event.getId().toString();
    // 修改 p90 的弹药数量为 123
    if (id === "tacz:p90_data") {
        const json = JSON.parse(event.getStdJson());
        json.ammo_amount = 123;
        return event.setJson(JSON.stringify(json));
    }
    // 修改 黄金沙漠之鹰 的伤害为 999
    if (id === "tacz:deagle_golden_data") {
        const json = JSON.parse(event.getStdJson());
        json.bullet.extra_damage.damage_adjust = [
            { distance: 18, damage: 999 },
            { distance: 36, damage: 999 },
            { distance: 55, damage: 999 },
            { distance: "infinite", damage: 999 },
        ];
        return event.setJson(JSON.stringify(json));
    }
})

TaCZStartupEvents.attachmentDataLoad((event) => {
    const id = event.getId().toString();
    // 修改 克苏鲁K7制退器, 装备后会拥有 10 倍的垂直后坐力👍
    if (id === "tacz:muzzle_brake_cthulhu_data") {
        const json = JSON.parse(event.getStdJson());
        json.recoil.pitch = {multiplier: 10}
        return event.setJson(JSON.stringify(json));
    }
})
