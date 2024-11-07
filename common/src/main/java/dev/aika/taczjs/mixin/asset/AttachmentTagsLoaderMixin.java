package dev.aika.taczjs.mixin.asset;

import com.tacz.guns.resource.CommonAssetManager;
import com.tacz.guns.resource.loader.asset.AttachmentTagsLoader;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.events.ModStartupEvents;
import dev.aika.taczjs.events.asset.AttachmentTagsLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = AttachmentTagsLoader.class, remap = false)
public abstract class AttachmentTagsLoaderMixin {
    @Inject(method = "loadFromJsonString", at = @At("HEAD"), cancellable = true)
    private static void load(ResourceLocation id, String json, CallbackInfo ci) {
        var event = new AttachmentTagsLoadEvent(id, json);
        ModStartupEvents.ATTACHMENT_TAGS_LOAD_REGISTER.post(event);
        if (event.isRemove()) {
            ci.cancel();
            return;
        }
        var newJson = event.getJson();
        if (Objects.equals(newJson, "") || newJson.equals(json)) return;
        CommonAssetManager.INSTANCE.putAttachmentTags(id, event.getAttachmentTagsList());
        CommonGunPackNetwork.addData(DataType.ATTACHMENT_TAGS, id, newJson);
        ci.cancel();
    }
}
