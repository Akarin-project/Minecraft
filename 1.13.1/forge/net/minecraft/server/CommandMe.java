package net.minecraft.server;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandMe {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)CommandDispatcher.a("me").then(CommandDispatcher.a("action", StringArgumentType.greedyString()).executes((commandcontext) -> {
            ((CommandListenerWrapper)commandcontext.getSource()).getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.emote", new Object[]{((CommandListenerWrapper)commandcontext.getSource()).getScoreboardDisplayName(), StringArgumentType.getString(commandcontext, "action")}));
            return 1;
        })));
    }
}
