package dev.aika.taczjs.forge.mixin.client;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
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
@Mixin(value = AttachmentItemBuilder.class, remap = false)
public abstract class AttachmentItemBuilderMixin {
    @Shadow
    private ResourceLocation attachmentId;

    @Inject(method = "build", at = @At("HEAD"), cancellable = true)
    private void build(CallbackInfoReturnable<ItemStack> cir) {
        StackWalker walker = StackWalker.getInstance();
        walker.walk(f -> f.skip(2).findFirst()).ifPresent(f -> {
            if (!f.getClassName().equals("com.tacz.guns.init.ModCreativeTabs")) return;
            if (TimelessAPI.getCommonAttachmentIndex(this.attachmentId).isPresent()) return;
            // https://github.com/MCModderAnchor/TACZ/blob/bd41964da0a869808ce963be5004dcb1d0fa4d69/src/main/java/com/tacz/guns/init/ModCreativeTabs.java#L42
            var ATTACHMENT_TYPE_MAP = Map.of(
                    "scope_acog_ta31", AttachmentType.SCOPE,
                    "muzzle_compensator_trident", AttachmentType.MUZZLE,
                    "stock_militech_b5", AttachmentType.SCOPE,
                    "grip_magpul_afg_2", AttachmentType.GRIP,
                    "extended_mag_3", AttachmentType.EXTENDED_MAG
            );
            var result = Optional.ofNullable(ATTACHMENT_TYPE_MAP.get(this.attachmentId.getPath()))
                    .flatMap(type -> TimelessAPI.getAllCommonAttachmentIndex().stream()
                            .filter(x -> Objects.equals(x.getValue().getType(), type))
                            .findFirst()
                    )
                    .map(first -> {
                        var itemStack = new ItemStack(ModItems.ATTACHMENT.get(), 1);
                        if (itemStack.getItem() instanceof IAttachment i) i.setAttachmentId(itemStack, first.getKey());
                        return itemStack;
                    })
                    .orElse(ModItems.GUN_SMITH_TABLE.get().getDefaultInstance());
            cir.setReturnValue(result);
        });
    }
}
