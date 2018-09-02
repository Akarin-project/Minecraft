package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CommandTeam {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.team.add.duplicate", new Object[0]));
    private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.team.add.longName", new Object[]{object});
    });
    private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ChatMessage("commands.team.empty.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.name.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.color.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.friendlyfire.alreadyEnabled", new Object[0]));
    private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.friendlyfire.alreadyDisabled", new Object[0]));
    private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.seeFriendlyInvisibles.alreadyEnabled", new Object[0]));
    private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.seeFriendlyInvisibles.alreadyDisabled", new Object[0]));
    private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.nametagVisibility.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.deathMessageVisibility.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType l = new SimpleCommandExceptionType(new ChatMessage("commands.team.option.collisionRule.unchanged", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("team").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(((LiteralArgumentBuilder)CommandDispatcher.a("list").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource());
        })).then(CommandDispatcher.a("team", ArgumentScoreboardTeam.a()).executes((commandcontext) -> {
            return c((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"));
        })))).then(CommandDispatcher.a("add").then(((RequiredArgumentBuilder)CommandDispatcher.a("team", StringArgumentType.word()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), StringArgumentType.getString(commandcontext, "team"));
        })).then(CommandDispatcher.a("displayName", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), StringArgumentType.getString(commandcontext, "team"), ArgumentChatComponent.a(commandcontext, "displayName"));
        }))))).then(CommandDispatcher.a("remove").then(CommandDispatcher.a("team", ArgumentScoreboardTeam.a()).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"));
        })))).then(CommandDispatcher.a("empty").then(CommandDispatcher.a("team", ArgumentScoreboardTeam.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"));
        })))).then(CommandDispatcher.a("join").then(((RequiredArgumentBuilder)CommandDispatcher.a("team", ArgumentScoreboardTeam.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), Collections.singleton(((CommandListenerWrapper)commandcontext.getSource()).g().getName()));
        })).then(CommandDispatcher.a("members", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ArgumentScoreholder.c(commandcontext, "members"));
        }))))).then(CommandDispatcher.a("leave").then(CommandDispatcher.a("members", ArgumentScoreholder.b()).suggests(ArgumentScoreholder.a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreholder.c(commandcontext, "members"));
        })))).then(CommandDispatcher.a("modify").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("team", ArgumentScoreboardTeam.a()).then(CommandDispatcher.a("displayName").then(CommandDispatcher.a("displayName", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ArgumentChatComponent.a(commandcontext, "displayName"));
        })))).then(CommandDispatcher.a("color").then(CommandDispatcher.a("value", ArgumentChatFormat.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ArgumentChatFormat.a(commandcontext, "value"));
        })))).then(CommandDispatcher.a("friendlyFire").then(CommandDispatcher.a("allowed", BoolArgumentType.bool()).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), BoolArgumentType.getBool(commandcontext, "allowed"));
        })))).then(CommandDispatcher.a("seeFriendlyInvisibles").then(CommandDispatcher.a("allowed", BoolArgumentType.bool()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), BoolArgumentType.getBool(commandcontext, "allowed"));
        })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("nametagVisibility").then(CommandDispatcher.a("never").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        }))).then(CommandDispatcher.a("hideForOtherTeams").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OTHER_TEAMS);
        }))).then(CommandDispatcher.a("hideForOwnTeam").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OWN_TEAM);
        }))).then(CommandDispatcher.a("always").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
        })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("deathMessageVisibility").then(CommandDispatcher.a("never").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        }))).then(CommandDispatcher.a("hideForOtherTeams").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OTHER_TEAMS);
        }))).then(CommandDispatcher.a("hideForOwnTeam").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.HIDE_FOR_OWN_TEAM);
        }))).then(CommandDispatcher.a("always").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS);
        })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("collisionRule").then(CommandDispatcher.a("never").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumTeamPush.NEVER);
        }))).then(CommandDispatcher.a("pushOwnTeam").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumTeamPush.PUSH_OWN_TEAM);
        }))).then(CommandDispatcher.a("pushOtherTeams").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumTeamPush.PUSH_OTHER_TEAMS);
        }))).then(CommandDispatcher.a("always").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ScoreboardTeamBase.EnumTeamPush.ALWAYS);
        })))).then(CommandDispatcher.a("prefix").then(CommandDispatcher.a("prefix", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ArgumentChatComponent.a(commandcontext, "prefix"));
        })))).then(CommandDispatcher.a("suffix").then(CommandDispatcher.a("suffix", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return c((CommandListenerWrapper)commandcontext.getSource(), ArgumentScoreboardTeam.a(commandcontext, "team"), ArgumentChatComponent.a(commandcontext, "suffix"));
        }))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<String> collection) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();

        for(String s : collection) {
            scoreboardserver.removePlayerFromTeam(s);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.leave.success.single", new Object[]{collection.iterator().next()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.leave.success.multiple", new Object[]{collection.size()}), true);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, Collection<String> collection) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();

        for(String s : collection) {
            scoreboardserver.addPlayerToTeam(s, scoreboardteam);
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.join.success.single", new Object[]{collection.iterator().next(), scoreboardteam.d()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.join.success.multiple", new Object[]{collection.size(), scoreboardteam.d()}), true);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility) throws CommandSyntaxException {
        if (scoreboardteam.getNameTagVisibility() == scoreboardteambase$enumnametagvisibility) {
            throw j.create();
        } else {
            scoreboardteam.setNameTagVisibility(scoreboardteambase$enumnametagvisibility);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.nametagVisibility.success", new Object[]{scoreboardteam.d(), scoreboardteambase$enumnametagvisibility.b()}), true);
            return 0;
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility) throws CommandSyntaxException {
        if (scoreboardteam.getDeathMessageVisibility() == scoreboardteambase$enumnametagvisibility) {
            throw k.create();
        } else {
            scoreboardteam.setDeathMessageVisibility(scoreboardteambase$enumnametagvisibility);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.deathMessageVisibility.success", new Object[]{scoreboardteam.d(), scoreboardteambase$enumnametagvisibility.b()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, ScoreboardTeamBase.EnumTeamPush scoreboardteambase$enumteampush) throws CommandSyntaxException {
        if (scoreboardteam.getCollisionRule() == scoreboardteambase$enumteampush) {
            throw l.create();
        } else {
            scoreboardteam.setCollisionRule(scoreboardteambase$enumteampush);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.collisionRule.success", new Object[]{scoreboardteam.d(), scoreboardteambase$enumteampush.b()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, boolean flag) throws CommandSyntaxException {
        if (scoreboardteam.canSeeFriendlyInvisibles() == flag) {
            if (flag) {
                throw h.create();
            } else {
                throw i.create();
            }
        } else {
            scoreboardteam.setCanSeeFriendlyInvisibles(flag);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.seeFriendlyInvisibles." + (flag ? "enabled" : "disabled"), new Object[]{scoreboardteam.d()}), true);
            return 0;
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, boolean flag) throws CommandSyntaxException {
        if (scoreboardteam.allowFriendlyFire() == flag) {
            if (flag) {
                throw f.create();
            } else {
                throw g.create();
            }
        } else {
            scoreboardteam.setAllowFriendlyFire(flag);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.friendlyfire." + (flag ? "enabled" : "disabled"), new Object[]{scoreboardteam.d()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, IChatBaseComponent ichatbasecomponent) throws CommandSyntaxException {
        if (scoreboardteam.getDisplayName().equals(ichatbasecomponent)) {
            throw d.create();
        } else {
            scoreboardteam.setDisplayName(ichatbasecomponent);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.name.success", new Object[]{scoreboardteam.d()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, EnumChatFormat enumchatformat) throws CommandSyntaxException {
        if (scoreboardteam.getColor() == enumchatformat) {
            throw e.create();
        } else {
            scoreboardteam.setColor(enumchatformat);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.color.success", new Object[]{scoreboardteam.d(), enumchatformat.g()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam) throws CommandSyntaxException {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        ArrayList arraylist = Lists.newArrayList(scoreboardteam.getPlayerNameSet());
        if (arraylist.isEmpty()) {
            throw c.create();
        } else {
            for(String s : arraylist) {
                scoreboardserver.removePlayerFromTeam(s, scoreboardteam);
            }

            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.empty.success", new Object[]{arraylist.size(), scoreboardteam.d()}), true);
            return arraylist.size();
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam) {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        scoreboardserver.removeTeam(scoreboardteam);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.remove.success", new Object[]{scoreboardteam.d()}), true);
        return scoreboardserver.getTeams().size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, String s) throws CommandSyntaxException {
        return a(commandlistenerwrapper, s, new ChatComponentText(s));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, String s, IChatBaseComponent ichatbasecomponent) throws CommandSyntaxException {
        ScoreboardServer scoreboardserver = commandlistenerwrapper.getServer().getScoreboard();
        if (scoreboardserver.getTeam(s) != null) {
            throw a.create();
        } else if (s.length() > 16) {
            throw b.create(16);
        } else {
            ScoreboardTeam scoreboardteam = scoreboardserver.createTeam(s);
            scoreboardteam.setDisplayName(ichatbasecomponent);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.add.success", new Object[]{scoreboardteam.d()}), true);
            return scoreboardserver.getTeams().size();
        }
    }

    private static int c(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam) {
        Collection collection = scoreboardteam.getPlayerNameSet();
        if (collection.isEmpty()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.list.members.empty", new Object[]{scoreboardteam.d()}), false);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.list.members.success", new Object[]{scoreboardteam.d(), collection.size(), ChatComponentUtils.a(collection)}), false);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper) {
        Collection collection = commandlistenerwrapper.getServer().getScoreboard().getTeams();
        if (collection.isEmpty()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.list.teams.empty", new Object[0]), false);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.list.teams.success", new Object[]{collection.size(), ChatComponentUtils.b(collection, ScoreboardTeam::d)}), false);
        }

        return collection.size();
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, IChatBaseComponent ichatbasecomponent) {
        scoreboardteam.setPrefix(ichatbasecomponent);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.prefix.success", new Object[]{ichatbasecomponent}), false);
        return 1;
    }

    private static int c(CommandListenerWrapper commandlistenerwrapper, ScoreboardTeam scoreboardteam, IChatBaseComponent ichatbasecomponent) {
        scoreboardteam.setSuffix(ichatbasecomponent);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.team.option.suffix.success", new Object[]{ichatbasecomponent}), false);
        return 1;
    }
}
