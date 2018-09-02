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

public class ArgumentInventorySlot implements ArgumentType<Integer> {
    private static final Collection<String> a = Arrays.asList("container.5", "12", "weapon");
    private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("slot.unknown", new Object[]{object});
    });
    private static final Map<String, Integer> c = (Map)SystemUtils.a(Maps.newHashMap(), (hashmap) -> {
        for(int i = 0; i < 54; ++i) {
            hashmap.put("container." + i, i);
        }

        for(int j = 0; j < 9; ++j) {
            hashmap.put("hotbar." + j, j);
        }

        for(int k = 0; k < 27; ++k) {
            hashmap.put("inventory." + k, 9 + k);
        }

        for(int l = 0; l < 27; ++l) {
            hashmap.put("enderchest." + l, 200 + l);
        }

        for(int i1 = 0; i1 < 8; ++i1) {
            hashmap.put("villager." + i1, 300 + i1);
        }

        for(int j1 = 0; j1 < 15; ++j1) {
            hashmap.put("horse." + j1, 500 + j1);
        }

        hashmap.put("weapon", 98);
        hashmap.put("weapon.mainhand", 98);
        hashmap.put("weapon.offhand", 99);
        hashmap.put("armor.head", 100 + EnumItemSlot.HEAD.b());
        hashmap.put("armor.chest", 100 + EnumItemSlot.CHEST.b());
        hashmap.put("armor.legs", 100 + EnumItemSlot.LEGS.b());
        hashmap.put("armor.feet", 100 + EnumItemSlot.FEET.b());
        hashmap.put("horse.saddle", 400);
        hashmap.put("horse.armor", 401);
        hashmap.put("horse.chest", 499);
    });

    public ArgumentInventorySlot() {
    }

    public static ArgumentInventorySlot a() {
        return new ArgumentInventorySlot();
    }

    public static int a(CommandContext<CommandListenerWrapper> commandcontext, String s) {
        return commandcontext.getArgument(s, Integer.class);
    }

    public Integer a(StringReader stringreader) throws CommandSyntaxException {
        String s = stringreader.readUnquotedString();
        if (!c.containsKey(s)) {
            throw b.create(s);
        } else {
            return (Integer)c.get(s);
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder suggestionsbuilder) {
        return ICompletionProvider.b(c.keySet(), suggestionsbuilder);
    }

    public Collection<String> getExamples() {
        return a;
    }

    // $FF: synthetic method
    public Object parse(StringReader stringreader) throws CommandSyntaxException {
        return this.a(stringreader);
    }
}
