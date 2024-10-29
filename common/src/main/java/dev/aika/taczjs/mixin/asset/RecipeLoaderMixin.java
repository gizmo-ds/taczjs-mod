package dev.aika.taczjs.mixin.asset;

import com.tacz.guns.crafting.GunSmithTableRecipe;
import com.tacz.guns.resource.CommonAssetManager;
import com.tacz.guns.resource.loader.asset.RecipeLoader;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.events.JSEvents;
import dev.aika.taczjs.events.asset.RecipeLoadBeginEvent;
import dev.aika.taczjs.events.asset.RecipeLoadEndEvent;
import dev.aika.taczjs.events.asset.RecipeLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.Objects;
import java.util.zip.ZipFile;

@Mixin(value = RecipeLoader.class, remap = false)
public abstract class RecipeLoaderMixin {
    @Inject(method = "loadFromJsonString", at = @At("HEAD"), cancellable = true)
    private static void load(ResourceLocation id, String json, CallbackInfo ci) {
        var event = new RecipeLoadEvent(id, json);
        JSEvents.RECIPE_LOAD_REGISTER.post(event);
        if (event.isRemove()) {
            ci.cancel();
            return;
        }
        var newJson = event.getJson();
        if (Objects.equals(newJson, "") || newJson.equals(json)) return;
        CommonAssetManager.INSTANCE.putRecipe(id, new GunSmithTableRecipe(id, event.getTableRecipe()));
        CommonGunPackNetwork.addData(DataType.RECIPES, id, newJson);
        ci.cancel();
    }

    @Inject(method = "load(Ljava/util/zip/ZipFile;Ljava/lang/String;)Z", at = @At("HEAD"))
    private static void loadHead(ZipFile zipFile, String zipPath, CallbackInfoReturnable<Boolean> cir) {
        JSEvents.RECIPE_LOAD_BEGIN_REGISTER.post(new RecipeLoadBeginEvent());
    }

    @Inject(method = "load(Ljava/io/File;)V", at = @At("HEAD"))
    private static void loadHead(File root, CallbackInfo ci) {
        JSEvents.RECIPE_LOAD_BEGIN_REGISTER.post(new RecipeLoadBeginEvent());
    }

    @Inject(method = "load(Ljava/util/zip/ZipFile;Ljava/lang/String;)Z", at = @At("RETURN"))
    private static void loadReturn(ZipFile zipFile, String zipPath, CallbackInfoReturnable<Boolean> cir) {
        JSEvents.RECIPE_LOAD_END_REGISTER.post(new RecipeLoadEndEvent());
    }

    @Inject(method = "load(Ljava/io/File;)V", at = @At("RETURN"))
    private static void loadReturn(File root, CallbackInfo ci) {
        JSEvents.RECIPE_LOAD_END_REGISTER.post(new RecipeLoadEndEvent());
    }
}
