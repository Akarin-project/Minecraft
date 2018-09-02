package net.minecraft.server;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ICompletionProvider {
    Collection<String> l();

    default Collection<String> p() {
        return Collections.emptyList();
    }

    Collection<String> m();

    Collection<MinecraftKey> n();

    Collection<MinecraftKey> o();

    CompletableFuture<Suggestions> a(CommandContext<ICompletionProvider> var1, SuggestionsBuilder var2);

    Collection<ICompletionProvider.a> a(boolean var1);

    boolean hasPermission(int var1);

    static <T> void a(Iterable<T> iterable, String s, Function<T, MinecraftKey> function, Consumer<T> consumer) {
        boolean flag = s.indexOf(58) > -1;

        for(Object object : iterable) {
            MinecraftKey minecraftkey = (MinecraftKey)function.apply(object);
            if (flag) {
                String s1 = minecraftkey.toString();
                if (s1.startsWith(s)) {
                    consumer.accept(object);
                }
            } else if (minecraftkey.b().startsWith(s) || minecraftkey.b().equals("minecraft") && minecraftkey.getKey().startsWith(s)) {
                consumer.accept(object);
            }
        }

    }

    static <T> void a(Iterable<T> iterable, String s, String s1, Function<T, MinecraftKey> function, Consumer<T> consumer) {
        if (s.isEmpty()) {
            iterable.forEach(consumer);
        } else {
            String s2 = Strings.commonPrefix(s, s1);
            if (!s2.isEmpty()) {
                String s3 = s.substring(s2.length());
                a(iterable, s3, function, consumer);
            }
        }

    }

    static CompletableFuture<Suggestions> a(Iterable<MinecraftKey> iterable, SuggestionsBuilder suggestionsbuilder, String s) {
        String s1 = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);
        a(iterable, s1, s, (minecraftkey) -> {
            return minecraftkey;
        }, (minecraftkey) -> {
            suggestionsbuilder.suggest(s + minecraftkey);
        });
        return suggestionsbuilder.buildFuture();
    }

    static CompletableFuture<Suggestions> a(Iterable<MinecraftKey> iterable, SuggestionsBuilder suggestionsbuilder) {
        String s = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);
        a(iterable, s, (minecraftkey) -> {
            return minecraftkey;
        }, (minecraftkey) -> {
            suggestionsbuilder.suggest(minecraftkey.toString());
        });
        return suggestionsbuilder.buildFuture();
    }

    static <T> CompletableFuture<Suggestions> a(Iterable<T> iterable, SuggestionsBuilder suggestionsbuilder, Function<T, MinecraftKey> function, Function<T, Message> function1) {
        String s = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);
        a(iterable, s, function, (object) -> {
            suggestionsbuilder.suggest(((MinecraftKey)function.apply(object)).toString(), (Message)function1.apply(object));
        });
        return suggestionsbuilder.buildFuture();
    }

    static CompletableFuture<Suggestions> a(Stream<MinecraftKey> stream, SuggestionsBuilder suggestionsbuilder) {
        return a(stream::iterator, suggestionsbuilder);
    }

    static <T> CompletableFuture<Suggestions> a(Stream<T> stream, SuggestionsBuilder suggestionsbuilder, Function<T, MinecraftKey> function, Function<T, Message> function1) {
        return a(stream::iterator, suggestionsbuilder, function, function1);
    }

    static CompletableFuture<Suggestions> a(String s, Collection<ICompletionProvider.a> collection, SuggestionsBuilder suggestionsbuilder, Predicate<String> predicate) {
        ArrayList arraylist = Lists.newArrayList();
        if (Strings.isNullOrEmpty(s)) {
            for(ICompletionProvider.a icompletionprovider$a : collection) {
                String s1 = icompletionprovider$a.c + " " + icompletionprovider$a.d + " " + icompletionprovider$a.e;
                if (predicate.test(s1)) {
                    arraylist.add(icompletionprovider$a.c);
                    arraylist.add(icompletionprovider$a.c + " " + icompletionprovider$a.d);
                    arraylist.add(s1);
                }
            }
        } else {
            String[] astring = s.split(" ");
            if (astring.length == 1) {
                for(ICompletionProvider.a icompletionprovider$a1 : collection) {
                    String s2 = astring[0] + " " + icompletionprovider$a1.d + " " + icompletionprovider$a1.e;
                    if (predicate.test(s2)) {
                        arraylist.add(astring[0] + " " + icompletionprovider$a1.d);
                        arraylist.add(s2);
                    }
                }
            } else if (astring.length == 2) {
                for(ICompletionProvider.a icompletionprovider$a2 : collection) {
                    String s3 = astring[0] + " " + astring[1] + " " + icompletionprovider$a2.e;
                    if (predicate.test(s3)) {
                        arraylist.add(s3);
                    }
                }
            }
        }

        return b(arraylist, suggestionsbuilder);
    }

    static CompletableFuture<Suggestions> b(String s, Collection<ICompletionProvider.a> collection, SuggestionsBuilder suggestionsbuilder, Predicate<String> predicate) {
        ArrayList arraylist = Lists.newArrayList();
        if (Strings.isNullOrEmpty(s)) {
            for(ICompletionProvider.a icompletionprovider$a : collection) {
                String s1 = icompletionprovider$a.c + " " + icompletionprovider$a.e;
                if (predicate.test(s1)) {
                    arraylist.add(icompletionprovider$a.c);
                    arraylist.add(s1);
                }
            }
        } else {
            String[] astring = s.split(" ");
            if (astring.length == 1) {
                for(ICompletionProvider.a icompletionprovider$a1 : collection) {
                    String s2 = astring[0] + " " + icompletionprovider$a1.e;
                    if (predicate.test(s2)) {
                        arraylist.add(s2);
                    }
                }
            }
        }

        return b(arraylist, suggestionsbuilder);
    }

    static CompletableFuture<Suggestions> b(Iterable<String> iterable, SuggestionsBuilder suggestionsbuilder) {
        String s = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);

        for(String s1 : iterable) {
            if (s1.toLowerCase(Locale.ROOT).startsWith(s)) {
                suggestionsbuilder.suggest(s1);
            }
        }

        return suggestionsbuilder.buildFuture();
    }

    static CompletableFuture<Suggestions> b(Stream<String> stream, SuggestionsBuilder suggestionsbuilder) {
        String s = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);
        stream.filter((s2) -> {
            return s2.toLowerCase(Locale.ROOT).startsWith(s);
        }).forEach(suggestionsbuilder::suggest);
        return suggestionsbuilder.buildFuture();
    }

    static CompletableFuture<Suggestions> a(String[] astring, SuggestionsBuilder suggestionsbuilder) {
        String s = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);

        for(String s1 : astring) {
            if (s1.toLowerCase(Locale.ROOT).startsWith(s)) {
                suggestionsbuilder.suggest(s1);
            }
        }

        return suggestionsbuilder.buildFuture();
    }

    public static class a {
        public static final ICompletionProvider.a a = new ICompletionProvider.a("^", "^", "^");
        public static final ICompletionProvider.a b = new ICompletionProvider.a("~", "~", "~");
        public final String c;
        public final String d;
        public final String e;

        public a(String s, String s1, String s2) {
            this.c = s;
            this.d = s1;
            this.e = s2;
        }
    }
}
