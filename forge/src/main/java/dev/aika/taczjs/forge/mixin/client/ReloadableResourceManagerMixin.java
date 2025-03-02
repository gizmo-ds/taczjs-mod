package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.TimelessAPI;
import dev.aika.taczjs.forge.events.ModClientEvents;
import dev.aika.taczjs.forge.events.client.ClientGunIndexLoadEvent;
import dev.aika.taczjs.forge.interfaces.client.IClientGun;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Unit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@OnlyIn(Dist.CLIENT)
@Mixin(ReloadableResourceManager.class)
public abstract class ReloadableResourceManagerMixin {
    @Inject(method = "createReload", at = @At("RETURN"))
    private void onCreateReload(Executor backgroundExecutor, Executor gameExecutor, CompletableFuture<Unit> waitingFor, List<PackResources> resourcePacks, CallbackInfoReturnable<ReloadInstance> cir) {
        TimelessAPI.getAllCommonGunIndex().forEach(e -> {
            var gunId = e.getKey();
            if (TimelessAPI.getClientGunIndex(gunId).orElse(null) instanceof IClientGun iClientGun)
                iClientGun.setVanillaInteract(false);
            var event = new ClientGunIndexLoadEvent(gunId);
            ModClientEvents.GUN_INDEX_LOAD_REGISTER.post(event);
        });
    }
}
