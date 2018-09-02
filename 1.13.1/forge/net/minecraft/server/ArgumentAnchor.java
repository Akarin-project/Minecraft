package net.minecraft.server;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class ArgumentAnchor implements ArgumentType<ArgumentAnchor.Anchor> {
    private static final Collection<String> a = Arrays.asList("eyes", "feet");
    private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("argument.anchor.invalid", new Object[]{object});
    });

    public ArgumentAnchor() {
    }

    public static ArgumentAnchor.Anchor a(CommandContext<CommandListenerWrapper> commandcontext, String s) {
        return (ArgumentAnchor.Anchor)commandcontext.getArgument(s, ArgumentAnchor.Anchor.class);
    }

    public static ArgumentAnchor a() {
        return new ArgumentAnchor();
    }

    public ArgumentAnchor.Anchor a(StringReader stringreader) throws CommandSyntaxException {
        int i = stringreader.getCursor();
        String s = stringreader.readUnquotedString();
        ArgumentAnchor.Anchor argumentanchor$anchor = ArgumentAnchor.Anchor.a(s);
        if (argumentanchor$anchor == null) {
            stringreader.setCursor(i);
            throw b.createWithContext(stringreader, s);
        } else {
            return argumentanchor$anchor;
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder suggestionsbuilder) {
        return ICompletionProvider.b(ArgumentAnchor.Anchor.c.keySet(), suggestionsbuilder);
    }

    public Collection<String> getExamples() {
        return a;
    }

    // $FF: synthetic method
    public Object parse(StringReader stringreader) throws CommandSyntaxException {
        return this.a(stringreader);
    }

    public static enum Anchor {
        FEET("feet", (vec3d, var1) -> {
            return vec3d;
        }),
        EYES("eyes", (vec3d, entity) -> {
            return new Vec3D(vec3d.x, vec3d.y + (double)entity.getHeadHeight(), vec3d.z);
        });

        private static final Map<String, ArgumentAnchor.Anchor> c = (Map)SystemUtils.a(Maps.newHashMap(), (hashmap) -> {
            for(ArgumentAnchor.Anchor argumentanchor$anchor : values()) {
                hashmap.put(argumentanchor$anchor.d, argumentanchor$anchor);
            }

        });
        private final String d;
        private final BiFunction<Vec3D, Entity, Vec3D> e;

        private Anchor(String s1, BiFunction bifunction) {
            this.d = s1;
            this.e = bifunction;
        }

        @Nullable
        public static ArgumentAnchor.Anchor a(String s) {
            return (ArgumentAnchor.Anchor)c.get(s);
        }

        public Vec3D a(Entity entity) {
            return (Vec3D)this.e.apply(new Vec3D(entity.locX, entity.locY, entity.locZ), entity);
        }

        public Vec3D a(CommandListenerWrapper commandlistenerwrapper) {
            Entity entity = commandlistenerwrapper.f();
            return entity == null ? commandlistenerwrapper.getPosition() : (Vec3D)this.e.apply(commandlistenerwrapper.getPosition(), entity);
        }
    }
}
