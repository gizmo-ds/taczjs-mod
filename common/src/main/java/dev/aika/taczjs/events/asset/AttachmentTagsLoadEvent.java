package dev.aika.taczjs.events.asset;

import com.google.gson.reflect.TypeToken;
import com.tacz.guns.resource.CommonGunPackLoader;
import dev.aika.taczjs.events.AbstractLoadEvent;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@SuppressWarnings("unused")
public class AttachmentTagsLoadEvent extends AbstractLoadEvent {
    public AttachmentTagsLoadEvent(ResourceLocation id, String json) {
        super(id, json);
    }

    public String[] getAttachmentTags() {
        return getAttachmentTagsList().toArray(new String[0]);
    }

    @HideFromJS
    public List<String> getAttachmentTagsList() {
        return CommonGunPackLoader.GSON.fromJson(this.getJson(), new TypeToken<>() {
        });
    }

    public void removeAttachmentTags() {
        this.setRemove(true);
    }
}
