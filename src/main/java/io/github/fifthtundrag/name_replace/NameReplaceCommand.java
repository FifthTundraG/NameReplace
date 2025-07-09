package io.github.fifthtundrag.name_replace;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.players.PlayerList;

import java.util.EnumSet;

public class NameReplaceCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("namereplace")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(3))
                        .then(Commands.literal("add")
                            .then(Commands.argument("old_name", StringArgumentType.word())
                                .then(Commands.argument("new_name", StringArgumentType.string())
                                        .executes(commandContext -> addName(
                                                commandContext.getSource(),
                                                StringArgumentType.getString(commandContext, "old_name"),
                                                StringArgumentType.getString(commandContext, "new_name")
                                        ))
                                )
                            ))
                        .then(Commands.literal("remove")
                            .then(Commands.argument("name", StringArgumentType.word())
                                    .executes(commandContext -> removeName(
                                            commandContext.getSource(),
                                            StringArgumentType.getString(commandContext, "name")
                                    ))
                            )
                        )
                        .then(Commands.literal("clear")
                            .executes(commandContext -> clearAll(
                                commandContext.getSource()
                            ))
                        )
        );
    }

    private static int addName(CommandSourceStack commandSourceStack, String oldName, String newName) {
        NameReplace.config.put(oldName, newName);
        NameReplace.configIO.save(NameReplace.config);

        commandSourceStack.sendSuccess(() -> Component.translatable("commands.namereplace.add.success", oldName, newName), true);
        sendPlayerInfoUpdate(commandSourceStack.getServer().getPlayerList());
        return 1;
    }

    private static int removeName(CommandSourceStack commandSourceStack, String name) {
        NameReplace.config.remove(name);
        NameReplace.configIO.save(NameReplace.config);

        commandSourceStack.sendSuccess(() -> Component.translatable("commands.namereplace.remove.success", name), true);
        sendPlayerInfoUpdate(commandSourceStack.getServer().getPlayerList());
        return 1;
    }

    private static int clearAll(CommandSourceStack commandSourceStack) {
        NameReplace.config.clear();
        NameReplace.configIO.save(NameReplace.config);

        commandSourceStack.sendSuccess(() -> Component.translatable("commands.namereplace.clear.success"), true);
        sendPlayerInfoUpdate(commandSourceStack.getServer().getPlayerList());
        return 1;
    }

    private static void sendPlayerInfoUpdate(PlayerList playerList) {
        playerList.broadcastAll(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME), playerList.getPlayers()));
    }
}
