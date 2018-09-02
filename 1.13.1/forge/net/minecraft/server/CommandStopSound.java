package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import javax.annotation.Nullable;

public class CommandStopSound {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        RequiredArgumentBuilder requiredargumentbuilder = (RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), (SoundCategory)null, (MinecraftKey)null);
        })).then(CommandDispatcher.a("*").then(CommandDispatcher.a("sound", ArgumentMinecraftKeyRegistered.a()).suggests(CompletionProviders.c).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), (SoundCategory)null, ArgumentMinecraftKeyRegistered.c(commandcontext, "sound"));
        })));

        for(SoundCategory soundcategory : SoundCategory.values()) {
            requiredargumentbuilder.then(((LiteralArgumentBuilder)CommandDispatcher.a(soundcategory.a()).executes((commandcontext) -> {
                return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), soundcategory, (MinecraftKey)null);
            })).then(CommandDispatcher.a("sound", ArgumentMinecraftKeyRegistered.a()).suggests(CompletionProviders.c).executes((commandcontext) -> {
                return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), soundcategory, ArgumentMinecraftKeyRegistered.c(commandcontext, "sound"));
            })));
        }

        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("stopsound").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(requiredargumentbuilder));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, @Nullable SoundCategory soundcategory, @Nullable MinecraftKey minecraftkey) {
        PacketPlayOutStopSound packetplayoutstopsound = new PacketPlayOutStopSound(minecraftkey, soundcategory);

        for(EntityPlayer entityplayer : collection) {
            entityplayer.playerConnection.sendPacket(packetplayoutstopsound);
        }

        if (soundcategory != null) {
            if (minecraftkey != null) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.stopsound.success.source.sound", new Object[]{minecraftkey, soundcategory.a()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.stopsound.success.source.any", new Object[]{soundcategory.a()}), true);
            }
        } else if (minecraftkey != null) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.stopsound.success.sourceless.sound", new Object[]{minecraftkey}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.stopsound.success.sourceless.any", new Object[0]), true);
        }

        return collection.size();
    }
}
