package dev.aika.taczjs.events.index;

import com.tacz.guns.resource.CommonGunPackLoader;
import com.tacz.guns.resource.pojo.AttachmentIndexPOJO;
import dev.aika.taczjs.events.AbstractLoadEvent;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unused")
public class AttachmentIndexLoadEvent extends AbstractLoadEvent {
    public AttachmentIndexLoadEvent(ResourceLocation id, String json) {
        super(id, json);
    }

    public AttachmentIndexPOJO getPOJO() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), AttachmentIndexPOJO.class);
    }

    public void removeAttachment() {
        this.setRemove(true);
    }
}
