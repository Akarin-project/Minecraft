package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;

public class ArgumentBlock {
    public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("argument.block.tag.disallowed", new Object[0]));
    public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("argument.block.id.invalid", new Object[]{object});
    });
    public static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.block.property.unknown", new Object[]{object, object1});
    });
    public static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.block.property.duplicate", new Object[]{object1, object});
    });
    public static final Dynamic3CommandExceptionType e = new Dynamic3CommandExceptionType((object, object1, object2) -> {
        return new ChatMessage("argument.block.property.invalid", new Object[]{object, object2, object1});
    });
    public static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("argument.block.property.novalue", new Object[]{object, object1});
    });
    public static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new ChatMessage("argument.block.property.unclosed", new Object[0]));
    private static final Function<SuggestionsBuilder, CompletableFuture<Suggestions>> h = SuggestionsBuilder::buildFuture;
    private final StringReader i;
    private final boolean j;
    private final Map<IBlockState<?>, Comparable<?>> k = Maps.newHashMap();
    private final Map<String, String> l = Maps.newHashMap();
    private MinecraftKey m = new MinecraftKey("");
    private BlockStateList<Block, IBlockData> n;
    private IBlockData o;
    @Nullable
    private NBTTagCompound p;
    private MinecraftKey q = new MinecraftKey("");
    private int r;
    private Function<SuggestionsBuilder, CompletableFuture<Suggestions>> s;

    public ArgumentBlock(StringReader stringreader, boolean flag) {
        this.s = h;
        this.i = stringreader;
        this.j = flag;
    }

    public Map<IBlockState<?>, Comparable<?>> a() {
        return this.k;
    }

    @Nullable
    public IBlockData b() {
        return this.o;
    }

    @Nullable
    public NBTTagCompound c() {
        return this.p;
    }

    @Nullable
    public MinecraftKey d() {
        return this.q;
    }

    public ArgumentBlock a(boolean flag) throws CommandSyntaxException {
        this.s = this::l;
        if (this.i.canRead() && this.i.peek() == '#') {
            this.f();
            this.s = this::i;
            if (this.i.canRead() && this.i.peek() == '[') {
                this.h();
                this.s = this::f;
            }
        } else {
            this.e();
            this.s = this::j;
            if (this.i.canRead() && this.i.peek() == '[') {
                this.g();
                this.s = this::f;
            }
        }

        if (flag && this.i.canRead() && this.i.peek() == '{') {
            this.s = h;
            this.i();
        }

        return this;
    }

    private CompletableFuture<Suggestions> b(SuggestionsBuilder suggestionsbuilder) {
        if (suggestionsbuilder.getRemaining().isEmpty()) {
            suggestionsbuilder.suggest(String.valueOf(']'));
        }

        return this.d(suggestionsbuilder);
    }

    private CompletableFuture<Suggestions> c(SuggestionsBuilder suggestionsbuilder) {
        if (suggestionsbuilder.getRemaining().isEmpty()) {
            suggestionsbuilder.suggest(String.valueOf(']'));
        }

        return this.e(suggestionsbuilder);
    }

    private CompletableFuture<Suggestions> d(SuggestionsBuilder suggestionsbuilder) {
        String sx = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);

        for(IBlockState iblockstate : this.o.a()) {
            if (!this.k.containsKey(iblockstate) && iblockstate.a().startsWith(sx)) {
                suggestionsbuilder.suggest(iblockstate.a() + '=');
            }
        }

        return suggestionsbuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> e(SuggestionsBuilder suggestionsbuilder) {
        String sx = suggestionsbuilder.getRemaining().toLowerCase(Locale.ROOT);
        if (this.q != null && !this.q.getKey().isEmpty()) {
            Tag tag = TagsBlock.a().a(this.q);
            if (tag != null) {
                for(Block block : tag.a()) {
                    for(IBlockState iblockstate : block.getStates().d()) {
                        if (!this.l.containsKey(iblockstate.a()) && iblockstate.a().startsWith(sx)) {
                            suggestionsbuilder.suggest(iblockstate.a() + '=');
                        }
                    }
                }
            }
        }

        return suggestionsbuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> f(SuggestionsBuilder suggestionsbuilder) {
        if (suggestionsbuilder.getRemaining().isEmpty() && this.k()) {
            suggestionsbuilder.suggest(String.valueOf('{'));
        }

        return suggestionsbuilder.buildFuture();
    }

    private boolean k() {
        if (this.o != null) {
            return this.o.getBlock().isTileEntity();
        } else {
            if (this.q != null) {
                Tag tag = TagsBlock.a().a(this.q);
                if (tag != null) {
                    for(Block block : tag.a()) {
                        if (block.isTileEntity()) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    private CompletableFuture<Suggestions> g(SuggestionsBuilder suggestionsbuilder) {
        if (suggestionsbuilder.getRemaining().isEmpty()) {
            suggestionsbuilder.suggest(String.valueOf('='));
        }

        return suggestionsbuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> h(SuggestionsBuilder suggestionsbuilder) {
        if (suggestionsbuilder.getRemaining().isEmpty()) {
            suggestionsbuilder.suggest(String.valueOf(']'));
        }

        if (suggestionsbuilder.getRemaining().isEmpty() && this.k.size() < this.o.a().size()) {
            suggestionsbuilder.suggest(String.valueOf(','));
        }

        return suggestionsbuilder.buildFuture();
    }

    private static <T extends Comparable<T>> SuggestionsBuilder a(SuggestionsBuilder suggestionsbuilder, IBlockState<T> iblockstate) {
        for(Comparable comparable : iblockstate.d()) {
            if (comparable instanceof Integer) {
                suggestionsbuilder.suggest(comparable);
            } else {
                suggestionsbuilder.suggest(iblockstate.a(comparable));
            }
        }

        return suggestionsbuilder;
    }

    private CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsbuilder, String sx) {
        boolean flag = false;
        if (this.q != null && !this.q.getKey().isEmpty()) {
            Tag tag = TagsBlock.a().a(this.q);
            if (tag != null) {
                label40:
                for(Block block : tag.a()) {
                    IBlockState iblockstate = block.getStates().a(sx);
                    if (iblockstate != null) {
                        a(suggestionsbuilder, iblockstate);
                    }

                    if (!flag) {
                        Iterator iterator = block.getStates().d().iterator();

                        while(true) {
                            if (!iterator.hasNext()) {
                                continue label40;
                            }

                            IBlockState iblockstate1 = (IBlockState)iterator.next();
                            if (!this.l.containsKey(iblockstate1.a())) {
                                break;
                            }
                        }

                        flag = true;
                    }
                }
            }
        }

        if (flag) {
            suggestionsbuilder.suggest(String.valueOf(','));
        }

        suggestionsbuilder.suggest(String.valueOf(']'));
        return suggestionsbuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> i(SuggestionsBuilder suggestionsbuilder) {
        if (suggestionsbuilder.getRemaining().isEmpty()) {
            Tag tag = TagsBlock.a().a(this.q);
            if (tag != null) {
                boolean flag = false;
                boolean flag1 = false;

                for(Block block : tag.a()) {
                    flag |= !block.getStates().d().isEmpty();
                    flag1 |= block.isTileEntity();
                    if (flag && flag1) {
                        break;
                    }
                }

                if (flag) {
                    suggestionsbuilder.suggest(String.valueOf('['));
                }

                if (flag1) {
                    suggestionsbuilder.suggest(String.valueOf('{'));
                }
            }
        }

        return this.k(suggestionsbuilder);
    }

    private CompletableFuture<Suggestions> j(SuggestionsBuilder suggestionsbuilder) {
        if (suggestionsbuilder.getRemaining().isEmpty()) {
            if (!this.o.getBlock().getStates().d().isEmpty()) {
                suggestionsbuilder.suggest(String.valueOf('['));
            }

            if (this.o.getBlock().isTileEntity()) {
                suggestionsbuilder.suggest(String.valueOf('{'));
            }
        }

        return suggestionsbuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> k(SuggestionsBuilder suggestionsbuilder) {
        return ICompletionProvider.a(TagsBlock.a().a(), suggestionsbuilder.createOffset(this.r).add(suggestionsbuilder));
    }

    private CompletableFuture<Suggestions> l(SuggestionsBuilder suggestionsbuilder) {
        if (this.j) {
            ICompletionProvider.a(TagsBlock.a().a(), suggestionsbuilder, String.valueOf('#'));
        }

        ICompletionProvider.a(IRegistry.BLOCK.keySet(), suggestionsbuilder);
        return suggestionsbuilder.buildFuture();
    }

    public void e() throws CommandSyntaxException {
        int ix = this.i.getCursor();
        this.m = MinecraftKey.a(this.i);
        if (IRegistry.BLOCK.c(this.m)) {
            Block block = IRegistry.BLOCK.getOrDefault(this.m);
            this.n = block.getStates();
            this.o = block.getBlockData();
        } else {
            this.i.setCursor(ix);
            throw b.createWithContext(this.i, this.m.toString());
        }
    }

    public void f() throws CommandSyntaxException {
        if (!this.j) {
            throw a.create();
        } else {
            this.s = this::k;
            this.i.expect('#');
            this.r = this.i.getCursor();
            this.q = MinecraftKey.a(this.i);
        }
    }

    public void g() throws CommandSyntaxException {
        this.i.skip();
        this.s = this::b;
        this.i.skipWhitespace();

        while(true) {
            if (this.i.canRead() && this.i.peek() != ']') {
                this.i.skipWhitespace();
                int ix = this.i.getCursor();
                String sx = this.i.readString();
                IBlockState iblockstate = this.n.a(sx);
                if (iblockstate == null) {
                    this.i.setCursor(ix);
                    throw c.createWithContext(this.i, this.m.toString(), sx);
                }

                if (this.k.containsKey(iblockstate)) {
                    this.i.setCursor(ix);
                    throw d.createWithContext(this.i, this.m.toString(), sx);
                }

                this.i.skipWhitespace();
                this.s = this::g;
                if (!this.i.canRead() || this.i.peek() != '=') {
                    throw f.createWithContext(this.i, this.m.toString(), sx);
                }

                this.i.skip();
                this.i.skipWhitespace();
                this.s = (suggestionsbuilder) -> {
                    return a(suggestionsbuilder, iblockstate).buildFuture();
                };
                int jx = this.i.getCursor();
                this.a(iblockstate, this.i.readString(), jx);
                this.s = this::h;
                this.i.skipWhitespace();
                if (!this.i.canRead()) {
                    continue;
                }

                if (this.i.peek() == ',') {
                    this.i.skip();
                    this.s = this::d;
                    continue;
                }

                if (this.i.peek() != ']') {
                    throw g.createWithContext(this.i);
                }
            }

            if (this.i.canRead()) {
                this.i.skip();
                return;
            }

            throw g.createWithContext(this.i);
        }
    }

    public void h() throws CommandSyntaxException {
        this.i.skip();
        this.s = this::c;
        int ix = -1;
        this.i.skipWhitespace();

        while(true) {
            if (this.i.canRead() && this.i.peek() != ']') {
                this.i.skipWhitespace();
                int jx = this.i.getCursor();
                String sx = this.i.readString();
                if (this.l.containsKey(sx)) {
                    this.i.setCursor(jx);
                    throw d.createWithContext(this.i, this.m.toString(), sx);
                }

                this.i.skipWhitespace();
                if (!this.i.canRead() || this.i.peek() != '=') {
                    this.i.setCursor(jx);
                    throw f.createWithContext(this.i, this.m.toString(), sx);
                }

                this.i.skip();
                this.i.skipWhitespace();
                this.s = (suggestionsbuilder) -> {
                    return this.a(suggestionsbuilder, sx);
                };
                ix = this.i.getCursor();
                String s1 = this.i.readString();
                this.l.put(sx, s1);
                this.i.skipWhitespace();
                if (!this.i.canRead()) {
                    continue;
                }

                ix = -1;
                if (this.i.peek() == ',') {
                    this.i.skip();
                    this.s = this::e;
                    continue;
                }

                if (this.i.peek() != ']') {
                    throw g.createWithContext(this.i);
                }
            }

            if (this.i.canRead()) {
                this.i.skip();
                return;
            }

            if (ix >= 0) {
                this.i.setCursor(ix);
            }

            throw g.createWithContext(this.i);
        }
    }

    public void i() throws CommandSyntaxException {
        this.p = (new MojangsonParser(this.i)).f();
    }

    private <T extends Comparable<T>> void a(IBlockState<T> iblockstate, String sx, int ix) throws CommandSyntaxException {
        Optional optional = iblockstate.b(sx);
        if (optional.isPresent()) {
            this.o = (IBlockData)this.o.set(iblockstate, (Comparable)optional.get());
            this.k.put(iblockstate, optional.get());
        } else {
            this.i.setCursor(ix);
            throw e.createWithContext(this.i, this.m.toString(), iblockstate.a(), sx);
        }
    }

    public static String a(IBlockData iblockdata, @Nullable NBTTagCompound nbttagcompound) {
        StringBuilder stringbuilder = new StringBuilder(IRegistry.BLOCK.getKey(iblockdata.getBlock()).toString());
        if (!iblockdata.a().isEmpty()) {
            stringbuilder.append('[');
            boolean flag = false;

            for(UnmodifiableIterator unmodifiableiterator = iblockdata.b().entrySet().iterator(); unmodifiableiterator.hasNext(); flag = true) {
                Entry entry = (Entry)unmodifiableiterator.next();
                if (flag) {
                    stringbuilder.append(',');
                }

                a(stringbuilder, (IBlockState)entry.getKey(), (Comparable)entry.getValue());
            }

            stringbuilder.append(']');
        }

        if (nbttagcompound != null) {
            stringbuilder.append(nbttagcompound);
        }

        return stringbuilder.toString();
    }

    private static <T extends Comparable<T>> void a(StringBuilder stringbuilder, IBlockState<T> iblockstate, Comparable<?> comparable) {
        stringbuilder.append(iblockstate.a());
        stringbuilder.append('=');
        stringbuilder.append(iblockstate.a(comparable));
    }

    public CompletableFuture<Suggestions> a(SuggestionsBuilder suggestionsbuilder) {
        return (CompletableFuture)this.s.apply(suggestionsbuilder.createOffset(this.i.getCursor()));
    }

    public Map<String, String> j() {
        return this.l;
    }
}
