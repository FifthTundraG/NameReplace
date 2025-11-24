package io.github.fifthtundrag.namereplace.neoforge;

import io.github.fifthtundrag.namereplace.NameReplace;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(NameReplace.MOD_ID)
public final class NameReplaceNeoForge {
    public NameReplaceNeoForge() {
        // Run our common setup.
        NameReplace.init();

        NeoForge.EVENT_BUS.register(new NameFormatHandler());
        NeoForge.EVENT_BUS.register(new CommandHandler());
    }
}