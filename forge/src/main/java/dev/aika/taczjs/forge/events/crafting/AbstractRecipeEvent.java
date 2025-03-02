package dev.aika.taczjs.forge.events.crafting;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public abstract class AbstractRecipeEvent extends EventJS {
    private final ResourceLocation id;
    private final ResourceLocation recipeId;
    private Boolean cancelled;

    public AbstractRecipeEvent(ResourceLocation recipeId) {
        this.recipeId = recipeId;
        this.id = toId(recipeId);
        this.cancelled = false;
    }

    @HideFromJS
    public static ResourceLocation toId(ResourceLocation recipeId) {
        var paths = recipeId.getPath().split("/");
        if (paths.length == 1) return recipeId;
        return new ResourceLocation(recipeId.getNamespace(), paths[paths.length - 1]);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public ResourceLocation getRecipeId() {
        return this.recipeId;
    }

    @HideFromJS
    public Boolean isRemove() {
        return this.cancelled;
    }

    @HideFromJS
    public void setRemove(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
