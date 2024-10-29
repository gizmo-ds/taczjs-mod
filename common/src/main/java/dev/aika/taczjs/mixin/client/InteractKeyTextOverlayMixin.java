package dev.aika.taczjs.mixin.client;

import com.tacz.guns.client.gui.overlay.InteractKeyTextOverlay;
import dev.aika.taczjs.helper.ModClientHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = InteractKeyTextOverlay.class, remap = false)
public abstract class InteractKeyTextOverlayMixin {
    @Inject(method = "renderText", at = @At("HEAD"), cancellable = true)
    private static void renderText(GuiGraphics graphics, int width, int height, Font font, CallbackInfo ci) {
        ModClientHelper.getClientGun(Minecraft.getInstance().player).ifPresent(gun -> {
            if (gun.isVanillaInteract()) ci.cancel();
        });
    }
}
