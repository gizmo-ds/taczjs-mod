package dev.aika.taczjs.fabric.mixin.client;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import com.tacz.guns.init.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Environment(EnvType.CLIENT)
@Mixin(AmmoItemBuilder.class)
public abstract class AmmoItemBuilderMixin {
    @Shadow
    private ResourceLocation ammoId;

    @Inject(remap = false, method = "build", at = @At("HEAD"), cancellable = true)
    private void build(CallbackInfoReturnable<ItemStack> cir) {
        StackWalker walker = StackWalker.getInstance();
        walker.walk(f -> f.skip(2).findFirst()).ifPresent(f -> {
            if (!f.getClassName().equals("com.tacz.guns.init.ModCreativeTabs")) return;
            if (TimelessAPI.getCommonAmmoIndex(this.ammoId).isPresent()) return;
            var result = Optional.of(DefaultAssets.DEFAULT_AMMO_ID.getPath())
                    .flatMap(type -> TimelessAPI.getAllCommonAmmoIndex().stream().findFirst())
                    .map(first -> {
                        var itemStack = new ItemStack(ModItems.AMMO, 1);
                        if (itemStack.getItem() instanceof IAmmo i) i.setAmmoId(itemStack, first.getKey());
                        return itemStack;
                    })
                    .orElse(ModItems.GUN_SMITH_TABLE.getDefaultInstance());
            cir.setReturnValue(result);
        });
    }
}
