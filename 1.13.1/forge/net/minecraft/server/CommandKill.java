package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;

public class CommandKill {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("kill").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("targets", ArgumentEntity.b()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.b(commandcontext, "targets"));
        })));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<? extends Entity> collection) {
        for(Entity entity : collection) {
            entity.killEntity();
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.kill.success.single", new Object[]{((Entity)collection.iterator().next()).getScoreboardDisplayName()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.kill.success.multiple", new Object[]{collection.size()}), true);
        }

        return collection.size();
    }
}
