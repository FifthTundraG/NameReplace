package io.github.fifthtundrag.namereplace.util;

public class Platform {
    public static final PlatformHelper INSTANCE = findHelper();

    private static PlatformHelper findHelper() {
        try {
            return (PlatformHelper) Class.forName("io.github.fifthtundrag.namereplace.fabric.FabricPlatformHelper")
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (ClassNotFoundException ignored) {
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to init FabricPlatformHelper", e);
        }

        try {
            return (PlatformHelper) Class.forName("io.github.fifthtundrag.namereplace.neoforge.NeoForgePlatformHelper")
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (ClassNotFoundException ignored) {
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to init NeoForgePlatformHelper", e);
        }

        throw new RuntimeException("No PlatformHelper found!");
    }
}
