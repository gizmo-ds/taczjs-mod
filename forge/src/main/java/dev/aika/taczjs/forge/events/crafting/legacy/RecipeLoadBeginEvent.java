package dev.aika.taczjs.forge.events.crafting.legacy;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class RecipeLoadBeginEvent extends EventJS {
    private Boolean removeAllRecipes;
    private final Map<ResourceLocation, JsonElement> putRecipes;

    public RecipeLoadBeginEvent() {
        this.removeAllRecipes = false;
        this.putRecipes = new HashMap<>();
    }

    public void removeAllRecipes() {
        this.removeAllRecipes = true;
    }

    @HideFromJS
    public boolean isRemoveAllRecipes() {
        return removeAllRecipes;
    }

    public void putRecipe(ResourceLocation id, String json) {
        this.putRecipes.put(id, GsonHelper.parse(json, true));
    }

    @Info("@deprecated This is an alias for `event.putRecipe`. Please use `event.putRecipe` instead.")
    public void addRecipe(ResourceLocation id, String json) {
        putRecipe(id, json);
    }

    @HideFromJS
    public Map<ResourceLocation, JsonElement> getPutRecipes() {
        return putRecipes;
    }
}
