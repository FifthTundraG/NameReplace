package io.github.fifthtundrag.name_replace;

import com.google.gson.Gson;
import io.github.fifthtundrag.name_replace.config.Config;
import io.github.fifthtundrag.name_replace.config.ConfigIO;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameReplace implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NameReplace");
    public static final Gson GSON = new Gson();

    public static final ConfigIO configIO = new ConfigIO(FabricLoader.getInstance().getConfigDir().resolve("name_replace.json"));
    public static Config config;

    @Override
    public void onInitialize() {
        LOGGER.info("Loading/creating NameReplace config.");
        config = configIO.load();
    }
}
