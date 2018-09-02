package net.minecraft.server;

import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class PacketPlayOutCommands implements Packet<PacketListenerPlayOut> {
    private RootCommandNode<ICompletionProvider> a;

    public PacketPlayOutCommands() {
    }

    public PacketPlayOutCommands(RootCommandNode<ICompletionProvider> rootcommandnode) {
        this.a = rootcommandnode;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        PacketPlayOutCommands.a[] apacketplayoutcommands$a = new PacketPlayOutCommands.a[packetdataserializer.g()];
        ArrayDeque arraydeque = new ArrayDeque(apacketplayoutcommands$a.length);

        for(int i = 0; i < apacketplayoutcommands$a.length; ++i) {
            apacketplayoutcommands$a[i] = this.c(packetdataserializer);
            arraydeque.add(apacketplayoutcommands$a[i]);
        }

        while(!arraydeque.isEmpty()) {
            boolean flag = false;
            Iterator iterator = arraydeque.iterator();

            while(iterator.hasNext()) {
                PacketPlayOutCommands.a packetplayoutcommands$a = (PacketPlayOutCommands.a)iterator.next();
                if (packetplayoutcommands$a.a(apacketplayoutcommands$a)) {
                    iterator.remove();
                    flag = true;
                }
            }

            if (!flag) {
                throw new IllegalStateException("Server sent an impossible command tree");
            }
        }

        this.a = (RootCommandNode)apacketplayoutcommands$a[packetdataserializer.g()].e;
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        HashMap hashmap = Maps.newHashMap();
        ArrayDeque arraydeque = new ArrayDeque();
        arraydeque.add(this.a);

        while(!arraydeque.isEmpty()) {
            CommandNode commandnode = (CommandNode)arraydeque.pollFirst();
            if (!hashmap.containsKey(commandnode)) {
                int i = hashmap.size();
                hashmap.put(commandnode, i);
                arraydeque.addAll(commandnode.getChildren());
                if (commandnode.getRedirect() != null) {
                    arraydeque.add(commandnode.getRedirect());
                }
            }
        }

        CommandNode[] acommandnode = new CommandNode[hashmap.size()];

        for(Entry entry : hashmap.entrySet()) {
            acommandnode[entry.getValue()] = (CommandNode)entry.getKey();
        }

        packetdataserializer.d(acommandnode.length);

        for(CommandNode commandnode1 : acommandnode) {
            this.a(packetdataserializer, commandnode1, hashmap);
        }

        packetdataserializer.d(hashmap.get(this.a));
    }

    private PacketPlayOutCommands.a c(PacketDataSerializer packetdataserializer) {
        byte b0 = packetdataserializer.readByte();
        int[] aint = packetdataserializer.b();
        int i = (b0 & 8) != 0 ? packetdataserializer.g() : 0;
        ArgumentBuilder argumentbuilder = this.a(packetdataserializer, b0);
        return new PacketPlayOutCommands.a(argumentbuilder, b0, i, aint);
    }

    @Nullable
    private ArgumentBuilder<ICompletionProvider, ?> a(PacketDataSerializer packetdataserializer, byte b0) {
        int i = b0 & 3;
        if (i == 2) {
            String s = packetdataserializer.e(32767);
            ArgumentType argumenttype = ArgumentRegistry.a(packetdataserializer);
            if (argumenttype == null) {
                return null;
            } else {
                RequiredArgumentBuilder requiredargumentbuilder = RequiredArgumentBuilder.argument(s, argumenttype);
                if ((b0 & 16) != 0) {
                    requiredargumentbuilder.suggests(CompletionProviders.a(packetdataserializer.l()));
                }

                return requiredargumentbuilder;
            }
        } else {
            return i == 1 ? LiteralArgumentBuilder.literal(packetdataserializer.e(32767)) : null;
        }
    }

    private void a(PacketDataSerializer packetdataserializer, CommandNode<ICompletionProvider> commandnode, Map<CommandNode<ICompletionProvider>, Integer> map) {
        byte b0 = 0;
        if (commandnode.getRedirect() != null) {
            b0 = (byte)(b0 | 8);
        }

        if (commandnode.getCommand() != null) {
            b0 = (byte)(b0 | 4);
        }

        if (commandnode instanceof RootCommandNode) {
            b0 = (byte)(b0 | 0);
        } else if (commandnode instanceof ArgumentCommandNode) {
            b0 = (byte)(b0 | 2);
            if (((ArgumentCommandNode)commandnode).getCustomSuggestions() != null) {
                b0 = (byte)(b0 | 16);
            }
        } else {
            if (!(commandnode instanceof LiteralCommandNode)) {
                throw new UnsupportedOperationException("Unknown node type " + commandnode);
            }

            b0 = (byte)(b0 | 1);
        }

        packetdataserializer.writeByte(b0);
        packetdataserializer.d(commandnode.getChildren().size());

        for(CommandNode commandnode1 : commandnode.getChildren()) {
            packetdataserializer.d(map.get(commandnode1));
        }

        if (commandnode.getRedirect() != null) {
            packetdataserializer.d(map.get(commandnode.getRedirect()));
        }

        if (commandnode instanceof ArgumentCommandNode) {
            ArgumentCommandNode argumentcommandnode = (ArgumentCommandNode)commandnode;
            packetdataserializer.a(argumentcommandnode.getName());
            ArgumentRegistry.a(packetdataserializer, argumentcommandnode.getType());
            if (argumentcommandnode.getCustomSuggestions() != null) {
                packetdataserializer.a(CompletionProviders.a(argumentcommandnode.getCustomSuggestions()));
            }
        } else if (commandnode instanceof LiteralCommandNode) {
            packetdataserializer.a(((LiteralCommandNode)commandnode).getLiteral());
        }

    }

    public void a(PacketListenerPlayOut packetlistenerplayout) {
        packetlistenerplayout.a(this);
    }

    static class a {
        @Nullable
        private final ArgumentBuilder<ICompletionProvider, ?> a;
        private final byte b;
        private final int c;
        private final int[] d;
        private CommandNode<ICompletionProvider> e;

        private a(@Nullable ArgumentBuilder<ICompletionProvider, ?> argumentbuilder, byte b0, int i, int[] aint) {
            this.a = argumentbuilder;
            this.b = b0;
            this.c = i;
            this.d = aint;
        }

        public boolean a(PacketPlayOutCommands.a[] apacketplayoutcommands$a) {
            if (this.e == null) {
                if (this.a == null) {
                    this.e = new RootCommandNode();
                } else {
                    if ((this.b & 8) != 0) {
                        if (apacketplayoutcommands$a[this.c].e == null) {
                            return false;
                        }

                        this.a.redirect(apacketplayoutcommands$a[this.c].e);
                    }

                    if ((this.b & 4) != 0) {
                        this.a.executes((var0) -> {
                            return 0;
                        });
                    }

                    this.e = this.a.build();
                }
            }

            for(int i : this.d) {
                if (apacketplayoutcommands$a[i].e == null) {
                    return false;
                }
            }

            for(int j : this.d) {
                CommandNode commandnode = apacketplayoutcommands$a[j].e;
                if (!(commandnode instanceof RootCommandNode)) {
                    this.e.addChild(commandnode);
                }
            }

            return true;
        }
    }
}
