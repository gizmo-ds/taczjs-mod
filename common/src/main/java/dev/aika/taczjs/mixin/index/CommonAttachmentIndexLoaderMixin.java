package dev.aika.taczjs.mixin.index;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.index.CommonAttachmentIndex;
import com.tacz.guns.resource.loader.index.CommonAttachmentIndexLoader;
import com.tacz.guns.resource.network.CommonGunPackNetwork;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.events.index.AttachmentIndexLoadEvent;
import dev.aika.taczjs.events.JSEvents;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CommonAttachmentIndexLoader.class, remap = false)
public abstract class CommonAttachmentIndexLoaderMixin {
    @Inject(method = "loadAttachmentFromJsonString", at = @At("HEAD"), cancellable = true)
    private static void load(ResourceLocation id, String json, CallbackInfo ci) {
        var event = new AttachmentIndexLoadEvent(id, json);
        JSEvents.ATTACHMENT_INDEX_LOAD_REGISTER.post(event);
        if (event.isRemove()) {
            ci.cancel();
            return;
        }
        var newJson = event.getJson();
        if (newJson.equals(json)) return;
        CommonGunPackLoader.ATTACHMENT_INDEX.put(event.getId(), CommonAttachmentIndex.getInstance(event.getId(), event.getPOJO()));
        CommonGunPackNetwork.addData(DataType.ATTACHMENT_INDEX, event.getId(), event.getJson());
        ci.cancel();
    }
}
