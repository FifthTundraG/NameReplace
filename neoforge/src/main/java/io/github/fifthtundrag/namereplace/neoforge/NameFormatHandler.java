package io.github.fifthtundrag.namereplace.neoforge;

import io.github.fifthtundrag.namereplace.NameReplace;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class NameFormatHandler {
    /**
     * On Fabric {@link io.github.fifthtundrag.namereplace.mixin.PlayerMixin} is sufficient to replace the name, NeoForge requires some special work to change it. This is that work.
     * <p>
     * We also need to run {@link Player#refreshDisplayName()} for this to apply, we do that in {@link io.github.fifthtundrag.namereplace.NameReplaceCommand}
     * */
    @SubscribeEvent
    public void onNameFormat(PlayerEvent.NameFormat event) {
        String realPlayerName = event.getEntity().getGameProfile().getName();
        if (NameReplace.config.replacements.containsKey(realPlayerName)) {
            String newName = io.github.fifthtundrag.namereplace.NameReplace.config.replacements.get(realPlayerName);
            event.setDisplayname(Component.literal(newName));
        }
    }
}
