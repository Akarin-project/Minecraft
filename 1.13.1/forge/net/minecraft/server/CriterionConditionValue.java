package net.minecraft.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public abstract class CriterionConditionValue<T extends Number> {
    public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("argument.range.empty", new Object[0]));
    public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("argument.range.swapped", new Object[0]));
    protected final T c;
    protected final T d;

    protected CriterionConditionValue(@Nullable T number, @Nullable T number1) {
        this.c = number;
        this.d = number1;
    }

    @Nullable
    public T a() {
        return this.c;
    }

    @Nullable
    public T b() {
        return this.d;
    }

    public boolean c() {
        return this.c == null && this.d == null;
    }

    public JsonElement d() {
        if (this.c()) {
            return JsonNull.INSTANCE;
        } else if (this.c != null && this.c.equals(this.d)) {
            return new JsonPrimitive(this.c);
        } else {
            JsonObject jsonobject = new JsonObject();
            if (this.c != null) {
                jsonobject.addProperty("min", this.c);
            }

            if (this.d != null) {
                jsonobject.addProperty("max", this.c);
            }

            return jsonobject;
        }
    }

    protected static <T extends Number, R extends CriterionConditionValue<T>> R a(@Nullable JsonElement jsonelement, R criterionconditionvalue, BiFunction<JsonElement, String, T> bifunction, CriterionConditionValue.a<T, R> criterionconditionvalue$a) {
        if (jsonelement != null && !jsonelement.isJsonNull()) {
            if (ChatDeserializer.b(jsonelement)) {
                Number number2 = (Number)bifunction.apply(jsonelement, "value");
                return (R)criterionconditionvalue$a.create(number2, number2);
            } else {
                JsonObject jsonobject = ChatDeserializer.m(jsonelement, "value");
                Number number = jsonobject.has("min") ? (Number)bifunction.apply(jsonobject.get("min"), "min") : null;
                Number number1 = jsonobject.has("max") ? (Number)bifunction.apply(jsonobject.get("max"), "max") : null;
                return (R)criterionconditionvalue$a.create(number, number1);
            }
        } else {
            return (R)criterionconditionvalue;
        }
    }

    protected static <T extends Number, R extends CriterionConditionValue<T>> R a(StringReader stringreader, CriterionConditionValue.b<T, R> criterionconditionvalue$b, Function<String, T> function, Supplier<DynamicCommandExceptionType> supplier, Function<T, T> function1) throws CommandSyntaxException {
        if (!stringreader.canRead()) {
            throw a.createWithContext(stringreader);
        } else {
            int i = stringreader.getCursor();

            try {
                Number number = (Number)a(a(stringreader, function, supplier), function1);
                Number number1;
                if (stringreader.canRead(2) && stringreader.peek() == '.' && stringreader.peek(1) == '.') {
                    stringreader.skip();
                    stringreader.skip();
                    number1 = (Number)a(a(stringreader, function, supplier), function1);
                    if (number == null && number1 == null) {
                        throw a.createWithContext(stringreader);
                    }
                } else {
                    number1 = number;
                }

                if (number == null && number1 == null) {
                    throw a.createWithContext(stringreader);
                } else {
                    return (R)criterionconditionvalue$b.create(stringreader, number, number1);
                }
            } catch (CommandSyntaxException commandsyntaxexception) {
                stringreader.setCursor(i);
                throw new CommandSyntaxException(commandsyntaxexception.getType(), commandsyntaxexception.getRawMessage(), commandsyntaxexception.getInput(), i);
            }
        }
    }

    @Nullable
    private static <T extends Number> T a(StringReader stringreader, Function<String, T> function, Supplier<DynamicCommandExceptionType> supplier) throws CommandSyntaxException {
        int i = stringreader.getCursor();

        while(stringreader.canRead() && a(stringreader)) {
            stringreader.skip();
        }

        String s = stringreader.getString().substring(i, stringreader.getCursor());
        if (s.isEmpty()) {
            return (T)null;
        } else {
            try {
                return (T)(function.apply(s));
            } catch (NumberFormatException var6) {
                throw ((DynamicCommandExceptionType)supplier.get()).createWithContext(stringreader, s);
            }
        }
    }

    private static boolean a(StringReader stringreader) {
        char c0 = stringreader.peek();
        if ((c0 < '0' || c0 > '9') && c0 != '-') {
            if (c0 != '.') {
                return false;
            } else {
                return !stringreader.canRead(2) || stringreader.peek(1) != '.';
            }
        } else {
            return true;
        }
    }

    @Nullable
    private static <T> T a(@Nullable T object, Function<T, T> function) {
        return (T)(object == null ? null : function.apply(object));
    }

    @FunctionalInterface
    public interface a<T extends Number, R extends CriterionConditionValue<T>> {
        R create(@Nullable T var1, @Nullable T var2);
    }

    @FunctionalInterface
    public interface b<T extends Number, R extends CriterionConditionValue<T>> {
        R create(StringReader var1, @Nullable T var2, @Nullable T var3) throws CommandSyntaxException;
    }

    public static class c extends CriterionConditionValue<Float> {
        public static final CriterionConditionValue.c e = new CriterionConditionValue.c((Float)null, (Float)null);
        private final Double f;
        private final Double g;

        private static CriterionConditionValue.c a(StringReader stringreader, @Nullable Float fx, @Nullable Float f1) throws CommandSyntaxException {
            if (fx != null && f1 != null && fx > f1) {
                throw b.createWithContext(stringreader);
            } else {
                return new CriterionConditionValue.c(fx, f1);
            }
        }

        @Nullable
        private static Double a(@Nullable Float fx) {
            return fx == null ? null : fx.doubleValue() * fx.doubleValue();
        }

        private c(@Nullable Float fx, @Nullable Float f1) {
            super(fx, f1);
            this.f = a(fx);
            this.g = a(f1);
        }

        public static CriterionConditionValue.c b(float fx) {
            return new CriterionConditionValue.c(fx, (Float)null);
        }

        public boolean d(float fx) {
            if (this.c != null && this.c > fx) {
                return false;
            } else {
                return this.d == null || !(this.d < fx);
            }
        }

        public boolean a(double d0) {
            if (this.f != null && this.f > d0) {
                return false;
            } else {
                return this.g == null || !(this.g < d0);
            }
        }

        public static CriterionConditionValue.c a(@Nullable JsonElement jsonelement) {
            return (CriterionConditionValue.c)a(jsonelement, e, ChatDeserializer::e, CriterionConditionValue.c::new);
        }

        public static CriterionConditionValue.c a(StringReader stringreader) throws CommandSyntaxException {
            return a(stringreader, (fx) -> {
                return fx;
            });
        }

        public static CriterionConditionValue.c a(StringReader stringreader, Function<Float, Float> function) throws CommandSyntaxException {
            return (CriterionConditionValue.c)a(stringreader, CriterionConditionValue.c::a, Float::parseFloat, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidFloat, function);
        }
    }

    public static class d extends CriterionConditionValue<Integer> {
        public static final CriterionConditionValue.d e = new CriterionConditionValue.d((Integer)null, (Integer)null);
        private final Long f;
        private final Long g;

        private static CriterionConditionValue.d a(StringReader stringreader, @Nullable Integer integer, @Nullable Integer integer1) throws CommandSyntaxException {
            if (integer != null && integer1 != null && integer > integer1) {
                throw b.createWithContext(stringreader);
            } else {
                return new CriterionConditionValue.d(integer, integer1);
            }
        }

        @Nullable
        private static Long a(@Nullable Integer integer) {
            return integer == null ? null : integer.longValue() * integer.longValue();
        }

        private d(@Nullable Integer integer, @Nullable Integer integer1) {
            super(integer, integer1);
            this.f = a(integer);
            this.g = a(integer1);
        }

        public static CriterionConditionValue.d a(int i) {
            return new CriterionConditionValue.d(i, i);
        }

        public static CriterionConditionValue.d b(int i) {
            return new CriterionConditionValue.d(i, (Integer)null);
        }

        public boolean d(int i) {
            if (this.c != null && this.c > i) {
                return false;
            } else {
                return this.d == null || this.d >= i;
            }
        }

        public static CriterionConditionValue.d a(@Nullable JsonElement jsonelement) {
            return (CriterionConditionValue.d)a(jsonelement, e, ChatDeserializer::g, CriterionConditionValue.d::new);
        }

        public static CriterionConditionValue.d a(StringReader stringreader) throws CommandSyntaxException {
            return a(stringreader, (integer) -> {
                return integer;
            });
        }

        public static CriterionConditionValue.d a(StringReader stringreader, Function<Integer, Integer> function) throws CommandSyntaxException {
            return (CriterionConditionValue.d)a(stringreader, CriterionConditionValue.d::a, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, function);
        }
    }
}
