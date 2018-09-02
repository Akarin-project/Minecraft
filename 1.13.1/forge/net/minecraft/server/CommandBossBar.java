package net.minecraft.server;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Collections;

public class CommandBossBar {
    private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.bossbar.create.failed", new Object[]{object});
    });
    private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.bossbar.unknown", new Object[]{object});
    });
    private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.players.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.name.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.color.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.style.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.value.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.max.unchanged", new Object[0]));
    private static final SimpleCommandExceptionType j = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.visibility.unchanged.hidden", new Object[0]));
    private static final SimpleCommandExceptionType k = new SimpleCommandExceptionType(new ChatMessage("commands.bossbar.set.visibility.unchanged.visible", new Object[0]));
    public static final SuggestionProvider<CommandListenerWrapper> a = (commandcontext, suggestionsbuilder) -> {
        return ICompletionProvider.a(((CommandListenerWrapper)commandcontext.getSource()).getServer().aP().a(), suggestionsbuilder);
    };

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("bossbar").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("add").then(CommandDispatcher.a("id", ArgumentMinecraftKeyRegistered.a()).then(CommandDispatcher.a("name", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentMinecraftKeyRegistered.c(commandcontext, "id"), ArgumentChatComponent.a(commandcontext, "name"));
        }))))).then(CommandDispatcher.a("remove").then(CommandDispatcher.a("id", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return e((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext));
        })))).then(CommandDispatcher.a("list").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource());
        }))).then(CommandDispatcher.a("set").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("id", ArgumentMinecraftKeyRegistered.a()).suggests(a).then(CommandDispatcher.a("name").then(CommandDispatcher.a("name", ArgumentChatComponent.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), ArgumentChatComponent.a(commandcontext, "name"));
        })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("color").then(CommandDispatcher.a("pink").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarColor.PINK);
        }))).then(CommandDispatcher.a("blue").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarColor.BLUE);
        }))).then(CommandDispatcher.a("red").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarColor.RED);
        }))).then(CommandDispatcher.a("green").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarColor.GREEN);
        }))).then(CommandDispatcher.a("yellow").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarColor.YELLOW);
        }))).then(CommandDispatcher.a("purple").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarColor.PURPLE);
        }))).then(CommandDispatcher.a("white").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarColor.WHITE);
        })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("style").then(CommandDispatcher.a("progress").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarStyle.PROGRESS);
        }))).then(CommandDispatcher.a("notched_6").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarStyle.NOTCHED_6);
        }))).then(CommandDispatcher.a("notched_10").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarStyle.NOTCHED_10);
        }))).then(CommandDispatcher.a("notched_12").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarStyle.NOTCHED_12);
        }))).then(CommandDispatcher.a("notched_20").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BossBattle.BarStyle.NOTCHED_20);
        })))).then(CommandDispatcher.a("value").then(CommandDispatcher.a("value", IntegerArgumentType.integer(0)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), IntegerArgumentType.getInteger(commandcontext, "value"));
        })))).then(CommandDispatcher.a("max").then(CommandDispatcher.a("max", IntegerArgumentType.integer(1)).executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), IntegerArgumentType.getInteger(commandcontext, "max"));
        })))).then(CommandDispatcher.a("visible").then(CommandDispatcher.a("visible", BoolArgumentType.bool()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), BoolArgumentType.getBool(commandcontext, "visible"));
        })))).then(((LiteralArgumentBuilder)CommandDispatcher.a("players").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), Collections.emptyList());
        })).then(CommandDispatcher.a("targets", ArgumentEntity.d()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext), ArgumentEntity.d(commandcontext, "targets"));
        })))))).then(CommandDispatcher.a("get").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("id", ArgumentMinecraftKeyRegistered.a()).suggests(a).then(CommandDispatcher.a("value").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext));
        }))).then(CommandDispatcher.a("max").executes((commandcontext) -> {
            return b((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext));
        }))).then(CommandDispatcher.a("visible").executes((commandcontext) -> {
            return c((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext));
        }))).then(CommandDispatcher.a("players").executes((commandcontext) -> {
            return d((CommandListenerWrapper)commandcontext.getSource(), a(commandcontext));
        })))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom) {
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.get.value", new Object[]{bossbattlecustom.e(), bossbattlecustom.c()}), true);
        return bossbattlecustom.c();
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom) {
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.get.max", new Object[]{bossbattlecustom.e(), bossbattlecustom.d()}), true);
        return bossbattlecustom.d();
    }

    private static int c(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom) {
        if (bossbattlecustom.g()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.get.visible.visible", new Object[]{bossbattlecustom.e()}), true);
            return 1;
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.get.visible.hidden", new Object[]{bossbattlecustom.e()}), true);
            return 0;
        }
    }

    private static int d(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom) {
        if (bossbattlecustom.getPlayers().isEmpty()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.get.players.none", new Object[]{bossbattlecustom.e()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.get.players.some", new Object[]{bossbattlecustom.e(), bossbattlecustom.getPlayers().size(), ChatComponentUtils.b(bossbattlecustom.getPlayers(), EntityHuman::getScoreboardDisplayName)}), true);
        }

        return bossbattlecustom.getPlayers().size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom, boolean flag) throws CommandSyntaxException {
        if (bossbattlecustom.g() == flag) {
            if (flag) {
                throw k.create();
            } else {
                throw j.create();
            }
        } else {
            bossbattlecustom.setVisible(flag);
            if (flag) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.visible.success.visible", new Object[]{bossbattlecustom.e()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.visible.success.hidden", new Object[]{bossbattlecustom.e()}), true);
            }

            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom, int ix) throws CommandSyntaxException {
        if (bossbattlecustom.c() == ix) {
            throw h.create();
        } else {
            bossbattlecustom.a(ix);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.value.success", new Object[]{bossbattlecustom.e(), ix}), true);
            return ix;
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom, int ix) throws CommandSyntaxException {
        if (bossbattlecustom.d() == ix) {
            throw i.create();
        } else {
            bossbattlecustom.b(ix);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.max.success", new Object[]{bossbattlecustom.e(), ix}), true);
            return ix;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom, BossBattle.BarColor bossbattle$barcolor) throws CommandSyntaxException {
        if (bossbattlecustom.l().equals(bossbattle$barcolor)) {
            throw f.create();
        } else {
            bossbattlecustom.a(bossbattle$barcolor);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.color.success", new Object[]{bossbattlecustom.e()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom, BossBattle.BarStyle bossbattle$barstyle) throws CommandSyntaxException {
        if (bossbattlecustom.m().equals(bossbattle$barstyle)) {
            throw g.create();
        } else {
            bossbattlecustom.a(bossbattle$barstyle);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.style.success", new Object[]{bossbattlecustom.e()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom, IChatBaseComponent ichatbasecomponent) throws CommandSyntaxException {
        IChatBaseComponent ichatbasecomponent1 = ChatComponentUtils.filterForDisplay(commandlistenerwrapper, ichatbasecomponent, (Entity)null);
        if (bossbattlecustom.j().equals(ichatbasecomponent1)) {
            throw e.create();
        } else {
            bossbattlecustom.a(ichatbasecomponent1);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.name.success", new Object[]{bossbattlecustom.e()}), true);
            return 0;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom, Collection<EntityPlayer> collection) throws CommandSyntaxException {
        boolean flag = bossbattlecustom.a(collection);
        if (!flag) {
            throw d.create();
        } else {
            if (bossbattlecustom.getPlayers().isEmpty()) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.players.success.none", new Object[]{bossbattlecustom.e()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.set.players.success.some", new Object[]{bossbattlecustom.e(), collection.size(), ChatComponentUtils.b(collection, EntityHuman::getScoreboardDisplayName)}), true);
            }

            return bossbattlecustom.getPlayers().size();
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper) {
        Collection collection = commandlistenerwrapper.getServer().aP().b();
        if (collection.isEmpty()) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.list.bars.none", new Object[0]), false);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.list.bars.some", new Object[]{collection.size(), ChatComponentUtils.b(collection, BossBattleCustom::e)}), false);
        }

        return collection.size();
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, MinecraftKey minecraftkey, IChatBaseComponent ichatbasecomponent) throws CommandSyntaxException {
        BossBattleCustomData bossbattlecustomdata = commandlistenerwrapper.getServer().aP();
        if (bossbattlecustomdata.a(minecraftkey) != null) {
            throw b.create(minecraftkey.toString());
        } else {
            BossBattleCustom bossbattlecustom = bossbattlecustomdata.a(minecraftkey, ChatComponentUtils.filterForDisplay(commandlistenerwrapper, ichatbasecomponent, (Entity)null));
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.create.success", new Object[]{bossbattlecustom.e()}), true);
            return bossbattlecustomdata.b().size();
        }
    }

    private static int e(CommandListenerWrapper commandlistenerwrapper, BossBattleCustom bossbattlecustom) {
        BossBattleCustomData bossbattlecustomdata = commandlistenerwrapper.getServer().aP();
        bossbattlecustom.b();
        bossbattlecustomdata.a(bossbattlecustom);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.bossbar.remove.success", new Object[]{bossbattlecustom.e()}), true);
        return bossbattlecustomdata.b().size();
    }

    public static BossBattleCustom a(CommandContext<CommandListenerWrapper> commandcontext) throws CommandSyntaxException {
        MinecraftKey minecraftkey = ArgumentMinecraftKeyRegistered.c(commandcontext, "id");
        BossBattleCustom bossbattlecustom = ((CommandListenerWrapper)commandcontext.getSource()).getServer().aP().a(minecraftkey);
        if (bossbattlecustom == null) {
            throw c.create(minecraftkey.toString());
        } else {
            return bossbattlecustom;
        }
    }
}
