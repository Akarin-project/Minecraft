package net.minecraft.server;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandIdleTimeout {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("setidletimeout").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(3);
        })).then(CommandDispatcher.a("minutes", IntegerArgumentType.integer(0)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), IntegerArgumentType.getInteger(commandcontext, "minutes"));
        })));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, int i) {
        commandlistenerwrapper.getServer().setIdleTimeout(i);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.setidletimeout.success", new Object[]{i}), true);
        return i;
    }
}
