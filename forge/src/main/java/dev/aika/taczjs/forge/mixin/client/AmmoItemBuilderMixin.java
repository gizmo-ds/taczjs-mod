package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import com.tacz.guns.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mixin(value = AmmoItemBuilder.class, remap = false)
public abstract class AmmoItemBuilderMixin {
    @Shadow
    private ResourceLocation ammoId;

    @Inject(method = "build", at = @At("HEAD"), cancellable = true)
    private void build(CallbackInfoReturnable<ItemStack> cir) {
        StackWalker walker = StackWalker.getInstance();
        walker.walk(f -> f.skip(2).findFirst()).ifPresent(f -> {
            if (!f.getClassName().equals("com.tacz.guns.init.ModCreativeTabs")) return;
            if (TimelessAPI.getCommonAmmoIndex(this.ammoId).isPresent()) return;
            var result = Optional.of(DefaultAssets.DEFAULT_AMMO_ID.getPath())
                    .flatMap(type -> TimelessAPI.getAllCommonAmmoIndex().stream().findFirst())
                    .map(first -> {
                        var itemStack = new ItemStack(ModItems.AMMO.get(), 1);
                        if (itemStack.getItem() instanceof IAmmo i) i.setAmmoId(itemStack, first.getKey());
                        return itemStack;
                    })
                    .orElse(ModItems.GUN_SMITH_TABLE.get().getDefaultInstance());
            cir.setReturnValue(result);
        });
    }
}
