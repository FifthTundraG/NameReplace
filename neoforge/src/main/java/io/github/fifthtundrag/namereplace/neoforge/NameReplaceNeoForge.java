package io.github.fifthtundrag.namereplace.neoforge;

import io.github.fifthtundrag.namereplace.NameReplace;
import net.neoforged.fml.common.Mod;

@Mod(NameReplace.MOD_ID)
public final class NameReplaceNeoForge {
    public NameReplaceNeoForge() {
        // Run our common setup.
        NameReplace.init();
    }
}