package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ArgumentScoreboardCriteria implements ArgumentType<IScoreboardCriteria> {
    private static final Collection<String> b = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
    public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("argument.criteria.invalid", new Object[]{object});
    });

    private ArgumentScoreboardCriteria() {
    }

    public static ArgumentScoreboardCriteria a() {
        return new ArgumentScoreboardCriteria();
    }

    public static IScoreboardCriteria a(CommandContext<CommandListenerWrapper> commandcontext, String s) {
        return (IScoreboardCriteria)commandcontext.getArgument(s, IScoreboardCriteria.class);
    }

    public IScoreboardCriteria a(StringReader stringreader) throws CommandSyntaxException {
        int i = stringreader.getCursor();

        while(stringreader.canRead() && stringreader.peek() != ' ') {
            stringreader.skip();
        }

        String s = stringreader.getString().substring(i, stringreader.getCursor());
        IScoreboardCriteria iscoreboardcriteria = IScoreboardCriteria.a(s);
        if (iscoreboardcriteria == null) {
            stringreader.setCursor(i);
            throw a.create(s);
        } else {
            return iscoreboardcriteria;
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder suggestionsbuilder) {
        ArrayList arraylist = Lists.newArrayList(IScoreboardCriteria.criteria.keySet());

        for(StatisticWrapper statisticwrapper : IRegistry.STATS) {
            for(Object object : statisticwrapper.a()) {
                String s = this.a(statisticwrapper, object);
                arraylist.add(s);
            }
        }

        return ICompletionProvider.b(arraylist, suggestionsbuilder);
    }

    public <T> String a(StatisticWrapper<T> statisticwrapper, Object object) {
        return Statistic.a(statisticwrapper, object);
    }

    public Collection<String> getExamples() {
        return b;
    }

    // $FF: synthetic method
    public Object parse(StringReader stringreader) throws CommandSyntaxException {
        return this.a(stringreader);
    }
}
