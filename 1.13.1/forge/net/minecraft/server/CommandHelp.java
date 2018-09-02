package net.minecraft.server;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;

public class CommandHelp {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.help.failed", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("help").executes((commandcontext) -> {
            Map map = commanddispatcher.getSmartUsage(commanddispatcher.getRoot(), commandcontext.getSource());

            for(String s : map.values()) {
                ((CommandListenerWrapper)commandcontext.getSource()).sendMessage(new ChatComponentText("/" + s), false);
            }

            return map.size();
        })).then(CommandDispatcher.a("command", StringArgumentType.greedyString()).executes((commandcontext) -> {
            ParseResults parseresults = commanddispatcher.parse(StringArgumentType.getString(commandcontext, "command"), commandcontext.getSource());
            if (parseresults.getContext().getNodes().isEmpty()) {
                throw a.create();
            } else {
                Map map = commanddispatcher.getSmartUsage((CommandNode)Iterables.getLast(parseresults.getContext().getNodes().keySet()), commandcontext.getSource());

                for(String s : map.values()) {
                    ((CommandListenerWrapper)commandcontext.getSource()).sendMessage(new ChatComponentText("/" + parseresults.getReader().getString() + " " + s), false);
                }

                return map.size();
            }
        })));
    }
}
