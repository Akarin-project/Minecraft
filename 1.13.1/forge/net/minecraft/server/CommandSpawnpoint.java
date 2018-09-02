package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.Collections;

public class CommandSpawnpoint {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("spawnpoint").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), Collections.singleton(((CommandListenerWrapper)commandcontext.getSource()).h()), new BlockPosition(((CommandListenerWrapper)commandcontext.getSource()).getPosition()));
        })).then(((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), new BlockPosition(((CommandListenerWrapper)commandcontext.getSource()).getPosition()));
        })).then(CommandDispatcher.a("pos", ArgumentPosition.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentPosition.b(commandcontext, "pos"));
        }))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, BlockPosition blockposition) {
        for(EntityPlayer entityplayer : collection) {
            entityplayer.setRespawnPosition(blockposition, true);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.spawnpoint.success.single", new Object[]{blockposition.getX(), blockposition.getY(), blockposition.getZ(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.spawnpoint.success.multiple", new Object[]{blockposition.getX(), blockposition.getY(), blockposition.getZ(), collection.size()}), true);
        }

        return collection.size();
    }
}
