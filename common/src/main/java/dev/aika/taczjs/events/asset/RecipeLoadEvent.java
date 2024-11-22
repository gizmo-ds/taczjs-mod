package dev.aika.taczjs.events.asset;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.data.recipe.TableRecipe;
import dev.aika.taczjs.TaCZJS;
import dev.aika.taczjs.events.AbstractLoadEvent;
import dev.latvian.mods.kubejs.recipe.filter.ConstantFilter;
import dev.latvian.mods.kubejs.recipe.filter.IDFilter;
import dev.latvian.mods.kubejs.recipe.filter.OrFilter;
import dev.latvian.mods.kubejs.recipe.filter.RecipeFilter;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class RecipeLoadEvent extends AbstractLoadEvent {
    public RecipeLoadEvent(ResourceLocation id, String json) {
        super(id, json);

        this.setRemove(TaCZJS.RecipeFilter.stream().anyMatch(f -> testRecipe(f, id)));
    }

    @HideFromJS
    private boolean testRecipe(RecipeFilter filter, ResourceLocation id) {
        if (filter instanceof ConstantFilter) return true;
        if (filter instanceof IDFilter idFilter) {
            return idFilter.id.equals(id);
        } else if (filter instanceof OrFilter orFilter) {
            return orFilter.list.stream().anyMatch(f -> testRecipe(f, id));
        }
        return false;
    }

    public TableRecipe getTableRecipe() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), TableRecipe.class);
    }

    public void removeRecipe() {
        this.setRemove(true);
    }
}
