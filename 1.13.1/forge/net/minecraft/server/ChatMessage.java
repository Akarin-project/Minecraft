package net.minecraft.server;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ChatMessage extends ChatBaseComponent {
    private static final LocaleLanguage d = new LocaleLanguage();
    private static final LocaleLanguage e = LocaleLanguage.a();
    private final String f;
    private final Object[] g;
    private final Object h = new Object();
    private long i = -1L;
    @VisibleForTesting
    List<IChatBaseComponent> b = Lists.newArrayList();
    public static final Pattern c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    public ChatMessage(String s, Object... aobject) {
        this.f = s;
        this.g = aobject;

        for(int ix = 0; ix < aobject.length; ++ix) {
            Object object = aobject[ix];
            if (object instanceof IChatBaseComponent) {
                IChatBaseComponent ichatbasecomponent = ((IChatBaseComponent)object).h();
                this.g[ix] = ichatbasecomponent;
                ichatbasecomponent.getChatModifier().setChatModifier(this.getChatModifier());
            } else if (object == null) {
                this.g[ix] = "null";
            }
        }

    }

    @VisibleForTesting
    synchronized void i() {
        synchronized(this.h) {
            long ix = e.b();
            if (ix == this.i) {
                return;
            }

            this.i = ix;
            this.b.clear();
        }

        try {
            this.b(e.a(this.f));
        } catch (ChatMessageException chatmessageexception) {
            this.b.clear();

            try {
                this.b(d.a(this.f));
            } catch (ChatMessageException var5) {
                throw chatmessageexception;
            }
        }

    }

    protected void b(String s) {
        Matcher matcher = c.matcher(s);

        try {
            int ix = 0;

            int j;
            int l;
            for(j = 0; matcher.find(j); j = l) {
                int k = matcher.start();
                l = matcher.end();
                if (k > j) {
                    ChatComponentText chatcomponenttext = new ChatComponentText(String.format(s.substring(j, k)));
                    chatcomponenttext.getChatModifier().setChatModifier(this.getChatModifier());
                    this.b.add(chatcomponenttext);
                }

                String s3 = matcher.group(2);
                String s1 = s.substring(k, l);
                if ("%".equals(s3) && "%%".equals(s1)) {
                    ChatComponentText chatcomponenttext2 = new ChatComponentText("%");
                    chatcomponenttext2.getChatModifier().setChatModifier(this.getChatModifier());
                    this.b.add(chatcomponenttext2);
                } else {
                    if (!"s".equals(s3)) {
                        throw new ChatMessageException(this, "Unsupported format: '" + s1 + "'");
                    }

                    String s2 = matcher.group(1);
                    int i1 = s2 != null ? Integer.parseInt(s2) - 1 : ix++;
                    if (i1 < this.g.length) {
                        this.b.add(this.b(i1));
                    }
                }
            }

            if (j < s.length()) {
                ChatComponentText chatcomponenttext1 = new ChatComponentText(String.format(s.substring(j)));
                chatcomponenttext1.getChatModifier().setChatModifier(this.getChatModifier());
                this.b.add(chatcomponenttext1);
            }

        } catch (IllegalFormatException illegalformatexception) {
            throw new ChatMessageException(this, illegalformatexception);
        }
    }

    private IChatBaseComponent b(int ix) {
        if (ix >= this.g.length) {
            throw new ChatMessageException(this, ix);
        } else {
            Object object = this.g[ix];
            Object object1;
            if (object instanceof IChatBaseComponent) {
                object1 = (IChatBaseComponent)object;
            } else {
                object1 = new ChatComponentText(object == null ? "null" : object.toString());
                ((IChatBaseComponent)object1).getChatModifier().setChatModifier(this.getChatModifier());
            }

            return (IChatBaseComponent)object1;
        }
    }

    public IChatBaseComponent setChatModifier(ChatModifier chatmodifier) {
        super.setChatModifier(chatmodifier);

        for(Object object : this.g) {
            if (object instanceof IChatBaseComponent) {
                ((IChatBaseComponent)object).getChatModifier().setChatModifier(this.getChatModifier());
            }
        }

        if (this.i > -1L) {
            for(IChatBaseComponent ichatbasecomponent : this.b) {
                ichatbasecomponent.getChatModifier().setChatModifier(chatmodifier);
            }
        }

        return this;
    }

    public Stream<IChatBaseComponent> c() {
        this.i();
        return Streams.concat(new Stream[]{this.b.stream(), this.a.stream()}).flatMap(IChatBaseComponent::c);
    }

    public String getText() {
        this.i();
        StringBuilder stringbuilder = new StringBuilder();

        for(IChatBaseComponent ichatbasecomponent : this.b) {
            stringbuilder.append(ichatbasecomponent.getText());
        }

        return stringbuilder.toString();
    }

    public ChatMessage j() {
        Object[] aobject = new Object[this.g.length];

        for(int ix = 0; ix < this.g.length; ++ix) {
            if (this.g[ix] instanceof IChatBaseComponent) {
                aobject[ix] = ((IChatBaseComponent)this.g[ix]).h();
            } else {
                aobject[ix] = this.g[ix];
            }
        }

        return new ChatMessage(this.f, aobject);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ChatMessage)) {
            return false;
        } else {
            ChatMessage chatmessage1 = (ChatMessage)object;
            return Arrays.equals(this.g, chatmessage1.g) && this.f.equals(chatmessage1.f) && super.equals(object);
        }
    }

    public int hashCode() {
        int ix = super.hashCode();
        ix = 31 * ix + this.f.hashCode();
        ix = 31 * ix + Arrays.hashCode(this.g);
        return ix;
    }

    public String toString() {
        return "TranslatableComponent{key='" + this.f + '\'' + ", args=" + Arrays.toString(this.g) + ", siblings=" + this.a + ", style=" + this.getChatModifier() + '}';
    }

    public String k() {
        return this.f;
    }

    public Object[] l() {
        return this.g;
    }

    // $FF: synthetic method
    public IChatBaseComponent g() {
        return this.j();
    }
}
