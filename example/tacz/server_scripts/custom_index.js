// 与 `TaCZStartupEvents.gunIndexLoad` 功能一致, 但优先级更高
TaCZServerEvents.gunIndexLoad((event) => {
    const id = event.getId().toString();
    // 修改 p90 的枪械类型为 `rifle`(步枪)
    if (id === "tacz:p90") {
        const json = JSON.parse(event.getStdJson());
        json.type = "rifle";
        return event.setJson(JSON.stringify(json));
    }

    // 删除 黄金沙漠之鹰
    if (id === "tacz:deagle_golden") {
        return event.removeGun();
    }
})

// 与 `TaCZStartupEvents.ammoIndexLoad` 功能一致, 但优先级更高
TaCZServerEvents.ammoIndexLoad((event) => {
    const id = event.getId().toString();
    // 修改 火箭弹 的堆叠数量为 33
    if (id === "tacz:rpg_rocket") {
        const json = JSON.parse(event.getStdJson());
        json.stack_size = 33;
        return event.setJson(JSON.stringify(json));
    }
})

// 与 `TaCZStartupEvents.attachmentIndexLoad` 功能一致, 但优先级更高
TaCZServerEvents.attachmentIndexLoad((event) => {
    const id = event.getId().toString();
    // 删除 狙击弹药扩容弹匣3
    if (id === "tacz:sniper_extended_mag_3") {
        return event.removeAttachment();
    }
})
