// 这是一个修改"机动军械师"中配方的例子, 这个例子中将"标准镜头"的配方修改为只需要9个苹果
// "机动军械师"的下载地址: https://www.curseforge.com/minecraft/mc-mods/timeless-and-classics-zero

TaCZStartupEvents.recipeLoad((event) => {
    if (event.getId().toString() === "create_armorer:attachments/sight_standard") {
        const json = JSON.parse(event.getJson());
        json.materials = [
            {
                item: { item: "minecraft:apple" },
                count: 9,
            },
        ];
        return event.setJson(JSON.stringify(json));
    }
});
