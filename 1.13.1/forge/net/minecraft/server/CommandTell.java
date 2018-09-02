package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;

public class CommandTell {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        LiteralCommandNode literalcommandnode = commanddispatcher.register((LiteralArgumentBuilder)CommandDispatcher.a("msg").then(CommandDispatcher.a("targets", ArgumentEntity.d()).then(CommandDispatcher.a("message", ArgumentChat.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentChat.a(commandcontext, "message"));
        }))));
        commanddispatcher.register((LiteralArgumentBuilder)CommandDispatcher.a("tell").redirect(literalcommandnode));
        commanddispatcher.register((LiteralArgumentBuilder)CommandDispatcher.a("w").redirect(literalcommandnode));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, IChatBaseComponent ichatbasecomponent) {
        for(EntityPlayer entityplayer : collection) {
            entityplayer.sendMessage((new ChatMessage("commands.message.display.incoming", new Object[]{commandlistenerwrapper.getScoreboardDisplayName(), ichatbasecomponent.h()})).a(new EnumChatFormat[]{EnumChatFormat.GRAY, EnumChatFormat.ITALIC}));
            commandlistenerwrapper.sendMessage((new ChatMessage("commands.message.display.outgoing", new Object[]{entityplayer.getScoreboardDisplayName(), ichatbasecomponent.h()})).a(new EnumChatFormat[]{EnumChatFormat.GRAY, EnumChatFormat.ITALIC}), false);
        }

        return collection.size();
    }
}
