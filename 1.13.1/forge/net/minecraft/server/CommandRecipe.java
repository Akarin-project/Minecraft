package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;

public class CommandRecipe {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.recipe.give.failed", new Object[0]));
    private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("commands.recipe.take.failed", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("recipe").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("give").then(((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).then(CommandDispatcher.a("recipe", ArgumentMinecraftKeyRegistered.a()).suggests(CompletionProviders.b).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), Collections.singleton(ArgumentMinecraftKeyRegistered.b(commandcontext, "recipe")));
        }))).then(CommandDispatcher.a("*").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ((CommandListenerWrapper)commandcontext.getSource()).getServer().getCraftingManager().b());
        }))))).then(CommandDispatcher.a("take").then(((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).then(CommandDispatcher.a("recipe", ArgumentMinecraftKeyRegistered.a()).suggests(CompletionProviders.b).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), Collections.singleton(ArgumentMinecraftKeyRegistered.b(commandcontext, "recipe")));
        }))).then(CommandDispatcher.a("*").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ((CommandListenerWrapper)commandcontext.getSource()).getServer().getCraftingManager().b());
        })))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, Collection<IRecipe> collection1) throws CommandSyntaxException {
        int i = 0;

        for(EntityPlayer entityplayer : collection) {
            i += entityplayer.a(collection1);
        }

        if (i == 0) {
            throw a.create();
        } else {
            if (collection.size() == 1) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.recipe.give.success.single", new Object[]{collection1.size(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.recipe.give.success.multiple", new Object[]{collection1.size(), collection.size()}), true);
            }

            return i;
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, Collection<IRecipe> collection1) throws CommandSyntaxException {
        int i = 0;

        for(EntityPlayer entityplayer : collection) {
            i += entityplayer.b(collection1);
        }

        if (i == 0) {
            throw b.create();
        } else {
            if (collection.size() == 1) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.recipe.take.success.single", new Object[]{collection1.size(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.recipe.take.success.multiple", new Object[]{collection1.size(), collection.size()}), true);
            }

            return i;
        }
    }
}
