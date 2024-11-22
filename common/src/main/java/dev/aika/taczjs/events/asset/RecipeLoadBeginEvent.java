package dev.aika.taczjs.events.asset;

import dev.aika.taczjs.TaCZJS;
import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;
import dev.latvian.mods.rhino.util.HideFromJS;

@SuppressWarnings("unused")
public class RecipeLoadBeginEvent extends RecipeLoadEndEvent {
    public RecipeLoadBeginEvent() {
        super();

        TaCZJS.RecipeFilter.clear();
    }

    public void remove(RecipeFilter filter) {
        TaCZJS.RecipeFilter.add(filter);
    }

    @HideFromJS
    public void removeAllRecipes() {
    }
}
