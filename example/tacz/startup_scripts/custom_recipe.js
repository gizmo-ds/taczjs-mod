/**
 * 旧版的配方修改, 新版本建议使用 KubeJS 的 `ServerEvents.recipes` 进行配方管理.
 * 当前配方修改方法可能会在未来的某个版本移除.
 */

// TaCZ 配方加载开始前触发
TaCZStartupEvents.recipeLoadBegin((event) => {
    /**
     * `event.addRecipe`与`event.putRecipe`的作用都是修改或添加一个配方
     * `event.addRecipe`是一个错误的命名
     */

    // 添加 p90 的配方
    event.putRecipe(
        new ResourceLocation("tacz:gun/p90"),
        JSON.stringify({
            materials: [{item: {item: "minecraft:oak_button"}, count: 3}],
            result: {type: "gun", id: "tacz:p90", count: 1},
        }),
    )

    // 移除所有配方
    // event.removeAllRecipes();
})

// TaCZ 配方加载过程, 每个配方都会触发一次事件
TaCZStartupEvents.recipeLoad((event) => {
    const id = event.getId().toString();
    // 移除 AA12 的配方
    if (id === "tacz:aa12") return event.removeRecipe();
    // 移除 762x54 的配方
    if (id === "tacz:762x54") return event.removeRecipe()
    // 修改 沙漠之鹰 的配方
    if (id === "tacz:deagle")
        return event.setJson(JSON.stringify({
            materials: [{item: {item: "minecraft:apple"}, count: 1}],
            result: {type: "gun", id: "tacz:deagle"},
        }));
});

// TaCZ 配方加载结束后触发
TaCZStartupEvents.recipeLoadEnd((event) => {
    /**
     * `event.addRecipe`与`event.putRecipe`的作用都是修改或添加一个配方
     * `event.addRecipe`是一个错误的命名
     */

    // 添加 762x54 的配方
    event.putRecipe(
        new ResourceLocation("tacz:ammo/762x54"),
        JSON.stringify({
            materials: [{item: {item: "minecraft:oak_button"}, count: 3}],
            result: {type: "ammo", id: "tacz:762x54", count: 60},
        }),
    );

    // 移除所有配方
    // event.removeAllRecipes();
});
