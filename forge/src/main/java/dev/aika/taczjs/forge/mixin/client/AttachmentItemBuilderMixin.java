package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
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
@Mixin(value = AttachmentItemBuilder.class, remap = false)
public abstract class AttachmentItemBuilderMixin {
    @Shadow
    private ResourceLocation attachmentId;

    @Inject(method = "build", at = @At("HEAD"), cancellable = true)
    public void build(CallbackInfoReturnable<ItemStack> cir) {
        var st = Thread.currentThread().getStackTrace();
        if (st.length >= 4 && st[3].getClassName().equals("com.tacz.guns.init.ModCreativeTabs")) {
            if (TimelessAPI.getCommonAttachmentIndex(this.attachmentId).isEmpty()) {
                switch (this.attachmentId.toString()) {
                    case "tacz:sight_sro_dot":
                        cir.setReturnValue(taczjs$creativeTabsAttachmentItem(AttachmentType.SCOPE));
                        return;
                    case "tacz:muzzle_compensator_trident":
                        cir.setReturnValue(taczjs$creativeTabsAttachmentItem(AttachmentType.MUZZLE));
                        return;
                    case "tacz:stock_militech_b5":
                        cir.setReturnValue(taczjs$creativeTabsAttachmentItem(AttachmentType.STOCK));
                        return;
                    case "tacz:grip_magpul_afg_2":
                        cir.setReturnValue(taczjs$creativeTabsAttachmentItem(AttachmentType.GRIP));
                        return;
                    case "tacz:extended_mag_3":
                        cir.setReturnValue(taczjs$creativeTabsAttachmentItem(AttachmentType.EXTENDED_MAG));
                }
            }
        }
    }

    @Unique
    private ItemStack taczjs$creativeTabsAttachmentItem(AttachmentType type) {
        var firstAttachment = CommonGunPackLoader.getAllAttachments().stream().filter(x -> Objects.equals(x.getValue().getType(), type)).findFirst().orElse(null);
        if (firstAttachment == null) return ModItems.GUN_SMITH_TABLE.get().getDefaultInstance();
        var attachment = new ItemStack(ModItems.ATTACHMENT.get(), 1);
        if (attachment.getItem() instanceof IAttachment iAttachment)
            iAttachment.setAttachmentId(attachment, firstAttachment.getKey());
        return attachment;
    }
}
