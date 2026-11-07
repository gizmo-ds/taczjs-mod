package dev.aika.taczjs.neoforge.mixin.client.v120;

import com.tacz.guns.client.gui.overlay.InteractKeyTextOverlay;
import dev.aika.taczjs.neoforge.TaCZJSUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InteractKeyTextOverlay.class)
public abstract class InteractKeyTextOverlayMixin {
    @Inject(remap = false, method = "renderText", at = @At("HEAD"), cancellable = true)
    private static void renderText(GuiGraphics graphics, int width, int height, Font font, String keyName, Component name, CallbackInfo ci) {
        TaCZJSUtils.getClientGun(Minecraft.getInstance().player).ifPresent(gun -> {
            if (gun.isVanillaInteract()) ci.cancel();
        });
    }
}
