package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Map.Entry;

public class CommandGamerule {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        LiteralArgumentBuilder literalargumentbuilder = (LiteralArgumentBuilder)CommandDispatcher.a("gamerule").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        });

        for(Entry entry : GameRules.getGameRules().entrySet()) {
            literalargumentbuilder.then(((LiteralArgumentBuilder)CommandDispatcher.a((String)entry.getKey()).executes((commandcontext) -> {
                return a((CommandListenerWrapper)commandcontext.getSource(), (String)entry.getKey());
            })).then(((GameRules.b)entry.getValue()).b().a("value").executes((commandcontext) -> {
                return a((CommandListenerWrapper)commandcontext.getSource(), (String)entry.getKey(), commandcontext);
            })));
        }

        commanddispatcher.register(literalargumentbuilder);
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, String s, CommandContext<CommandListenerWrapper> commandcontext) {
        GameRules.GameRuleValue gamerules$gamerulevalue = commandlistenerwrapper.getServer().getGameRules().get(s);
        gamerules$gamerulevalue.getType().a(commandcontext, "value", gamerules$gamerulevalue);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.gamerule.set", new Object[]{s, gamerules$gamerulevalue.a()}), true);
        return gamerules$gamerulevalue.c();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, String s) {
        GameRules.GameRuleValue gamerules$gamerulevalue = commandlistenerwrapper.getServer().getGameRules().get(s);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.gamerule.query", new Object[]{s, gamerules$gamerulevalue.a()}), false);
        return gamerules$gamerulevalue.c();
    }
}
