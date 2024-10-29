package dev.aika.taczjs.events.asset;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.data.attachment.AttachmentData;
import dev.aika.taczjs.events.AbstractLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class AttachmentDataLoadEvent extends AbstractLoadEvent {
    public AttachmentDataLoadEvent(ResourceLocation id, String json) {
        super(id, json);
    }

    public AttachmentData getAttachmentData() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), AttachmentData.class);
    }

    public void removeAttachmentData() {
        this.setRemove(true);
    }
}
