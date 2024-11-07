package dev.aika.taczjs.mixin.asset;

import com.tacz.guns.resource.CommonAssetManager;
import com.tacz.guns.resource.loader.asset.AttachmentDataLoader;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.events.ModStartupEvents;
import dev.aika.taczjs.events.asset.AttachmentDataLoadEvent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = AttachmentDataLoader.class, remap = false)
public abstract class AttachmentDataLoaderMixin {
    @Inject(method = "loadFromJsonString", at = @At("HEAD"), cancellable = true)
    private static void load(ResourceLocation id, String json, CallbackInfo ci) {
        var event = new AttachmentDataLoadEvent(id, json);
        ModStartupEvents.ATTACHMENT_DATA_LOAD_REGISTER.post(event);
        if (event.isRemove()) {
            ci.cancel();
            return;
        }
        var newJson = event.getJson();
        if (Objects.equals(newJson, "") || newJson.equals(json)) return;
        CommonAssetManager.INSTANCE.putAttachmentData(id, event.getAttachmentData());
        CommonGunPackNetwork.addData(DataType.ATTACHMENT_DATA, id, newJson);
        ci.cancel();
    }
}
