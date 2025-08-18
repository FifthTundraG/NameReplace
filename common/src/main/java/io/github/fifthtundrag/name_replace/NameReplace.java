package io.github.fifthtundrag.name_replace;

import com.google.gson.Gson;
import io.github.fifthtundrag.name_replace.config.Config;
import io.github.fifthtundrag.name_replace.config.ConfigIO;
import io.github.fifthtundrag.name_replace.util.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameReplace {
    public static final Logger LOGGER = LoggerFactory.getLogger("NameReplace");
    public static final Gson GSON = new Gson();

    public static final ConfigIO configIO = new ConfigIO(Platform.INSTANCE.getConfigDir().resolve("name_replace.json"));
    public static Config config;

    public static void init() {
        LOGGER.info("Loading/creating NameReplace config.");
        config = configIO.load();
    }
}
