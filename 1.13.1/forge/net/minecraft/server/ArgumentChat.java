package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;

public class ArgumentChat implements ArgumentType<ArgumentChat.a> {
    private static final Collection<String> a = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

    public ArgumentChat() {
    }

    public static ArgumentChat a() {
        return new ArgumentChat();
    }

    public static IChatBaseComponent a(CommandContext<CommandListenerWrapper> commandcontext, String s) throws CommandSyntaxException {
        return ((ArgumentChat.a)commandcontext.getArgument(s, ArgumentChat.a.class)).a((CommandListenerWrapper)commandcontext.getSource(), ((CommandListenerWrapper)commandcontext.getSource()).hasPermission(2));
    }

    public ArgumentChat.a a(StringReader stringreader) throws CommandSyntaxException {
        return ArgumentChat.a.a(stringreader, true);
    }

    public Collection<String> getExamples() {
        return a;
    }

    // $FF: synthetic method
    public Object parse(StringReader stringreader) throws CommandSyntaxException {
        return this.a(stringreader);
    }

    public static class a {
        private final String a;
        private final ArgumentChat.b[] b;

        public a(String s, ArgumentChat.b[] aargumentchat$b) {
            this.a = s;
            this.b = aargumentchat$b;
        }

        public IChatBaseComponent a(CommandListenerWrapper commandlistenerwrapper, boolean flag) throws CommandSyntaxException {
            if (this.b.length != 0 && flag) {
                ChatComponentText chatcomponenttext = new ChatComponentText(this.a.substring(0, this.b[0].a()));
                int i = this.b[0].a();

                for(ArgumentChat.b argumentchat$b : this.b) {
                    IChatBaseComponent ichatbasecomponent = argumentchat$b.a(commandlistenerwrapper);
                    if (i < argumentchat$b.a()) {
                        chatcomponenttext.a(this.a.substring(i, argumentchat$b.a()));
                    }

                    if (ichatbasecomponent != null) {
                        chatcomponenttext.addSibling(ichatbasecomponent);
                    }

                    i = argumentchat$b.b();
                }

                if (i < this.a.length()) {
                    chatcomponenttext.a(this.a.substring(i, this.a.length()));
                }

                return chatcomponenttext;
            } else {
                return new ChatComponentText(this.a);
            }
        }

        public static ArgumentChat.a a(StringReader stringreader, boolean flag) throws CommandSyntaxException {
            String s = stringreader.getString().substring(stringreader.getCursor(), stringreader.getTotalLength());
            if (!flag) {
                stringreader.setCursor(stringreader.getTotalLength());
                return new ArgumentChat.a(s, new ArgumentChat.b[0]);
            } else {
                ArrayList arraylist = Lists.newArrayList();
                int i = stringreader.getCursor();

                while(true) {
                    int j;
                    EntitySelector entityselector;
                    while(true) {
                        if (!stringreader.canRead()) {
                            return new ArgumentChat.a(s, (ArgumentChat.b[])arraylist.toArray(new ArgumentChat.b[arraylist.size()]));
                        }

                        if (stringreader.peek() == '@') {
                            j = stringreader.getCursor();

                            try {
                                ArgumentParserSelector argumentparserselector = new ArgumentParserSelector(stringreader);
                                entityselector = argumentparserselector.s();
                                break;
                            } catch (CommandSyntaxException commandsyntaxexception) {
                                if (commandsyntaxexception.getType() != ArgumentParserSelector.d && commandsyntaxexception.getType() != ArgumentParserSelector.b) {
                                    throw commandsyntaxexception;
                                }

                                stringreader.setCursor(j + 1);
                            }
                        } else {
                            stringreader.skip();
                        }
                    }

                    arraylist.add(new ArgumentChat.b(j - i, stringreader.getCursor() - i, entityselector));
                }
            }
        }
    }

    public static class b {
        private final int a;
        private final int b;
        private final EntitySelector c;

        public b(int i, int j, EntitySelector entityselector) {
            this.a = i;
            this.b = j;
            this.c = entityselector;
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        @Nullable
        public IChatBaseComponent a(CommandListenerWrapper commandlistenerwrapper) throws CommandSyntaxException {
            return EntitySelector.a(this.c.b(commandlistenerwrapper));
        }
    }
}
