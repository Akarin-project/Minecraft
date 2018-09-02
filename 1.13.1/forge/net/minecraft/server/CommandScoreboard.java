package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

public class CommandScoreboard {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.scoreboard.objectives.add.duplicate", new Object[0]));
    private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("commands.scoreboard.objectives.display.alreadyEmpty", new Object[0]));
    private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ChatMessage("commands.scoreboard.objectives.display.alreadySet", new Object[0]));
    private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ChatMessage("commands.scoreboard.players.enable.failed", new Object[0]));
    private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ChatMessage("commands.scoreboard.players.enable.invalid", new Object[0]));
    private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("commands.scoreboard.players.get.null", new Object[]{object, object1});
    });

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("scoreboard").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("objectives").then(CommandDispatcher.a("list").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource());
        }))).then(CommandDispatcher.a("add").then(CommandDispatcher.a("objective", StringArgumentType.word()).then(((RequiredArgumentBuilder)CommandDispatcher.a("criteria", ArgumentScoreboardCriteria.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), StringArgumentType.getString(commandcontext, "objective"), ArgumentScoreboardCriteria.a(commandcontext, "criteria"), new ChatComponentText(StringArgumentType.getString(commandcontext, "objective")));
        })).then(CommandDispatcher.a("displayName", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), StringArgumentType.getString(commandcontext, "objective"), ArgumentScoreboardCriteria.a(commandcontext, "criteria"), ArgumentChatComponent.a(commandcontext, "displayName"));
        })))))).then(CommandDispatcher.a("modify").then(((RequiredArgumentBuilder)CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).then(CommandDispatcher.a("displayname").then(CommandDispatcher.a("displayName", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardObjective.a(commandcontext, "objective"), ArgumentChatComponent.a(commandcontext, "displayName"));
        })))).then(a())))).then(CommandDispatcher.a("remove").then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardObjective.a(commandcontext, "objective"));
        })))).then(CommandDispatcher.a("setdisplay").then(((RequiredArgumentBuilder)CommandDispatcher.a("slot", ArgumentScoreboardSlot.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardSlot.a(commandcontext, "slot"));
        })).then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardSlot.a(commandcontext, "slot"), ArgumentScoreboardObjective.a(commandcontext, "objective"));
        })))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("players").then(((LiteralArgumentBuilder)CommandDispatcher.a("list").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource());
        })).then(CommandDispatcher.a("target", ArgumentScoreholder.a()).suggests(ArgumentScoreholder.a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.a(commandcontext, "target"));
        })))).then(CommandDispatcher.a("set").then(CommandDispatcher.a("targets", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).then(CommandDispatcher.a("score", IntegerArgumentType.integer()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"), ArgumentScoreboardObjective.b(commandcontext, "objective"), IntegerArgumentType.getInteger(commandcontext, "score"));
        })))))).then(CommandDispatcher.a("get").then(CommandDispatcher.a("target", ArgumentScoreholder.a()).suggests(ArgumentScoreholder.a).then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.a(commandcontext, "target"), ArgumentScoreboardObjective.a(commandcontext, "objective"));
        }))))).then(CommandDispatcher.a("add").then(CommandDispatcher.a("targets", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).then(CommandDispatcher.a("score", IntegerArgumentType.integer(0)).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"), ArgumentScoreboardObjective.b(commandcontext, "objective"), IntegerArgumentType.getInteger(commandcontext, "score"));
        })))))).then(CommandDispatcher.a("remove").then(CommandDispatcher.a("targets", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).then(CommandDispatcher.a("score", IntegerArgumentType.integer(0)).executes((commandcontext) -> {
            return c((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"), ArgumentScoreboardObjective.b(commandcontext, "objective"), IntegerArgumentType.getInteger(commandcontext, "score"));
        })))))).then(CommandDispatcher.a("reset").then(((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"));
        })).then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"), ArgumentScoreboardObjective.a(commandcontext, "objective"));
        }))))).then(CommandDispatcher.a("enable").then(CommandDispatcher.a("targets", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).then(CommandDispatcher.a("objective", ArgumentScoreboardObjective.a()).suggests((commandcontext, suggestionsbuilder) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"), suggestionsbuilder);
        }).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"), ArgumentScoreboardObjective.a(commandcontext, "objective"));
        }))))).then(CommandDispatcher.a("operation").then(CommandDispatcher.a("targets", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).then(CommandDispatcher.a("targetObjective", ArgumentScoreboardObjective.a()).then(CommandDispatcher.a("operation", ArgumentMathOperation.a()).then(CommandDispatcher.a("source", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).then(CommandDispatcher.a("sourceObjective", ArgumentScoreboardObjective.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "targets"), ArgumentScoreboardObjective.b(commandcontext, "targetObjective"), ArgumentMathOperation.a(commandcontext, "operation"), ArgumentScoreholder.c(commandcontext, "source"), ArgumentScoreboardObjective.a(commandcontext, "sourceObjective"));
        })))))))));
    }

    private static LiteralArgumentBuilder<CommandListenerWrapper> a() {
        LiteralArgumentBuilder literalargumentbuilder = CommandDispatcher.a("rendertype");

        for(IScoreboardCriteria.EnumScoreboardHealthDisplay iscoreboardcriteria$enumscoreboardhealthdisplay : IScoreboardCriteria.EnumScoreboardHealthDisplay.values()) {
            literalargumentbuilder.then(CommandDispatcher.a(iscoreboardcriteria$enumscoreboardhealthdisplay.a()).executes((commandcontext) -> {
                return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardObjective.a(commandcontext, "objective"), iscoreboardcriteria$enumscoreboardhealthdisplay);
            }));
        }

        return literalargumentbuilder;
    }

    private static CompletableFuture<Suggestions> a(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection, SuggestionsBuilder suggestionsbuilder) {
        ArrayList arraylist = Lists.newArrayList();
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();

        for(ScoreboardObjective scoreboardobjective : scoreboardserver.getObjectives()) {
            if (scoreboardobjective.getCriteria() == IScoreboardCriteria.c) {
                boolean flag = false;

                for(String s : collection) {
                    if (!scoreboardserver.b(s, scoreboardobjective) || scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective).g()) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    arraylist.add(scoreboardobjective.getName());
                }
            }
        }

        return ICompletionProvider.b(arraylist, suggestionsbuilder);
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, String s, ScoreboardObjective scoreboardobjective) throws CommandSyntaxException {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        if (!scoreboardserver.b(s, scoreboardobjective)) {
            throw f.create(scoreboardobjective.getName(), s);
        } else {
            ScoreboardScore scoreboardscore = scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.get.success", new Object[]{s, scoreboardscore.getScore(), scoreboardobjective.e()}), false);
            return scoreboardscore.getScore();
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection, ScoreboardObjective scoreboardobjective, ArgumentMathOperation.a argumentmathoperation$a, Collection<String> collection1, ScoreboardObjective scoreboardobjective1) throws CommandSyntaxException {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        int i = 0;

        for(String s : collection) {
            ScoreboardScore scoreboardscore = scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective);

            for(String s1 : collection1) {
                ScoreboardScore scoreboardscore1 = scoreboardserver.getPlayerScoreForObjective(s1, scoreboardobjective1);
                argumentmathoperation$a.apply(scoreboardscore, scoreboardscore1);
            }

            i += scoreboardscore.getScore();
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.operation.success.single", new Object[]{scoreboardobjective.e(), collection.iterator().next(), i}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.operation.success.multiple", new Object[]{scoreboardobjective.e(), collection.size()}), true);
        }

        return i;
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection, ScoreboardObjective scoreboardobjective) throws CommandSyntaxException {
        if (scoreboardobjective.getCriteria() != IScoreboardCriteria.c) {
            throw e.create();
        } else {
            ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
            int i = 0;

            for(String s : collection) {
                ScoreboardScore scoreboardscore = scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective);
                if (scoreboardscore.g()) {
                    scoreboardscore.a(false);
                    ++i;
                }
            }

            if (i == 0) {
                throw d.create();
            } else {
                if (collection.size() == 1) {
                    commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.enable.success.single", new Object[]{scoreboardobjective.e(), collection.iterator().next()}), true);
                } else {
                    commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.enable.success.multiple", new Object[]{scoreboardobjective.e(), collection.size()}), true);
                }

                return i;
            }
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();

        for(String s : collection) {
            scoreboardserver.resetPlayerScores(s, (ScoreboardObjective)null);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.reset.all.single", new Object[]{collection.iterator().next()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.reset.all.multiple", new Object[]{collection.size()}), true);
        }

        return collection.size();
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection, ScoreboardObjective scoreboardobjective) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();

        for(String s : collection) {
            scoreboardserver.resetPlayerScores(s, scoreboardobjective);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.reset.specific.single", new Object[]{scoreboardobjective.e(), collection.iterator().next()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.reset.specific.multiple", new Object[]{scoreboardobjective.e(), collection.size()}), true);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection, ScoreboardObjective scoreboardobjective, int i) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();

        for(String s : collection) {
            ScoreboardScore scoreboardscore = scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective);
            scoreboardscore.setScore(i);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.set.success.single", new Object[]{scoreboardobjective.e(), collection.iterator().next(), i}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.set.success.multiple", new Object[]{scoreboardobjective.e(), collection.size(), i}), true);
        }

        return i * collection.size();
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection, ScoreboardObjective scoreboardobjective, int i) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        int j = 0;

        for(String s : collection) {
            ScoreboardScore scoreboardscore = scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective);
            scoreboardscore.setScore(scoreboardscore.getScore() + i);
            j += scoreboardscore.getScore();
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.add.success.single", new Object[]{i, scoreboardobjective.e(), collection.iterator().next(), j}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.add.success.multiple", new Object[]{i, scoreboardobjective.e(), collection.size()}), true);
        }

        return j;
    }

    private static int c(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection, ScoreboardObjective scoreboardobjective, int i) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        int j = 0;

        for(String s : collection) {
            ScoreboardScore scoreboardscore = scoreboardserver.getPlayerScoreForObjective(s, scoreboardobjective);
            scoreboardscore.setScore(scoreboardscore.getScore() - i);
            j += scoreboardscore.getScore();
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.remove.success.single", new Object[]{i, scoreboardobjective.e(), collection.iterator().next(), j}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.remove.success.multiple", new Object[]{i, scoreboardobjective.e(), collection.size()}), true);
        }

        return j;
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper) {
        Collection collection = commandlistenerwrapper.getServer().getScoreboard().getPlayers();
        if (collection.isEmpty()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.list.empty", new Object[0]), false);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.list.success", new Object[]{collection.size(), ChatComponentUtils.a(collection)}), false);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, String s) {
        Map map = commandlistenerwrapper.getServer().getScoreboard().getPlayerObjectives(s);
        if (map.isEmpty()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.list.entity.empty", new Object[]{s}), false);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.list.entity.success", new Object[]{s, map.size()}), false);

            for(Entry entry : map.entrySet()) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.players.list.entity.entry", new Object[]{((ScoreboardObjective)entry.getKey()).e(), ((ScoreboardScore)entry.getValue()).getScore()}), false);
            }
        }

        return map.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, int i) throws CommandSyntaxException {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        if (scoreboardserver.getObjectiveForSlot(i) == null) {
            throw b.create();
        } else {
            scoreboardserver.setDisplaySlot(i, (ScoreboardObjective)null);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.display.cleared", new Object[]{Scoreboard.h()[i]}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, int i, ScoreboardObjective scoreboardobjective) throws CommandSyntaxException {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        if (scoreboardserver.getObjectiveForSlot(i) == scoreboardobjective) {
            throw c.create();
        } else {
            scoreboardserver.setDisplaySlot(i, scoreboardobjective);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.display.set", new Object[]{Scoreboard.h()[i], scoreboardobjective.getDisplayName()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardObjective scoreboardobjective, IChatBaseComponent ichatbasecomponent) {
        if (!scoreboardobjective.getDisplayName().equals(ichatbasecomponent)) {
            scoreboardobjective.setDisplayName(ichatbasecomponent);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.modify.displayname", new Object[]{scoreboardobjective.getName(), scoreboardobjective.e()}), true);
        }

        return 0;
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardObjective scoreboardobjective, IScoreboardCriteria.EnumScoreboardHealthDisplay iscoreboardcriteria$enumscoreboardhealthdisplay) {
        if (scoreboardobjective.f() != iscoreboardcriteria$enumscoreboardhealthdisplay) {
            scoreboardobjective.a(iscoreboardcriteria$enumscoreboardhealthdisplay);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.modify.rendertype", new Object[]{scoreboardobjective.e()}), true);
        }

        return 0;
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardObjective scoreboardobjective) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        scoreboardserver.unregisterObjective(scoreboardobjective);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.remove.success", new Object[]{scoreboardobjective.e()}), true);
        return scoreboardserver.getObjectives().size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, String s, IScoreboardCriteria iscoreboardcriteria, IChatBaseComponent ichatbasecomponent) throws CommandSyntaxException {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        if (scoreboardserver.getObjective(s) != null) {
            throw a.create();
        } else if (s.length() > 16) {
            throw ArgumentScoreboardObjective.a.create(16);
        } else {
            scoreboardserver.registerObjective(s, iscoreboardcriteria, ichatbasecomponent, iscoreboardcriteria.e());
            ScoreboardObjective scoreboardobjective = scoreboardserver.getObjective(s);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.add.success", new Object[]{scoreboardobjective.e()}), true);
            return scoreboardserver.getObjectives().size();
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper) {
        Collection collection = commandlistenerwrapper.getServer().getScoreboard().getObjectives();
        if (collection.isEmpty()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.list.empty", new Object[0]), false);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.scoreboard.objectives.list.success", new Object[]{collection.size(), ChatComponentUtils.b(collection, ScoreboardObjective::e)}), false);
        }

        return collection.size();
    }
}
