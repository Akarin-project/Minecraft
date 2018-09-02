package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;

public class CommandKick {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("kick").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(3);
        })).then(((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), new ChatMessage("multiplayer.disconnect.kicked", new Object[0]));
        })).then(CommandDispatcher.a("reason", ArgumentChat.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentChat.a(commandcontext, "reason"));
        }))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, IChatBaseComponent ichatbasecomponent) {
        for(EntityPlayer entityplayer : collection) {
            entityplayer.playerConnection.disconnect(ichatbasecomponent);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.kick.success", new Object[]{entityplayer.getScoreboardDisplayName(), ichatbasecomponent}), true);
        }

        return collection.size();
    }
}
