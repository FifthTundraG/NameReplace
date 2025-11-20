package io.github.fifthtundrag.namereplace.util;

import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;

public interface PlatformHelper {
    Path getConfigDir();

    /** NeoForge needs to run Player#refreshDisplayName for our new display name to apply, but this method does not exist on Fabric and also just literally doesn't need to be run.
     * <p>
     * The NeoForge impl of this should run that method, Fabric doesn't need to do anything.
     * */
    void refreshPlayerDisplayName(Player player);
}