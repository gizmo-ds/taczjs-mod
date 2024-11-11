package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.builder.GunItemBuilder;
import com.tacz.guns.api.item.gun.GunItemManager;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.resource.CommonGunPackLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mixin(value = GunItemBuilder.class, remap = false)
public abstract class GunItemBuilderMixin {
    @Shadow
    private ResourceLocation gunId;

    @Inject(method = "build", at = @At("HEAD"), cancellable = true)
    public void build(CallbackInfoReturnable<ItemStack> cir) {
        var st = Thread.currentThread().getStackTrace();
        if (st.length >= 4) {
            if (st[3].getClassName().equals("com.tacz.guns.init.ModCreativeTabs") || st[3].getClassName().startsWith("com.github.tartaricacid.touhoulittlemaid")) {
                if (TimelessAPI.getCommonGunIndex(this.gunId).isEmpty()) {
                    switch (this.gunId.toString()) {
                        case "tacz:glock_17":
                            cir.setReturnValue(taczjs$creativeTabsGunItem("pistol"));
                            return;
                        case "tacz:ai_awp":
                            cir.setReturnValue(taczjs$creativeTabsGunItem("sniper"));
                            return;
                        case "tacz:ak47":
                            cir.setReturnValue(taczjs$creativeTabsGunItem("rifle"));
                            return;
                        case "tacz:db_short":
                            cir.setReturnValue(taczjs$creativeTabsGunItem("shotgun"));
                            return;
                        case "tacz:hk_mp5a5":
                            cir.setReturnValue(taczjs$creativeTabsGunItem("smg"));
                            return;
                        case "tacz:rpg7":
                            cir.setReturnValue(taczjs$creativeTabsGunItem("rpg"));
                            return;
                        case "tacz:m249":
                            cir.setReturnValue(taczjs$creativeTabsGunItem("mg"));
                    }
                }
            }
        }
    }

    @Unique
    private ItemStack taczjs$creativeTabsGunItem(String gunType) {
        var firstGun = CommonGunPackLoader.getAllGuns().stream().filter(x -> Objects.equals(x.getValue().getType(), gunType)).findFirst().orElse(null);
        if (firstGun == null) return ModItems.GUN_SMITH_TABLE.get().getDefaultInstance();
        var gun = new ItemStack(GunItemManager.getGunItemRegistryObject("modern_kinetic").get(), 1);
        if (gun.getItem() instanceof IGun iGun) {
            iGun.setGunId(gun, firstGun.getKey());
        }
        return gun;
    }
}
