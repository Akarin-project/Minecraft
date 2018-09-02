package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CommandTrigger {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.trigger.failed.unprimed", new Object[0]));
    private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("commands.trigger.failed.invalid", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)CommandDispatcher.a("trigger").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).suggests((commandcontext, suggestionsbuilder) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), suggestionsbuilder);
        }).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(((CommandListenerWrapper)commandcontext.getSource()).h(), ArgumentScoreboardObjective.a(commandcontext, "objective")));
        })).then(CommandDispatcher.a("add").then(CommandDispatcher.a("value", IntegerArgumentType.integer()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(((CommandListenerWrapper)commandcontext.getSource()).h(), ArgumentScoreboardObjective.a(commandcontext, "objective")), IntegerArgumentType.getInteger(commandcontext, "value"));
        })))).then(CommandDispatcher.a("set").then(CommandDispatcher.a("value", IntegerArgumentType.integer()).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), a(((CommandListenerWrapper)commandcontext.getSource()).h(), ArgumentScoreboardObjective.a(commandcontext, "objective")), IntegerArgumentType.getInteger(commandcontext, "value"));
        })))));
    }

    public static CompletableFuture<Suggestions> a(CommandListenerWrapper commandlistenerwrapper, SuggestionsBuilder suggestionsbuilder) {
        Entity entity = commandlistenerwrapper.f();
        ArrayList arraylist = Lists.newArrayList();
        if (entity != null) {
            ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
            String s = entity.getName();

            for(ScoreboardObjective scoreboardobjective : scoreboardserver.getObjectives()) {
                if (scoreboardobjective.getCriteria() == IScoreboardCriteria.c && scoreboardserver.b(s, scoreboardobjective)) {
                    ScoreboardScore scoreboardscore = scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective);
                    if (!scoreboardscore.g()) {
                        arraylist.add(scoreboardobjective.getName());
                    }
                }
            }
        }

        return ICompletionProvider.b(arraylist, suggestionsbuilder);
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardScore scoreboardscore, int i) {
        scoreboardscore.addScore(i);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.trigger.add.success", new Object[]{scoreboardscore.getObjective().e(), i}), true);
        return scoreboardscore.getScore();
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, ScoreboardScore scoreboardscore, int i) {
        scoreboardscore.setScore(i);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.trigger.set.success", new Object[]{scoreboardscore.getObjective().e(), i}), true);
        return i;
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardScore scoreboardscore) {
        scoreboardscore.addScore(1);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.trigger.simple.success", new Object[]{scoreboardscore.getObjective().e()}), true);
        return scoreboardscore.getScore();
    }

    private static ScoreboardScore a(EntityPlayer entityplayer, ScoreboardObjective scoreboardobjective) throws CommandSyntaxException {
        if (scoreboardobjective.getCriteria() != IScoreboardCriteria.c) {
            throw b.create();
        } else {
            Scoreboard scoreboard = entityplayer.getScoreboard();
            String s = entityplayer.getName();
            if (!scoreboard.b(s, scoreboardobjective)) {
                throw a.create();
            } else {
                ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(s, scoreboardobjective);
                if (scoreboardscore.g()) {
                    throw a.create();
                } else {
                    scoreboardscore.a(true);
                    return scoreboardscore;
                }
            }
        }
    }
}
