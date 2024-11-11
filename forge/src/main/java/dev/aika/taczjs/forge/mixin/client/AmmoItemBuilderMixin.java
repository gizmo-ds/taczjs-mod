package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.resource.CommonGunPackLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(value = AmmoItemBuilder.class, remap = false)
public abstract class AmmoItemBuilderMixin {
    @Shadow
    private ResourceLocation ammoId;

    @Inject(method = "build", at = @At("HEAD"), cancellable = true)
    public void build(CallbackInfoReturnable<ItemStack> cir) {
        var st = Thread.currentThread().getStackTrace();
        if (st.length >= 4 && st[3].getClassName().equals("com.tacz.guns.init.ModCreativeTabs")) {
            if (TimelessAPI.getCommonAmmoIndex(this.ammoId).isEmpty()) {
                var firstAmmo = CommonGunPackLoader.getAllAmmo().stream().findFirst().orElse(null);
                if (firstAmmo == null) {
                    cir.setReturnValue(ModItems.GUN_SMITH_TABLE.get().getDefaultInstance());
                    return;
                }
                var ammo = new ItemStack(ModItems.AMMO.get(), 1);
                if (ammo.getItem() instanceof IAmmo iAmmo) {
                    iAmmo.setAmmoId(ammo, firstAmmo.getKey());
                    cir.setReturnValue(ammo);
                }
            }
        }
    }
}
