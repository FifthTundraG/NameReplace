package io.github.fifthtundrag.namereplace.mixin;

import io.github.fifthtundrag.namereplace.NameReplace;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// This is used for the client so it has the correct display name. This will show in the tablist but not nametags.
@Mixin(ClientboundPlayerInfoUpdatePacket.Entry.class)
public class ClientboundPlayerInfoUpdatePacketEntryMixin {

    @Redirect(
            method = "<init>(Lnet/minecraft/server/level/ServerPlayer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;getTabListDisplayName()Lnet/minecraft/network/chat/Component;"
            )
    )
    private static Component redirectDisplayName(ServerPlayer player) {
        String realPlayerName = player.getGameProfile().getName();
        if (NameReplace.config.replacements.containsKey(realPlayerName)) {
            final String newName = NameReplace.config.replacements.get(realPlayerName);
            // these two lines are stolen from ServerPlayer#getDisplayName, can we figure out a way to not copy/paste them?
            MutableComponent mutableComponent = PlayerTeam.formatNameForTeam(player.getTeam(), Component.literal(newName));
            return ((PlayerInvoker) player).callDecorateDisplayNameComponent(mutableComponent);
        }
        return player.getTabListDisplayName();
    }
}
