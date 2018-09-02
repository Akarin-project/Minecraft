package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

public class CommandSpreadPlayers {
    private static final Dynamic4CommandExceptionType a = new Dynamic4CommandExceptionType((object, object1, object2, object3) -> {
        return new ChatMessage("commands.spreadplayers.failed.teams", new Object[]{object, object1, object2, object3});
    });
    private static final Dynamic4CommandExceptionType b = new Dynamic4CommandExceptionType((object, object1, object2, object3) -> {
        return new ChatMessage("commands.spreadplayers.failed.entities", new Object[]{object, object1, object2, object3});
    });

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("spreadplayers").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("center", ArgumentVec2.a()).then(CommandDispatcher.a("spreadDistance", FloatArgumentType.floatArg(0.0F)).then(CommandDispatcher.a("maxRange", FloatArgumentType.floatArg(1.0F)).then(CommandDispatcher.a("respectTeams", BoolArgumentType.bool()).then(CommandDispatcher.a("targets", ArgumentEntity.b()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentVec2.a(commandcontext, "center"), FloatArgumentType.getFloat(commandcontext, "spreadDistance"), FloatArgumentType.getFloat(commandcontext, "maxRange"), BoolArgumentType.getBool(commandcontext, "respectTeams"), ArgumentEntity.b(commandcontext, "targets"));
        })))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Vec2F vec2f, float f, float f1, boolean flag, Collection<? extends Entity> collection) throws CommandSyntaxException {
        Random random = new Random();
        double d0 = (double)(vec2f.i - f1);
        double d1 = (double)(vec2f.j - f1);
        double d2 = (double)(vec2f.i + f1);
        double d3 = (double)(vec2f.j + f1);
        CommandSpreadPlayers.a[] acommandspreadplayers$a = a(random, flag ? a(collection) : collection.size(), d0, d1, d2, d3);
        a(vec2f, (double)f, commandlistenerwrapper.getWorld(), random, d0, d1, d2, d3, acommandspreadplayers$a, flag);
        double d4 = a(collection, commandlistenerwrapper.getWorld(), acommandspreadplayers$a, flag);
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.spreadplayers.success." + (flag ? "teams" : "entities"), new Object[]{acommandspreadplayers$a.length, vec2f.i, vec2f.j, String.format(Locale.ROOT, "%.2f", d4)}), true);
        return acommandspreadplayers$a.length;
    }

    private static int a(Collection<? extends Entity> collection) {
        HashSet hashset = Sets.newHashSet();

        for(Entity entity : collection) {
            if (entity instanceof EntityHuman) {
                hashset.add(entity.be());
            } else {
                hashset.add((Object)null);
            }
        }

        return hashset.size();
    }

    private static void a(Vec2F vec2f, double d0, WorldServer worldserver, Random random, double d1, double d2, double d3, double d4, CommandSpreadPlayers.a[] acommandspreadplayers$a, boolean flag) throws CommandSyntaxException {
        boolean flag1 = true;
        double d5 = (double)Float.MAX_VALUE;

        int i;
        for(i = 0; i < 10000 && flag1; ++i) {
            flag1 = false;
            d5 = (double)Float.MAX_VALUE;

            for(int j = 0; j < acommandspreadplayers$a.length; ++j) {
                CommandSpreadPlayers.a commandspreadplayers$a = acommandspreadplayers$a[j];
                int k = 0;
                CommandSpreadPlayers.a commandspreadplayers$a1 = new CommandSpreadPlayers.a();

                for(int l = 0; l < acommandspreadplayers$a.length; ++l) {
                    if (j != l) {
                        CommandSpreadPlayers.a commandspreadplayers$a2 = acommandspreadplayers$a[l];
                        double d6 = commandspreadplayers$a.a(commandspreadplayers$a2);
                        d5 = Math.min(d6, d5);
                        if (d6 < d0) {
                            ++k;
                            commandspreadplayers$a1.a = commandspreadplayers$a1.a + (commandspreadplayers$a2.a - commandspreadplayers$a.a);
                            commandspreadplayers$a1.b = commandspreadplayers$a1.b + (commandspreadplayers$a2.b - commandspreadplayers$a.b);
                        }
                    }
                }

                if (k > 0) {
                    commandspreadplayers$a1.a = commandspreadplayers$a1.a / (double)k;
                    commandspreadplayers$a1.b = commandspreadplayers$a1.b / (double)k;
                    double d7 = (double)commandspreadplayers$a1.b();
                    if (d7 > 0.0D) {
                        commandspreadplayers$a1.a();
                        commandspreadplayers$a.b(commandspreadplayers$a1);
                    } else {
                        commandspreadplayers$a.a(random, d1, d2, d3, d4);
                    }

                    flag1 = true;
                }

                if (commandspreadplayers$a.a(d1, d2, d3, d4)) {
                    flag1 = true;
                }
            }

            if (!flag1) {
                for(CommandSpreadPlayers.a commandspreadplayers$a3 : acommandspreadplayers$a) {
                    if (!commandspreadplayers$a3.b(worldserver)) {
                        commandspreadplayers$a3.a(random, d1, d2, d3, d4);
                        flag1 = true;
                    }
                }
            }
        }

        if (d5 == (double)Float.MAX_VALUE) {
            d5 = 0.0D;
        }

        if (i >= 10000) {
            if (flag) {
                throw a.create(acommandspreadplayers$a.length, vec2f.i, vec2f.j, String.format(Locale.ROOT, "%.2f", d5));
            } else {
                throw b.create(acommandspreadplayers$a.length, vec2f.i, vec2f.j, String.format(Locale.ROOT, "%.2f", d5));
            }
        }
    }

    private static double a(Collection<? extends Entity> collection, WorldServer worldserver, CommandSpreadPlayers.a[] acommandspreadplayers$a, boolean flag) {
        double d0 = 0.0D;
        int i = 0;
        HashMap hashmap = Maps.newHashMap();

        for(Entity entity : collection) {
            CommandSpreadPlayers.a commandspreadplayers$a;
            if (flag) {
                ScoreboardTeamBase scoreboardteambase = entity instanceof EntityHuman ? entity.be() : null;
                if (!hashmap.containsKey(scoreboardteambase)) {
                    hashmap.put(scoreboardteambase, acommandspreadplayers$a[i++]);
                }

                commandspreadplayers$a = (CommandSpreadPlayers.a)hashmap.get(scoreboardteambase);
            } else {
                commandspreadplayers$a = acommandspreadplayers$a[i++];
            }

            entity.enderTeleportTo((double)((float)MathHelper.floor(commandspreadplayers$a.a) + 0.5F), (double)commandspreadplayers$a.a(worldserver), (double)MathHelper.floor(commandspreadplayers$a.b) + 0.5D);
            double d2 = Double.MAX_VALUE;

            for(CommandSpreadPlayers.a commandspreadplayers$a1 : acommandspreadplayers$a) {
                if (commandspreadplayers$a != commandspreadplayers$a1) {
                    double d1 = commandspreadplayers$a.a(commandspreadplayers$a1);
                    d2 = Math.min(d1, d2);
                }
            }

            d0 += d2;
        }

        if (collection.size() < 2) {
            return 0.0D;
        } else {
            d0 = d0 / (double)collection.size();
            return d0;
        }
    }

    private static CommandSpreadPlayers.a[] a(Random random, int i, double d0, double d1, double d2, double d3) {
        CommandSpreadPlayers.a[] acommandspreadplayers$a = new CommandSpreadPlayers.a[i];

        for(int j = 0; j < acommandspreadplayers$a.length; ++j) {
            CommandSpreadPlayers.a commandspreadplayers$a = new CommandSpreadPlayers.a();
            commandspreadplayers$a.a(random, d0, d1, d2, d3);
            acommandspreadplayers$a[j] = commandspreadplayers$a;
        }

        return acommandspreadplayers$a;
    }

    static class a {
        private double a;
        private double b;

        a() {
        }

        double a(CommandSpreadPlayers.a commandspreadplayers$a1) {
            double d0 = this.a - commandspreadplayers$a1.a;
            double d1 = this.b - commandspreadplayers$a1.b;
            return Math.sqrt(d0 * d0 + d1 * d1);
        }

        void a() {
            double d0 = (double)this.b();
            this.a /= d0;
            this.b /= d0;
        }

        float b() {
            return MathHelper.sqrt(this.a * this.a + this.b * this.b);
        }

        public void b(CommandSpreadPlayers.a commandspreadplayers$a1) {
            this.a -= commandspreadplayers$a1.a;
            this.b -= commandspreadplayers$a1.b;
        }

        public boolean a(double d0, double d1, double d2, double d3) {
            boolean flag = false;
            if (this.a < d0) {
                this.a = d0;
                flag = true;
            } else if (this.a > d2) {
                this.a = d2;
                flag = true;
            }

            if (this.b < d1) {
                this.b = d1;
                flag = true;
            } else if (this.b > d3) {
                this.b = d3;
                flag = true;
            }

            return flag;
        }

        public int a(IBlockAccess iblockaccess) {
            BlockPosition blockposition = new BlockPosition(this.a, 256.0D, this.b);

            while(blockposition.getY() > 0) {
                blockposition = blockposition.down();
                if (!iblockaccess.getType(blockposition).isAir()) {
                    return blockposition.getY() + 1;
                }
            }

            return 257;
        }

        public boolean b(IBlockAccess iblockaccess) {
            BlockPosition blockposition = new BlockPosition(this.a, 256.0D, this.b);

            while(blockposition.getY() > 0) {
                blockposition = blockposition.down();
                IBlockData iblockdata = iblockaccess.getType(blockposition);
                if (!iblockdata.isAir()) {
                    Material material = iblockdata.getMaterial();
                    return !material.isLiquid() && material != Material.FIRE;
                }
            }

            return false;
        }

        public void a(Random random, double d0, double d1, double d2, double d3) {
            this.a = MathHelper.a(random, d0, d2);
            this.b = MathHelper.a(random, d1, d3);
        }
    }
}
