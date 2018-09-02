package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class ChatBaseComponent implements IChatBaseComponent {
    protected List<IChatBaseComponent> a = Lists.newArrayList();
    private ChatModifier b;

    public ChatBaseComponent() {
    }

    public IChatBaseComponent addSibling(IChatBaseComponent ichatbasecomponent) {
        ichatbasecomponent.getChatModifier().setChatModifier(this.getChatModifier());
        this.a.add(ichatbasecomponent);
        return this;
    }

    public List<IChatBaseComponent> a() {
        return this.a;
    }

    public IChatBaseComponent setChatModifier(ChatModifier chatmodifier) {
        this.b = chatmodifier;

        for(IChatBaseComponent ichatbasecomponent : this.a) {
            ichatbasecomponent.getChatModifier().setChatModifier(this.getChatModifier());
        }

        return this;
    }

    public ChatModifier getChatModifier() {
        if (this.b == null) {
            this.b = new ChatModifier();

            for(IChatBaseComponent ichatbasecomponent : this.a) {
                ichatbasecomponent.getChatModifier().setChatModifier(this.b);
            }
        }

        return this.b;
    }

    public Stream<IChatBaseComponent> c() {
        return Streams.concat(new Stream[]{Stream.of(this), this.a.stream().flatMap(IChatBaseComponent::c)});
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ChatBaseComponent)) {
            return false;
        } else {
            ChatBaseComponent chatbasecomponent1 = (ChatBaseComponent)object;
            return this.a.equals(chatbasecomponent1.a) && this.getChatModifier().equals(chatbasecomponent1.getChatModifier());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getChatModifier(), this.a});
    }

    public String toString() {
        return "BaseComponent{style=" + this.b + ", siblings=" + this.a + '}';
    }
}
