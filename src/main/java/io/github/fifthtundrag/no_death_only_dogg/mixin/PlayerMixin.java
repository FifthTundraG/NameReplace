package io.github.fifthtundrag.no_death_only_dogg.mixin;

import com.mojang.authlib.GameProfile;
import io.github.fifthtundrag.no_death_only_dogg.NoDeathOnlyDogg;
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
        if (Objects.equals(this.gameProfile.getName(), NoDeathOnlyDogg.NEW_NAME)) {
            cir.setReturnValue(Component.literal(String.format("%s (formerly known as %s)", NoDeathOnlyDogg.NEW_NAME, NoDeathOnlyDogg.OLD_NAME)));
        }
    }
}
