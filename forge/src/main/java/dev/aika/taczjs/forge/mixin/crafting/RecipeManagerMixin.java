package dev.aika.taczjs.forge.mixin.crafting;

import com.google.gson.JsonElement;
import dev.aika.taczjs.forge.TaCZJSHelper;
import dev.aika.taczjs.forge.events.ModStartupEvents;
import dev.aika.taczjs.forge.events.crafting.legacy.RecipeLoadBeginEvent;
import dev.aika.taczjs.forge.events.crafting.legacy.RecipeLoadEndEvent;
import dev.aika.taczjs.forge.events.crafting.legacy.RecipeLoadEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    /**
     * 兼容旧版 JS 的修改配方, 现在更加推荐使用 KubeJS 的 `ServerEvents.recipes` 进行配方管理. 可能会在未来的版本移除
     */
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void onApply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        ArrayList<ResourceLocation> removes = new ArrayList<>();
        Map<ResourceLocation, JsonElement> modified = new HashMap<>();

        // RecipeLoadBeginEvent
        {
            var event = new RecipeLoadBeginEvent();
            ModStartupEvents.RECIPE_LOAD_BEGIN_REGISTER.post(event);
            if (event.isRemoveAllRecipes()) object.clear();
            var addRecipes = event.getPutRecipes();
            if (!addRecipes.isEmpty()) object.putAll(addRecipes);
        }

        // RecipeLoadEvent
        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            var key = entry.getKey();
            if (key.getPath().startsWith("_")) continue;
            var recipeType = GsonHelper.getAsString(GsonHelper.convertToJsonObject(entry.getValue(), "top element"), "type");
            if (!recipeType.equals(TaCZJSHelper.GunSmithTableRecipeType)) continue;
            var json = GsonHelper.toStableString(entry.getValue());
            var event = new RecipeLoadEvent(key, json);
            ModStartupEvents.RECIPE_LOAD_REGISTER.post(event);
            if (event.isRemove()) removes.add(key);
            if (event.isModified()) modified.put(key, event.getJsonElement());
        }

        removes.forEach(object::remove);
        object.putAll(modified);

        // RecipeLoadEndEvent
        {
            var event = new RecipeLoadEndEvent();
            ModStartupEvents.RECIPE_LOAD_END_REGISTER.post(event);
            if (event.isRemoveAllRecipes()) object.clear();
            var addRecipes = event.getPutRecipes();
            if (!addRecipes.isEmpty()) object.putAll(addRecipes);
        }
    }
}
