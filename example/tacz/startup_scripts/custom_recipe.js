// 定义需要添加的配方
const recipes = {
    "tacz:ammo/762x54": JSON.stringify({
        materials: [{ item: { item: "minecraft:apple" }, count: 1 }],
        result: { type: "ammo", id: "tacz:762x54", count: 60 },
    }),
};

TaCZJSEvents.onRecipeLoad((event) => {
    // 移除任何配方
    return event.removeRecipe();
});

// 在 TaCZ 的配方加载流程结束后
TaCZJSEvents.onRecipeLoadEnd((event) => {
    // 添加新的配方
    for (const [key, value] of Object.entries(recipes)) {
        event.addRecipe(new ResourceLocation(key), value);
    }
});