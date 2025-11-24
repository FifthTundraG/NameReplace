package io.github.fifthtundrag.namereplace.fabric.mixin;

import com.mojang.brigadier.CommandDispatcher;
import io.github.fifthtundrag.namereplace.NameReplaceCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * I don't want to require the Fabric API for something small like this so I prefer just mixing into Commands instead
 * <p>
 * Fabric only: NeoForge instantiates {@link Commands} way before Fabric so fails when {@link io.github.fifthtundrag.namereplace.NameReplace#config} is null. Use native command registration for that loader instead.
 */
@Mixin(Commands.class)
public class CommandsMixin {
    @Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onRegisterCommands(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci) {
        NameReplaceCommand.register(this.dispatcher);
    }
}