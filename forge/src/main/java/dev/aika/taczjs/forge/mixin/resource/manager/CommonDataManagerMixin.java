package dev.aika.taczjs.forge.mixin.resource.manager;

import com.google.gson.JsonElement;
import com.tacz.guns.resource.manager.CommonDataManager;
import com.tacz.guns.resource.network.DataType;
import dev.aika.taczjs.TaCZJS;
import dev.aika.taczjs.forge.TaCZJSHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(value = CommonDataManager.class, remap = false)
public abstract class CommonDataManagerMixin {
    @Shadow
    @Final
    private DataType type;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    private void onApply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        TaCZJS.LOGGER.info("CommonDataManager::onApply: {}", this.type);
        ArrayList<ResourceLocation> removes = new ArrayList<>();
        Map<ResourceLocation, String> modified = new HashMap<>();
        if (EnumSet.of(
                DataType.GUN_INDEX, DataType.AMMO_INDEX, DataType.ATTACHMENT_INDEX,
                DataType.GUN_DATA, DataType.ATTACHMENT_DATA,
                DataType.ATTACHMENT_TAGS, DataType.ALLOW_ATTACHMENT_TAGS
        ).contains(this.type)) {
            for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
                var event = TaCZJSHelper.getLoadEventHandler(this.type, entry.getKey(), entry.getValue());
                if (event == null) return;
                if (event.isRemove()) removes.add(event.getId());
                else if (event.isModified()) modified.put(event.getId(), event.getJson());
            }
        }
        if (!removes.isEmpty()) removes.forEach(pObject::remove);
        if (!modified.isEmpty()) modified.forEach((key, value) -> pObject.put(key, GsonHelper.parse(value)));
    }
}
