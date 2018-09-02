package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandStop {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("stop").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(4);
        })).executes((commandcontext) -> {
            ((CommandListenerWrapper)commandcontext.getSource()).sendMessage(new ChatMessage("commands.stop.stopping", new Object[0]), true);
            ((CommandListenerWrapper)commandcontext.getSource()).getServer().safeShutdown();
            return 1;
        }));
    }
}
