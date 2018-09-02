package net.minecraft.server;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Locale;

public class CommandTitle {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("title").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).then(CommandDispatcher.a("clear").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"));
        }))).then(CommandDispatcher.a("reset").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"));
        }))).then(CommandDispatcher.a("title").then(CommandDispatcher.a("title", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentChatComponent.a(commandcontext, "title"), PacketPlayOutTitle.EnumTitleAction.TITLE);
        })))).then(CommandDispatcher.a("subtitle").then(CommandDispatcher.a("title", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentChatComponent.a(commandcontext, "title"), PacketPlayOutTitle.EnumTitleAction.SUBTITLE);
        })))).then(CommandDispatcher.a("actionbar").then(CommandDispatcher.a("title", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentChatComponent.a(commandcontext, "title"), PacketPlayOutTitle.EnumTitleAction.ACTIONBAR);
        })))).then(CommandDispatcher.a("times").then(CommandDispatcher.a("fadeIn", IntegerArgumentType.integer(0)).then(CommandDispatcher.a("stay", IntegerArgumentType.integer(0)).then(CommandDispatcher.a("fadeOut", IntegerArgumentType.integer(0)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), IntegerArgumentType.getInteger(commandcontext, "fadeIn"), IntegerArgumentType.getInteger(commandcontext, "stay"), IntegerArgumentType.getInteger(commandcontext, "fadeOut"));
        })))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection) {
        PacketPlayOutTitle packetplayouttitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, (IChatBaseComponent)null);

        for(EntityPlayer entityplayer : collection) {
            entityplayer.playerConnection.sendPacket(packetplayouttitle);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.cleared.single", new Object[]{((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.cleared.multiple", new Object[]{collection.size()}), true);
        }

        return collection.size();
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection) {
        PacketPlayOutTitle packetplayouttitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, (IChatBaseComponent)null);

        for(EntityPlayer entityplayer : collection) {
            entityplayer.playerConnection.sendPacket(packetplayouttitle);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.reset.single", new Object[]{((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.reset.multiple", new Object[]{collection.size()}), true);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, IChatBaseComponent ichatbasecomponent, PacketPlayOutTitle.EnumTitleAction packetplayouttitle$enumtitleaction) throws CommandSyntaxException {
        for(EntityPlayer entityplayer : collection) {
            entityplayer.playerConnection.sendPacket(new PacketPlayOutTitle(packetplayouttitle$enumtitleaction, ChatComponentUtils.filterForDisplay(commandlistenerwrapper, ichatbasecomponent, entityplayer)));
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.show." + packetplayouttitle$enumtitleaction.name().toLowerCase(Locale.ROOT) + ".single", new Object[]{((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.show." + packetplayouttitle$enumtitleaction.name().toLowerCase(Locale.ROOT) + ".multiple", new Object[]{collection.size()}), true);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, int i, int j, int k) {
        PacketPlayOutTitle packetplayouttitle = new PacketPlayOutTitle(i, j, k);

        for(EntityPlayer entityplayer : collection) {
            entityplayer.playerConnection.sendPacket(packetplayouttitle);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.times.single", new Object[]{((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.title.times.multiple", new Object[]{collection.size()}), true);
        }

        return collection.size();
    }
}