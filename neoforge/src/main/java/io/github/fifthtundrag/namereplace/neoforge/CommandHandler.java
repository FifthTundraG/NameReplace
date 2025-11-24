package io.github.fifthtundrag.namereplace.neoforge;

import io.github.fifthtundrag.namereplace.NameReplaceCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class CommandHandler {
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        NameReplaceCommand.register(event.getDispatcher());
    }
}
