package dev.aika.taczjs.helper;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import dev.aika.taczjs.interfaces.IClientGun;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class ModClientHelper {
    public static Optional<IClientGun> getClientGun(LocalPlayer player) {
        if (player == null || player.isSpectator()) return Optional.empty();
        var mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IGun iGun) {
            var gunId = iGun.getGunId(mainHandItem);
            return getClientGun(gunId);
        }
        return Optional.empty();
    }

    public static Optional<IClientGun> getClientGun(ResourceLocation gunId) {
        var gunIndex = TimelessAPI.getClientGunIndex(gunId).orElse(null);
        if (gunIndex instanceof IClientGun) return Optional.of((IClientGun) gunIndex);
        return Optional.empty();
    }

    public static Optional<ClientGunIndex> getClientGunIndex(ResourceLocation gunId) {
        var gunIndex = TimelessAPI.getClientGunIndex(gunId).orElse(null);
        if (gunIndex instanceof IClientGun) return Optional.of(gunIndex);
        return Optional.empty();
    }
}
