package dev.aika.taczjs.forge.events.asset;

import com.google.gson.JsonElement;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.attachment.AttachmentData;
import dev.aika.taczjs.forge.events.AbstractAssetLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class AttachmentDataLoadEvent extends AbstractAssetLoadEvent {
    public AttachmentDataLoadEvent(ResourceLocation id, JsonElement json) {
        super(id, json);
    }

    public AttachmentData getAttachmentData() {
        return CommonAssetsManager.GSON.fromJson(this.getJson(), AttachmentData.class);
    }

    public void removeAttachmentData() {
        this.setRemove(true);
    }
}
