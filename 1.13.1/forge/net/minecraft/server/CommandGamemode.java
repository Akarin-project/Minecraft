package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import java.util.Collections;

public class CommandGamemode {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        LiteralArgumentBuilder literalargumentbuilder = (LiteralArgumentBuilder)CommandDispatcher.a("gamemode").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        });

        for(EnumGamemode enumgamemode : EnumGamemode.values()) {
            if (enumgamemode != EnumGamemode.NOT_SET) {
                literalargumentbuilder.then(((LiteralArgumentBuilder)CommandDispatcher.a(enumgamemode.b()).executes((commandcontext) -> {
                    return a(commandcontext, Collections.singleton(((CommandListenerWrapper)commandcontext.getSource()).h()), enumgamemode);
                })).then(CommandDispatcher.a("target", ArgumentEntity.d()).executes((commandcontext) -> {
                    return a(commandcontext, ArgumentEntity.f(commandcontext, "target"), enumgamemode);
                })));
            }
        }

        commanddispatcher.register(literalargumentbuilder);
    }

    private static void a(CommandListenerWrapper commandlistenerwrapper, EntityPlayer entityplayer, EnumGamemode enumgamemode) {
        ChatMessage chatmessage = new ChatMessage("gameMode." + enumgamemode.b(), new Object[0]);
        if (commandlistenerwrapper.f() == entityplayer) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.gamemode.success.self", new Object[]{chatmessage}), true);
        } else {
            if (commandlistenerwrapper.getWorld().getGameRules().getBoolean("sendCommandFeedback")) {
                entityplayer.sendMessage(new ChatMessage("gameMode.changed", new Object[]{chatmessage}));
            }

            commandlistenerwrapper.sendMessage(new ChatMessage("commands.gamemode.success.other", new Object[]{entityplayer.getScoreboardDisplayName(), chatmessage}), true);
        }

    }

    private static int a(CommandContext<CommandListenerWrapper> commandcontext, Collection<EntityPlayer> collection, EnumGamemode enumgamemode) {
        int i = 0;

        for(EntityPlayer entityplayer : collection) {
            if (entityplayer.playerInteractManager.getGameMode() != enumgamemode) {
                entityplayer.a(enumgamemode);
                a((CommandListenerWrapper)commandcontext.getSource(), entityplayer, enumgamemode);
                ++i;
            }
        }

        return i;
    }
}
