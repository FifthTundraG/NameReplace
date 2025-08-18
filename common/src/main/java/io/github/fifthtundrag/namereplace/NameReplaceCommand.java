package io.github.fifthtundrag.namereplace;

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
                        .executes(commandContext -> {
                            commandContext.getSource().sendSuccess(() -> Component.literal(NameReplace.config.toString()), false);
                            return 1;
                        })
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

        // note to self: i18n isn't possible thru mojang's builtin system bc the server sends translation keys to the client and expects it to figure it out. the client won't have our lang files unless it also has the mod installed so it justs prints raw translation keys. this obviously will only work with english but at least it's better than the raw key.
        commandSourceStack.sendSuccess(() -> Component.literal(String.format("Set name for %s to \"%s\".", oldName, newName)), true);
        sendPlayerInfoUpdate(commandSourceStack.getServer().getPlayerList());
        return 1;
    }

    private static int removeName(CommandSourceStack commandSourceStack, String name) {
        NameReplace.config.remove(name);
        NameReplace.configIO.save(NameReplace.config);

        commandSourceStack.sendSuccess(() -> Component.literal(String.format("Removed custom name for %s.", name)), true);
        sendPlayerInfoUpdate(commandSourceStack.getServer().getPlayerList());
        return 1;
    }

    private static int clearAll(CommandSourceStack commandSourceStack) {
        NameReplace.config.clear();
        NameReplace.configIO.save(NameReplace.config);

        commandSourceStack.sendSuccess(() -> Component.literal("Cleared all replaced names."), true);
        sendPlayerInfoUpdate(commandSourceStack.getServer().getPlayerList());
        return 1;
    }

    private static void sendPlayerInfoUpdate(PlayerList playerList) {
        playerList.broadcastAll(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME), playerList.getPlayers()));
    }
}
