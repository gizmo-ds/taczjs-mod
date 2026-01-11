package dev.aika.taczjs.neoforge.events.crafting.legacy;

import com.google.gson.JsonElement;
import dev.aika.taczjs.neoforge.TaCZJSHelper;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class RecipeLoadBeginEvent implements KubeEvent {
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
        this.putRecipes.put(id, TaCZJSHelper.toJsonObject(json));
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
