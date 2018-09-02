package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandReload {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("reload").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(3);
        })).executes((commandcontext) -> {
            ((CommandListenerWrapper)commandcontext.getSource()).sendMessage(new ChatMessage("commands.reload.success", new Object[0]), true);
            ((CommandListenerWrapper)commandcontext.getSource()).getServer().reload();
            return 0;
        }));
    }
}
