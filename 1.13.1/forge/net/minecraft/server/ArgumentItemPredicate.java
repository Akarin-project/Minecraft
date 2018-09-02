package net.minecraft.server;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ArgumentItemPredicate implements ArgumentType<ArgumentItemPredicate.b> {
    private static final Collection<String> a = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
    private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("arguments.item.tag.unknown", new Object[]{object});
    });

    public ArgumentItemPredicate() {
    }

    public static ArgumentItemPredicate a() {
        return new ArgumentItemPredicate();
    }

    public ArgumentItemPredicate.b a(StringReader stringreader) throws CommandSyntaxException {
        ArgumentParserItemStack argumentparseritemstack = (new ArgumentParserItemStack(stringreader, true)).h();
        if (argumentparseritemstack.b() != null) {
            ArgumentItemPredicate.a argumentitempredicate$a = new ArgumentItemPredicate.a(argumentparseritemstack.b(), argumentparseritemstack.c());
            return (var1) -> {
                return argumentitempredicate$a;
            };
        } else {
            MinecraftKey minecraftkey = argumentparseritemstack.d();
            return (commandcontext) -> {
                Tag tag = ((CommandListenerWrapper)commandcontext.getSource()).getServer().getTagRegistry().b().a(minecraftkey);
                if (tag == null) {
                    throw b.create(minecraftkey.toString());
                } else {
                    return new ArgumentItemPredicate.c(tag, argumentparseritemstack.c());
                }
            };
        }
    }

    public static Predicate<ItemStack> a(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
        return ((ArgumentItemPredicate.b)commandcontext.getArgument(s, ArgumentItemPredicate.b.class)).create(commandcontext);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder suggestionsbuilder) {
        StringReader stringreader = new StringReader(suggestionsbuilder.getInput());
        stringreader.setCursor(suggestionsbuilder.getStart());
        ArgumentParserItemStack argumentparseritemstack = new ArgumentParserItemStack(stringreader, true);

        try {
            argumentparseritemstack.h();
        } catch (CommandSyntaxException var6) {
            ;
        }

        return argumentparseritemstack.a(suggestionsbuilder);
    }

    public Collection<String> getExamples() {
        return a;
    }

    // $FF: synthetic method
    public Object parse(StringReader stringreader) throws CommandSyntaxException {
        return this.a(stringreader);
    }

    static class a implements Predicate<ItemStack> {
        private final Item a;
        @Nullable
        private final NBTTagCompound b;

        public a(Item item, @Nullable NBTTagCompound nbttagcompound) {
            this.a = item;
            this.b = nbttagcompound;
        }

        public boolean a(ItemStack itemstack) {
            return itemstack.getItem() == this.a && GameProfileSerializer.a(this.b, itemstack.getTag(), true);
        }

        // $FF: synthetic method
        public boolean test(Object object) {
            return this.a((ItemStack)object);
        }
    }

    public interface b {
        Predicate<ItemStack> create(CommandContext<CommandListenerWrapper> var1) throws CommandSyntaxException;
    }

    static class c implements Predicate<ItemStack> {
        private final Tag<Item> a;
        @Nullable
        private final NBTTagCompound b;

        public c(Tag<Item> tag, @Nullable NBTTagCompound nbttagcompound) {
            this.a = tag;
            this.b = nbttagcompound;
        }

        public boolean a(ItemStack itemstack) {
            return this.a.isTagged(itemstack.getItem()) && GameProfileSerializer.a(this.b, itemstack.getTag(), true);
        }

        // $FF: synthetic method
        public boolean test(Object object) {
            return this.a((ItemStack)object);
        }
    }
}
