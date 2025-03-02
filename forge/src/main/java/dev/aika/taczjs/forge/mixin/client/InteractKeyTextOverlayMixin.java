package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.client.gui.overlay.InteractKeyTextOverlay;
import dev.aika.taczjs.forge.TaCZJSUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(value = InteractKeyTextOverlay.class, remap = false)
public abstract class InteractKeyTextOverlayMixin {
    @Inject(method = "renderText", at = @At("HEAD"), cancellable = true)
    private static void renderText(GuiGraphics graphics, int width, int height, Font font, CallbackInfo ci) {
        TaCZJSUtils.getClientGun(Minecraft.getInstance().player).ifPresent(gun -> {
            if (gun.isVanillaInteract())
                ci.cancel();
        });
    }
}
