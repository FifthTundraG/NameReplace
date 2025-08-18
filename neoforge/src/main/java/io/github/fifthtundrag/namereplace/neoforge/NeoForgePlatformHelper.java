package io.github.fifthtundrag.namereplace.neoforge;

import io.github.fifthtundrag.namereplace.util.PlatformHelper;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class NeoForgePlatformHelper implements PlatformHelper {
    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
