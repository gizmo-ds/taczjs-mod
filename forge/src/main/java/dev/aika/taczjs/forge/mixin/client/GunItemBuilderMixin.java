package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.builder.GunItemBuilder;
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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mixin(value = GunItemBuilder.class, remap = false)
public abstract class GunItemBuilderMixin {
    @Shadow
    private ResourceLocation gunId;

    @Inject(method = "build", at = @At("HEAD"), cancellable = true)
    public void build(CallbackInfoReturnable<ItemStack> cir) {
        StackWalker walker = StackWalker.getInstance();
        walker.walk(f -> f.skip(2).findFirst()).ifPresent(f -> {
            if (!f.getClassName().equals("com.tacz.guns.init.ModCreativeTabs")) return;
            if (TimelessAPI.getCommonGunIndex(this.gunId).isPresent()) return;
            // https://github.com/MCModderAnchor/TACZ/blob/bd41964da0a869808ce963be5004dcb1d0fa4d69/src/main/java/com/tacz/guns/init/ModCreativeTabs.java#L72
            var GUN_TYPE_MAP = Map.of(
                    "glock_17", "pistol",
                    "ai_awp", "sniper",
                    "ak47", "rifle",
                    "db_short", "shotgun",
                    "hk_mp5a5", "smg",
                    "rpg7", "rpg",
                    "m249", "mg"
            );
            var result = Optional.ofNullable(GUN_TYPE_MAP.get(this.gunId.getPath()))
                    .flatMap(type -> TimelessAPI.getAllCommonGunIndex().stream()
                            .filter(x -> Objects.equals(x.getValue().getType(), type))
                            .findFirst()
                    )
                    .map(first -> {
                        var itemStack = new ItemStack(ModItems.MODERN_KINETIC_GUN.get(), 1);
                        if (itemStack.getItem() instanceof IGun i) i.setGunId(itemStack, first.getKey());
                        return itemStack;
                    })
                    .orElse(ModItems.GUN_SMITH_TABLE.get().getDefaultInstance());
            cir.setReturnValue(result);
        });
    }
}
