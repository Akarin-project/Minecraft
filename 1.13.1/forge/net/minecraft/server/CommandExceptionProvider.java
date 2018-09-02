package net.minecraft.server;

import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class CommandExceptionProvider implements BuiltInExceptionProvider {
    private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.double.low", new Object[]{object1, object});
    });
    private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.double.big", new Object[]{object1, object});
    });
    private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.float.low", new Object[]{object1, object});
    });
    private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.float.big", new Object[]{object1, object});
    });
    private static final Dynamic2CommandExceptionType e = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.integer.low", new Object[]{object1, object});
    });
    private static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.integer.big", new Object[]{object1, object});
    });
    private static final DynamicCommandExceptionType g = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("argument.literal.incorrect", new Object[]{object});
    });
    private static final SimpleCommandExceptionType h = new SimpleCommandExceptionType(new ChatMessage("parsing.quote.expected.start", new Object[0]));
    private static final SimpleCommandExceptionType i = new SimpleCommandExceptionType(new ChatMessage("parsing.quote.expected.end", new Object[0]));
    private static final DynamicCommandExceptionType j = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("parsing.quote.escape", new Object[]{object});
    });
    private static final DynamicCommandExceptionType k = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("parsing.bool.invalid", new Object[]{object});
    });
    private static final DynamicCommandExceptionType l = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("parsing.int.invalid", new Object[]{object});
    });
    private static final SimpleCommandExceptionType m = new SimpleCommandExceptionType(new ChatMessage("parsing.int.expected", new Object[0]));
    private static final DynamicCommandExceptionType n = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("parsing.double.invalid", new Object[]{object});
    });
    private static final SimpleCommandExceptionType o = new SimpleCommandExceptionType(new ChatMessage("parsing.double.expected", new Object[0]));
    private static final DynamicCommandExceptionType p = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("parsing.float.invalid", new Object[]{object});
    });
    private static final SimpleCommandExceptionType q = new SimpleCommandExceptionType(new ChatMessage("parsing.float.expected", new Object[0]));
    private static final SimpleCommandExceptionType r = new SimpleCommandExceptionType(new ChatMessage("parsing.bool.expected", new Object[0]));
    private static final DynamicCommandExceptionType s = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("parsing.expected", new Object[]{object});
    });
    private static final SimpleCommandExceptionType t = new SimpleCommandExceptionType(new ChatMessage("command.unknown.command", new Object[0]));
    private static final SimpleCommandExceptionType u = new SimpleCommandExceptionType(new ChatMessage("command.unknown.argument", new Object[0]));
    private static final SimpleCommandExceptionType v = new SimpleCommandExceptionType(new ChatMessage("command.expected.separator", new Object[0]));
    private static final DynamicCommandExceptionType w = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("command.exception", new Object[]{object});
    });

    public CommandExceptionProvider() {
    }

    public Dynamic2CommandExceptionType doubleTooLow() {
        return a;
    }

    public Dynamic2CommandExceptionType doubleTooHigh() {
        return b;
    }

    public Dynamic2CommandExceptionType floatTooLow() {
        return c;
    }

    public Dynamic2CommandExceptionType floatTooHigh() {
        return d;
    }

    public Dynamic2CommandExceptionType integerTooLow() {
        return e;
    }

    public Dynamic2CommandExceptionType integerTooHigh() {
        return f;
    }

    public DynamicCommandExceptionType literalIncorrect() {
        return g;
    }

    public SimpleCommandExceptionType readerExpectedStartOfQuote() {
        return h;
    }

    public SimpleCommandExceptionType readerExpectedEndOfQuote() {
        return i;
    }

    public DynamicCommandExceptionType readerInvalidEscape() {
        return j;
    }

    public DynamicCommandExceptionType readerInvalidBool() {
        return k;
    }

    public DynamicCommandExceptionType readerInvalidInt() {
        return l;
    }

    public SimpleCommandExceptionType readerExpectedInt() {
        return m;
    }

    public DynamicCommandExceptionType readerInvalidDouble() {
        return n;
    }

    public SimpleCommandExceptionType readerExpectedDouble() {
        return o;
    }

    public DynamicCommandExceptionType readerInvalidFloat() {
        return p;
    }

    public SimpleCommandExceptionType readerExpectedFloat() {
        return q;
    }

    public SimpleCommandExceptionType readerExpectedBool() {
        return r;
    }

    public DynamicCommandExceptionType readerExpectedSymbol() {
        return s;
    }

    public SimpleCommandExceptionType dispatcherUnknownCommand() {
        return t;
    }

    public SimpleCommandExceptionType dispatcherUnknownArgument() {
        return u;
    }

    public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
        return v;
    }

    public DynamicCommandExceptionType dispatcherParseException() {
        return w;
    }
}
