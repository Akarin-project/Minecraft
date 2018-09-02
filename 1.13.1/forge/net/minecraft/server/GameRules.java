package net.minecraft.server;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class GameRules {
    private static final TreeMap<String, GameRules.b> a = (TreeMap)SystemUtils.a(new TreeMap(), (treemap) -> {
        treemap.put("doFireTick", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("mobGriefing", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("keepInventory", new GameRules.b("false", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("doMobSpawning", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("doMobLoot", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("doTileDrops", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("doEntityDrops", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("commandBlockOutput", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("naturalRegeneration", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("doDaylightCycle", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("logAdminCommands", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("showDeathMessages", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("randomTickSpeed", new GameRules.b("3", GameRules.EnumGameRuleType.NUMERICAL_VALUE));
        treemap.put("sendCommandFeedback", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("reducedDebugInfo", new GameRules.b("false", GameRules.EnumGameRuleType.BOOLEAN_VALUE, (minecraftserver, gamerules$gamerulevalue) -> {
            int i = gamerules$gamerulevalue.b() ? 22 : 23;

            for(EntityPlayer entityplayer : minecraftserver.getPlayerList().v()) {
                entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityStatus(entityplayer, (byte)i));
            }

        }));
        treemap.put("spectatorsGenerateChunks", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("spawnRadius", new GameRules.b("10", GameRules.EnumGameRuleType.NUMERICAL_VALUE));
        treemap.put("disableElytraMovementCheck", new GameRules.b("false", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("maxEntityCramming", new GameRules.b("24", GameRules.EnumGameRuleType.NUMERICAL_VALUE));
        treemap.put("doWeatherCycle", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("doLimitedCrafting", new GameRules.b("false", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
        treemap.put("maxCommandChainLength", new GameRules.b("65536", GameRules.EnumGameRuleType.NUMERICAL_VALUE));
        treemap.put("announceAdvancements", new GameRules.b("true", GameRules.EnumGameRuleType.BOOLEAN_VALUE));
    });
    private final TreeMap<String, GameRules.GameRuleValue> b = new TreeMap();

    public GameRules() {
        for(Entry entry : a.entrySet()) {
            this.b.put(entry.getKey(), ((GameRules.b)entry.getValue()).a());
        }

    }

    public void set(String s, String s1, @Nullable MinecraftServer minecraftserver) {
        GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.b.get(s);
        if (gamerules$gamerulevalue != null) {
            gamerules$gamerulevalue.a(s1, minecraftserver);
        }

    }

    public boolean getBoolean(String s) {
        GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.b.get(s);
        return gamerules$gamerulevalue != null ? gamerules$gamerulevalue.b() : false;
    }

    public int c(String s) {
        GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.b.get(s);
        return gamerules$gamerulevalue != null ? gamerules$gamerulevalue.c() : 0;
    }

    public NBTTagCompound a() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        for(String s : this.b.keySet()) {
            GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.b.get(s);
            nbttagcompound.setString(s, gamerules$gamerulevalue.a());
        }

        return nbttagcompound;
    }

    public void a(NBTTagCompound nbttagcompound) {
        for(String s : nbttagcompound.getKeys()) {
            this.set(s, nbttagcompound.getString(s), (MinecraftServer)null);
        }

    }

    public GameRules.GameRuleValue get(String s) {
        return (GameRules.GameRuleValue)this.b.get(s);
    }

    public static TreeMap<String, GameRules.b> getGameRules() {
        return a;
    }

    public static enum EnumGameRuleType {
        ANY_VALUE(StringArgumentType::greedyString, (commandcontext, s) -> {
            return (String)commandcontext.getArgument(s, String.class);
        }),
        BOOLEAN_VALUE(BoolArgumentType::bool, (commandcontext, s) -> {
            return ((Boolean)commandcontext.getArgument(s, Boolean.class)).toString();
        }),
        NUMERICAL_VALUE(IntegerArgumentType::integer, (commandcontext, s) -> {
            return ((Integer)commandcontext.getArgument(s, Integer.class)).toString();
        });

        private final Supplier<ArgumentType<?>> d;
        private final BiFunction<CommandContext<CommandListenerWrapper>, String, String> e;

        private EnumGameRuleType(Supplier supplier, BiFunction bifunction) {
            this.d = supplier;
            this.e = bifunction;
        }

        public RequiredArgumentBuilder<CommandListenerWrapper, ?> a(String s) {
            return CommandDispatcher.a(s, (ArgumentType)this.d.get());
        }

        public void a(CommandContext<CommandListenerWrapper> commandcontext, String s, GameRules.GameRuleValue gamerules$gamerulevalue) {
            gamerules$gamerulevalue.a((String)this.e.apply(commandcontext, s), ((CommandListenerWrapper)commandcontext.getSource()).getServer());
        }
    }

    public static class GameRuleValue {
        private String a;
        private boolean b;
        private int c;
        private double d;
        private final GameRules.EnumGameRuleType e;
        private final BiConsumer<MinecraftServer, GameRules.GameRuleValue> f;

        public GameRuleValue(String s, GameRules.EnumGameRuleType gamerules$enumgameruletype, BiConsumer<MinecraftServer, GameRules.GameRuleValue> biconsumer) {
            this.e = gamerules$enumgameruletype;
            this.f = biconsumer;
            this.a(s, (MinecraftServer)null);
        }

        public void a(String s, @Nullable MinecraftServer minecraftserver) {
            this.a = s;
            this.b = Boolean.parseBoolean(s);
            this.c = this.b ? 1 : 0;

            try {
                this.c = Integer.parseInt(s);
            } catch (NumberFormatException var5) {
                ;
            }

            try {
                this.d = Double.parseDouble(s);
            } catch (NumberFormatException var4) {
                ;
            }

            if (minecraftserver != null) {
                this.f.accept(minecraftserver, this);
            }

        }

        public String a() {
            return this.a;
        }

        public boolean b() {
            return this.b;
        }

        public int c() {
            return this.c;
        }

        public GameRules.EnumGameRuleType getType() {
            return this.e;
        }
    }

    public static class b {
        private final GameRules.EnumGameRuleType a;
        private final String b;
        private final BiConsumer<MinecraftServer, GameRules.GameRuleValue> c;

        public b(String s, GameRules.EnumGameRuleType gamerules$enumgameruletype) {
            this(s, gamerules$enumgameruletype, (var0, var1) -> {
            });
        }

        public b(String s, GameRules.EnumGameRuleType gamerules$enumgameruletype, BiConsumer<MinecraftServer, GameRules.GameRuleValue> biconsumer) {
            this.a = gamerules$enumgameruletype;
            this.b = s;
            this.c = biconsumer;
        }

        public GameRules.GameRuleValue a() {
            return new GameRules.GameRuleValue(this.b, this.a, this.c);
        }

        public GameRules.EnumGameRuleType b() {
            return this.a;
        }
    }
}
