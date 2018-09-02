package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandSay {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("say").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("message", ArgumentChat.a()).executes((commandcontext) -> {
            IChatBaseComponent ichatbasecomponent = ArgumentChat.a(commandcontext, "message");
            ((CommandListenerWrapper)commandcontext.getSource()).getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.announcement", new Object[]{((CommandListenerWrapper)commandcontext.getSource()).getScoreboardDisplayName(), ichatbasecomponent}));
            return 1;
        })));
    }
}
