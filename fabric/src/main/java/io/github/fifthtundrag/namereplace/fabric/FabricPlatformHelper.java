package io.github.fifthtundrag.namereplace.fabric;

import io.github.fifthtundrag.namereplace.util.PlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class FabricPlatformHelper implements PlatformHelper {
    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
