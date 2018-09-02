package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

public interface ArgumentCriterionValue<T extends CriterionConditionValue<?>> extends ArgumentType<T> {
    static ArgumentCriterionValue.b a() {
        return new ArgumentCriterionValue.b();
    }

    public static class a implements ArgumentCriterionValue<CriterionConditionValue.c> {
        private static final Collection<String> a = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

        public a() {
        }

        public CriterionConditionValue.c a(StringReader stringreader) throws CommandSyntaxException {
            return CriterionConditionValue.c.a(stringreader);
        }

        public Collection<String> getExamples() {
            return a;
        }

        // $FF: synthetic method
        public Object parse(StringReader stringreader) throws CommandSyntaxException {
            return this.a(stringreader);
        }

        public static class a extends ArgumentCriterionValue.c<ArgumentCriterionValue.a> {
            public a() {
            }

            public ArgumentCriterionValue.a a(PacketDataSerializer var1) {
                return new ArgumentCriterionValue.a();
            }

            // $FF: synthetic method
            public ArgumentType b(PacketDataSerializer packetdataserializer) {
                return this.a(packetdataserializer);
            }
        }
    }

    public static class b implements ArgumentCriterionValue<CriterionConditionValue.d> {
        private static final Collection<String> a = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

        public b() {
        }

        public static CriterionConditionValue.d a(CommandContext<CommandListenerWrapper> commandcontext, String s) {
            return (CriterionConditionValue.d)commandcontext.getArgument(s, CriterionConditionValue.d.class);
        }

        public CriterionConditionValue.d a(StringReader stringreader) throws CommandSyntaxException {
            return CriterionConditionValue.d.a(stringreader);
        }

        public Collection<String> getExamples() {
            return a;
        }

        // $FF: synthetic method
        public Object parse(StringReader stringreader) throws CommandSyntaxException {
            return this.a(stringreader);
        }

        public static class a extends ArgumentCriterionValue.c<ArgumentCriterionValue.b> {
            public a() {
            }

            public ArgumentCriterionValue.b a(PacketDataSerializer var1) {
                return new ArgumentCriterionValue.b();
            }

            // $FF: synthetic method
            public ArgumentType b(PacketDataSerializer packetdataserializer) {
                return this.a(packetdataserializer);
            }
        }
    }

    public abstract static class c<T extends ArgumentCriterionValue<?>> implements ArgumentSerializer<T> {
        public c() {
        }

        public void a(T var1, PacketDataSerializer var2) {
        }

        public void a(T var1, JsonObject var2) {
        }
    }
}
