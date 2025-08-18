package io.github.fifthtundrag.namereplace;

import com.google.gson.Gson;
import io.github.fifthtundrag.namereplace.config.Config;
import io.github.fifthtundrag.namereplace.config.ConfigIO;
import io.github.fifthtundrag.namereplace.util.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameReplace {
    public static final String MOD_ID = "namereplace";
    public static final Logger LOGGER = LoggerFactory.getLogger("NameReplace");

    public static final Gson GSON = new Gson();

    public static final ConfigIO configIO = new ConfigIO(Platform.INSTANCE.getConfigDir().resolve("namereplace.json"));
    public static Config config;

    public static void init() {
        LOGGER.info("Loading/creating NameReplace config.");
        config = configIO.load();
    }
}
