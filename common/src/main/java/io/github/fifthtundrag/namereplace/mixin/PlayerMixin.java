package io.github.fifthtundrag.namereplace.mixin;

import com.mojang.authlib.GameProfile;
import io.github.fifthtundrag.namereplace.NameReplace;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// This is used for whenever the *server* broadcasts the player's name.
@Mixin(Player.class)
public class PlayerMixin {
    @Shadow @Final private GameProfile gameProfile;

    @Inject(at = @At("HEAD"), method = "getName", cancellable = true)
    private void getName(CallbackInfoReturnable<Component> cir) {
        String realPlayerName = this.gameProfile.getName();
        if (NameReplace.config.containsKey(realPlayerName)) {
            final String newName = NameReplace.config.get(realPlayerName);
            cir.setReturnValue(Component.literal(newName));
        }
    }
}
