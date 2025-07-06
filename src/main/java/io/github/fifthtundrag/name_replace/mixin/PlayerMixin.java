package io.github.fifthtundrag.name_replace.mixin;

import com.mojang.authlib.GameProfile;
import io.github.fifthtundrag.name_replace.NameReplace;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

// This is used for whenever the *server* broadcasts the player's name.
@Mixin(Player.class)
public class PlayerMixin {
    @Shadow @Final private GameProfile gameProfile;

    @Inject(at = @At("HEAD"), method = "getName", cancellable = true)
    private void getName(CallbackInfoReturnable<Component> cir) {
        if (Objects.equals(this.gameProfile.getName(), NameReplace.NEW_NAME)) {
            cir.setReturnValue(Component.literal(String.format("%s (formerly known as %s)", NameReplace.NEW_NAME, NameReplace.OLD_NAME)));
        }
    }
}
