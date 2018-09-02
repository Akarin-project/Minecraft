package net.minecraft.server;

import com.google.common.collect.Maps;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CompletionProviders {
    private static final Map<MinecraftKey, SuggestionProvider<ICompletionProvider>> e = Maps.newHashMap();
    private static final MinecraftKey f = new MinecraftKey("minecraft:ask_server");
    public static final SuggestionProvider<ICompletionProvider> a = a(f, (commandcontext, suggestionsbuilder) -> {
        return ((ICompletionProvider)commandcontext.getSource()).a(commandcontext, suggestionsbuilder);
    });
    public static final SuggestionProvider<CommandListenerWrapper> b = a(new MinecraftKey("minecraft:all_recipes"), (commandcontext, suggestionsbuilder) -> {
        return ICompletionProvider.a(((ICompletionProvider)commandcontext.getSource()).o(), suggestionsbuilder);
    });
    public static final SuggestionProvider<CommandListenerWrapper> c = a(new MinecraftKey("minecraft:available_sounds"), (commandcontext, suggestionsbuilder) -> {
        return ICompletionProvider.a(((ICompletionProvider)commandcontext.getSource()).n(), suggestionsbuilder);
    });
    public static final SuggestionProvider<CommandListenerWrapper> d = a(new MinecraftKey("minecraft:summonable_entities"), (var0, suggestionsbuilder) -> {
        return ICompletionProvider.a(IRegistry.ENTITY_TYPE.f().filter(EntityTypes::b), suggestionsbuilder, EntityTypes::getName, (entitytypes) -> {
            return new ChatMessage(SystemUtils.a("entity", EntityTypes.getName(entitytypes)), new Object[0]);
        });
    });

    public static <S extends ICompletionProvider> SuggestionProvider<S> a(MinecraftKey minecraftkey, SuggestionProvider<ICompletionProvider> suggestionprovider) {
        if (e.containsKey(minecraftkey)) {
            throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + minecraftkey);
        } else {
            e.put(minecraftkey, suggestionprovider);
            return new CompletionProviders.a(minecraftkey, suggestionprovider);
        }
    }

    public static SuggestionProvider<ICompletionProvider> a(MinecraftKey minecraftkey) {
        return (SuggestionProvider)e.getOrDefault(minecraftkey, a);
    }

    public static MinecraftKey a(SuggestionProvider<ICompletionProvider> suggestionprovider) {
        return suggestionprovider instanceof CompletionProviders.a ? ((CompletionProviders.a)suggestionprovider).b : f;
    }

    public static SuggestionProvider<ICompletionProvider> b(SuggestionProvider<ICompletionProvider> suggestionprovider) {
        return suggestionprovider instanceof CompletionProviders.a ? suggestionprovider : a;
    }

    public static class a implements SuggestionProvider<ICompletionProvider> {
        private final SuggestionProvider<ICompletionProvider> a;
        private final MinecraftKey b;

        public a(MinecraftKey minecraftkey, SuggestionProvider<ICompletionProvider> suggestionprovider) {
            this.a = suggestionprovider;
            this.b = minecraftkey;
        }

        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ICompletionProvider> commandcontext, SuggestionsBuilder suggestionsbuilder) throws CommandSyntaxException {
            return this.a.getSuggestions(commandcontext, suggestionsbuilder);
        }
    }
}
