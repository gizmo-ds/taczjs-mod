package dev.aika.taczjs.forge.events.asset;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.tacz.guns.resource.CommonAssetsManager;
import dev.aika.taczjs.forge.events.AbstractIndexLoadEvent;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@SuppressWarnings("unused")
public class AttachmentTagsLoadEvent extends AbstractIndexLoadEvent {
    public AttachmentTagsLoadEvent(ResourceLocation resourceId, JsonElement json) {
        super(resourceId, json);
    }

    public String[] getAttachmentTags() {
        return getAttachmentTagsList().toArray(new String[0]);
    }

    @HideFromJS
    public List<String> getAttachmentTagsList() {
        return CommonAssetsManager.GSON.fromJson(this.getJson(), new TypeToken<>() {
        });
    }

    public void removeAttachmentTags() {
        this.setRemove(true);
    }
}
