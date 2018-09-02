package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandAdvancement {
    private static final SuggestionProvider<CommandListenerWrapper> a = (commandcontext, suggestionsbuilder) -> {
        Collection collection = ((CommandListenerWrapper)commandcontext.getSource()).getServer().getAdvancementData().b();
        return ICompletionProvider.a(collection.stream().map(Advancement::getName), suggestionsbuilder);
    };

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("advancement").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("grant").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).then(CommandDispatcher.a("only").then(((RequiredArgumentBuilder)CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.GRANT, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.ONLY));
        })).then(CommandDispatcher.a("criterion", StringArgumentType.greedyString()).suggests((commandcontext, suggestionsbuilder) -> {
            return ICompletionProvider.b(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement").getCriteria().keySet(), suggestionsbuilder);
        }).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.GRANT, ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), StringArgumentType.getString(commandcontext, "criterion"));
        }))))).then(CommandDispatcher.a("from").then(CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.GRANT, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.FROM));
        })))).then(CommandDispatcher.a("until").then(CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.GRANT, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.UNTIL));
        })))).then(CommandDispatcher.a("through").then(CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.GRANT, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.THROUGH));
        })))).then(CommandDispatcher.a("everything").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.GRANT, ((CommandListenerWrapper)commandcontext.getSource()).getServer().getAdvancementData().b());
        }))))).then(CommandDispatcher.a("revoke").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).then(CommandDispatcher.a("only").then(((RequiredArgumentBuilder)CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.REVOKE, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.ONLY));
        })).then(CommandDispatcher.a("criterion", StringArgumentType.greedyString()).suggests((commandcontext, suggestionsbuilder) -> {
            return ICompletionProvider.b(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement").getCriteria().keySet(), suggestionsbuilder);
        }).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.REVOKE, ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), StringArgumentType.getString(commandcontext, "criterion"));
        }))))).then(CommandDispatcher.a("from").then(CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.REVOKE, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.FROM));
        })))).then(CommandDispatcher.a("until").then(CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.REVOKE, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.UNTIL));
        })))).then(CommandDispatcher.a("through").then(CommandDispatcher.a("advancement", ArgumentMinecraftKeyRegistered.a()).suggests(a).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.REVOKE, a(ArgumentMinecraftKeyRegistered.a(commandcontext, "advancement"), CommandAdvancement.Filter.THROUGH));
        })))).then(CommandDispatcher.a("everything").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), CommandAdvancement.Action.REVOKE, ((CommandListenerWrapper)commandcontext.getSource()).getServer().getAdvancementData().b());
        })))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, CommandAdvancement.Action commandadvancement$action, Collection<Advancement> collection1) {
        int i = 0;

        for(EntityPlayer entityplayer : collection) {
            i += commandadvancement$action.a(entityplayer, collection1);
        }

        if (i == 0) {
            if (collection1.size() == 1) {
                if (collection.size() == 1) {
                    throw new CommandException(new ChatMessage(commandadvancement$action.a() + ".one.to.one.failure", new Object[]{((Advancement)collection1.iterator().next()).j(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}));
                } else {
                    throw new CommandException(new ChatMessage(commandadvancement$action.a() + ".one.to.many.failure", new Object[]{((Advancement)collection1.iterator().next()).j(), collection.size()}));
                }
            } else if (collection.size() == 1) {
                throw new CommandException(new ChatMessage(commandadvancement$action.a() + ".many.to.one.failure", new Object[]{collection1.size(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}));
            } else {
                throw new CommandException(new ChatMessage(commandadvancement$action.a() + ".many.to.many.failure", new Object[]{collection1.size(), collection.size()}));
            }
        } else {
            if (collection1.size() == 1) {
                if (collection.size() == 1) {
                    commandlistenerwrapper.sendMessage(new ChatMessage(commandadvancement$action.a() + ".one.to.one.success", new Object[]{((Advancement)collection1.iterator().next()).j(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
                } else {
                    commandlistenerwrapper.sendMessage(new ChatMessage(commandadvancement$action.a() + ".one.to.many.success", new Object[]{((Advancement)collection1.iterator().next()).j(), collection.size()}), true);
                }
            } else if (collection.size() == 1) {
                commandlistenerwrapper.sendMessage(new ChatMessage(commandadvancement$action.a() + ".many.to.one.success", new Object[]{collection1.size(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage(commandadvancement$action.a() + ".many.to.many.success", new Object[]{collection1.size(), collection.size()}), true);
            }

            return i;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, CommandAdvancement.Action commandadvancement$action, Advancement advancement, String s) {
        int i = 0;
        if (!advancement.getCriteria().containsKey(s)) {
            throw new CommandException(new ChatMessage("commands.advancement.criterionNotFound", new Object[]{advancement.j(), s}));
        } else {
            for(EntityPlayer entityplayer : collection) {
                if (commandadvancement$action.a(entityplayer, advancement, s)) {
                    ++i;
                }
            }

            if (i == 0) {
                if (collection.size() == 1) {
                    throw new CommandException(new ChatMessage(commandadvancement$action.a() + ".criterion.to.one.failure", new Object[]{s, advancement.j(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}));
                } else {
                    throw new CommandException(new ChatMessage(commandadvancement$action.a() + ".criterion.to.many.failure", new Object[]{s, advancement.j(), collection.size()}));
                }
            } else {
                if (collection.size() == 1) {
                    commandlistenerwrapper.sendMessage(new ChatMessage(commandadvancement$action.a() + ".criterion.to.one.success", new Object[]{s, advancement.j(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
                } else {
                    commandlistenerwrapper.sendMessage(new ChatMessage(commandadvancement$action.a() + ".criterion.to.many.success", new Object[]{s, advancement.j(), collection.size()}), true);
                }

                return i;
            }
        }
    }

    private static List<Advancement> a(Advancement advancement, CommandAdvancement.Filter commandadvancement$filter) {
        ArrayList arraylist = Lists.newArrayList();
        if (commandadvancement$filter.f) {
            for(Advancement advancement1 = advancement.b(); advancement1 != null; advancement1 = advancement1.b()) {
                arraylist.add(advancement1);
            }
        }

        arraylist.add(advancement);
        if (commandadvancement$filter.g) {
            a(advancement, arraylist);
        }

        return arraylist;
    }

    private static void a(Advancement advancement, List<Advancement> list) {
        for(Advancement advancement1 : advancement.e()) {
            list.add(advancement1);
            a(advancement1, list);
        }

    }

    static enum Action {
        GRANT("grant") {
            protected boolean a(EntityPlayer entityplayer, Advancement advancement) {
                AdvancementProgress advancementprogress = entityplayer.getAdvancementData().getProgress(advancement);
                if (advancementprogress.isDone()) {
                    return false;
                } else {
                    for(String s : advancementprogress.getRemainingCriteria()) {
                        entityplayer.getAdvancementData().grantCriteria(advancement, s);
                    }

                    return true;
                }
            }

            protected boolean a(EntityPlayer entityplayer, Advancement advancement, String s) {
                return entityplayer.getAdvancementData().grantCriteria(advancement, s);
            }
        },
        REVOKE("revoke") {
            protected boolean a(EntityPlayer entityplayer, Advancement advancement) {
                AdvancementProgress advancementprogress = entityplayer.getAdvancementData().getProgress(advancement);
                if (!advancementprogress.b()) {
                    return false;
                } else {
                    for(String s : advancementprogress.getAwardedCriteria()) {
                        entityplayer.getAdvancementData().revokeCritera(advancement, s);
                    }

                    return true;
                }
            }

            protected boolean a(EntityPlayer entityplayer, Advancement advancement, String s) {
                return entityplayer.getAdvancementData().revokeCritera(advancement, s);
            }
        };

        private final String c;

        private Action(String s1) {
            this.c = "commands.advancement." + s1;
        }

        public int a(EntityPlayer entityplayer, Iterable<Advancement> iterable) {
            int i = 0;

            for(Advancement advancement : iterable) {
                if (this.a(entityplayer, advancement)) {
                    ++i;
                }
            }

            return i;
        }

        protected abstract boolean a(EntityPlayer var1, Advancement var2);

        protected abstract boolean a(EntityPlayer var1, Advancement var2, String var3);

        protected String a() {
            return this.c;
        }
    }

    static enum Filter {
        ONLY(false, false),
        THROUGH(true, true),
        FROM(false, true),
        UNTIL(true, false),
        EVERYTHING(true, true);

        private final boolean f;
        private final boolean g;

        private Filter(boolean flag, boolean flag1) {
            this.f = flag;
            this.g = flag1;
        }
    }
}
