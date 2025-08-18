package io.github.fifthtundrag.namereplace.config;

import com.google.gson.reflect.TypeToken;
import io.github.fifthtundrag.namereplace.NameReplace;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

public record ConfigIO(Path configFilePath) {
    public void save(Config config) {
        try {
            Path parentFolder = this.configFilePath.getParent();
            if (parentFolder != null) {
                Files.createDirectories(parentFolder);
            }
        } catch (IOException exception) {
            NameReplace.LOGGER.error("Encountered exception while checking if config folder exists: ", exception);
            return;
        }

        try (Writer writer = new FileWriter(this.configFilePath.toFile())) {
            NameReplace.GSON.toJson(config, writer);
            NameReplace.LOGGER.info("Wrote a new NameReplace config file.");
        } catch (IOException exception) {
            NameReplace.LOGGER.error("Encountered exception while writing config file: ", exception);
        }
    }

    /**
     * Returns a {@link Config} obtained from the given {@link ConfigIO#configFilePath()}. If a file is not found at that location then a new {@link Config} is created.
     */
    public Config load() {
        Config config = new Config();
        if (configFilePath.toFile().exists()) {
            try (Reader reader = new FileReader(configFilePath.toFile())) {
                Type type = new TypeToken<Config>() {}.getType();
                config = NameReplace.GSON.fromJson(reader, type);
            } catch (IOException e) {
                NameReplace.LOGGER.error("Encountered exception while loading config file: ", e);
            }
        }
        return config;
    }
}
