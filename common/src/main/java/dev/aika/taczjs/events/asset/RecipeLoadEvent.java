package dev.aika.taczjs.events.asset;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.data.recipe.TableRecipe;
import dev.aika.taczjs.events.AbstractLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class RecipeLoadEvent extends AbstractLoadEvent {
    public RecipeLoadEvent(ResourceLocation id, String json) {
        super(id, json);
    }

    public TableRecipe getTableRecipe() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), TableRecipe.class);
    }

    public void removeRecipe() {
        this.setRemove(true);
    }
}
