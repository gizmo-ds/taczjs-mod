package dev.aika.taczjs.forge.events.crafting.legacy;

import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.recipe.TableRecipe;
import dev.aika.taczjs.forge.events.crafting.AbstractRecipeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class RecipeLoadEvent extends AbstractRecipeEvent {
    private String json;
    private Boolean modified;

    public RecipeLoadEvent(ResourceLocation recipeId, String json) {
        super(recipeId);
        this.json = json;
        this.modified = false;
    }

    public void removeRecipe() {
        this.setRemove(true);
    }

    @Info("@deprecated deprecated\nThe returned data may not conform to standard JSON format.")
    public String getJson() {
        return this.json;
    }

    @Info("@deprecated deprecated\nGet the JSON data in standard format.")
    public String getStdJson() {
        return getJson();
    }

    public void setJson(String json) {
        this.json = json;
        this.modified = true;
    }

    public TableRecipe getTableRecipe() {
        return CommonAssetsManager.GSON.fromJson(this.getJson(), TableRecipe.class);
    }

    @HideFromJS
    public Boolean isModified() {
        return this.modified;
    }
}
