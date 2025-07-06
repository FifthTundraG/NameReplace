package io.github.fifthtundrag.name_replace.mixin;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Player.class)
public interface PlayerInvoker {
    // we have this class bc decorateDisplayNameComponent does some fancy stuff that i don't want to have to copy/paste everywhere. this is easier.
    @Invoker("decorateDisplayNameComponent")
    MutableComponent callDecorateDisplayNameComponent(MutableComponent displayName);
}
