TaCZStartupEvents.gunDataLoad((event) => {
    const id = event.getId().toString();
    // 修改 p90 的弹药数量为 123
    if (id === "tacz:p90_data") {
        const json = JSON.parse(event.getStdJson());
        json.ammo_amount = 123;
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
