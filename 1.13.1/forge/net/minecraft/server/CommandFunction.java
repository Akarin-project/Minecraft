package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;

public class CommandFunction {
    public static final SuggestionProvider<CommandListenerWrapper> a = (commandcontext, suggestionsbuilder) -> {
        CustomFunctionData customfunctiondata = ((CommandListenerWrapper)commandcontext.getSource()).getServer().getFunctionData();
        ICompletionProvider.a(customfunctiondata.g().a(), suggestionsbuilder, "#");
        return ICompletionProvider.a(customfunctiondata.c().keySet(), suggestionsbuilder);
    };

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("function").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("name", ArgumentTag.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentTag.a(commandcontext, "name"));
        })));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<CustomFunction> collection) {
        int i = 0;

        for(CustomFunction customfunction : collection) {
            i += commandlistenerwrapper.getServer().getFunctionData().a(customfunction, commandlistenerwrapper.a().b(2));
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.function.success.single", new Object[]{i, ((CustomFunction)collection.iterator().next()).a()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.function.success.multiple", new Object[]{i, collection.size()}), true);
        }

        return i;
    }
}
