package dev.aika.taczjs.events.asset;

import com.tacz.guns.crafting.GunSmithTableRecipe;
import com.tacz.guns.resource.CommonAssetManager;
import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.data.recipe.TableRecipe;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class RecipeLoadEndEvent extends EventJS {
    public void removeAllRecipes() {
        CommonAssetManager.INSTANCE.clearRecipes();
    }

    public void addRecipe(ResourceLocation id, String json) {
        var tableRecipe = CommonGunPackLoader.GSON.fromJson(json, TableRecipe.class);
        CommonAssetManager.INSTANCE.putRecipe(id, new GunSmithTableRecipe(id, tableRecipe));
    }
}
