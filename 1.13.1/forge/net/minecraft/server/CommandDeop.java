package net.minecraft.server;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;

public class CommandDeop {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.deop.failed", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("deop").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(3);
        })).then(CommandDispatcher.a("targets", ArgumentProfile.a()).suggests((commandcontext, suggestionsbuilder) -> {
            return ICompletionProvider.a(((CommandListenerWrapper)commandcontext.getSource()).getServer().getPlayerList().n(), suggestionsbuilder);
        }).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentProfile.a(commandcontext, "targets"));
        })));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<GameProfile> collection) throws CommandSyntaxException {
        PlayerList playerlist = commandlistenerwrapper.getServer().getPlayerList();
        int i = 0;

        for(GameProfile gameprofile : collection) {
            if (playerlist.isOp(gameprofile)) {
                playerlist.removeOp(gameprofile);
                ++i;
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.deop.success", new Object[]{((GameProfile)collection.iterator().next()).getName()}), true);
            }
        }

        if (i == 0) {
            throw a.create();
        } else {
            commandlistenerwrapper.getServer().a(commandlistenerwrapper);
            return i;
        }
    }
}
