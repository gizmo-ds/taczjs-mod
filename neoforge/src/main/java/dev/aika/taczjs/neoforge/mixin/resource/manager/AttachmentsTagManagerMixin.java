package dev.aika.taczjs.neoforge.mixin.resource.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tacz.guns.resource.manager.AttachmentsTagManager;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.neoforge.TaCZJSHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(value = AttachmentsTagManager.class, remap = false)
public abstract class AttachmentsTagManagerMixin {
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void onApply(Map<ResourceLocation, List<JsonElement>> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        ArrayList<ResourceLocation> removes = new ArrayList<>();
        Map<ResourceLocation, List<JsonElement>> modified = new HashMap<>();
        for (Map.Entry<ResourceLocation, List<JsonElement>> entry : pObject.entrySet()) {
            // allow_attachments 有独立的 DataType，不属于 attachmentTagsLoad 的范畴
            if (entry.getKey().getPath().startsWith("allow_attachments/")) continue;
            List<JsonElement> roots = entry.getValue();
            if (roots == null || roots.isEmpty()) continue;
            JsonElement original = roots.getFirst();
            String originalJson = GsonHelper.toStableString(original);
            var event = TaCZJSHelper.getLoadEventHandler(DataType.ATTACHMENT_TAGS, entry.getKey(), original);
            if (event == null) continue;
            if (event.isRemove()) {
                removes.add(event.getId());
            } else if (!originalJson.equals(event.getJson())) {
                List<JsonElement> newRoots = new ArrayList<>(roots);
                newRoots.set(0, JsonParser.parseString(event.getJson()));
                modified.put(event.getId(), newRoots);
            }
        }
        if (!removes.isEmpty()) removes.forEach(pObject::remove);
        if (!modified.isEmpty()) pObject.putAll(modified);
    }
}