package dev.aika.taczjs.fabric.mixin.client;

import com.tacz.guns.api.client.event.InputEvent;
import com.tacz.guns.client.event.ClientPreventGunClick;
import dev.aika.taczjs.helper.ModClientHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = ClientPreventGunClick.class, remap = false)
public abstract class ClientPreventGunClickMixin {
    @Inject(method = "onClickInput", at = @At("HEAD"), cancellable = true)
    private static void onClickInput(InputEvent.InteractionKeyMappingTriggered event, CallbackInfo ci) {
        var mc = Minecraft.getInstance();
        if (mc.options.keyAttack.isDown()) return;
        ModClientHelper.getClientGun(mc.player).ifPresent(gun -> {
            if (gun.isVanillaInteract()) ci.cancel();
        });
    }
}
