package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandSetWorldSpawn {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("setworldspawn").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new BlockPosition(((CommandListenerWrapper)commandcontext.getSource()).getPosition()));
        })).then(CommandDispatcher.a("pos", ArgumentPosition.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.b(commandcontext, "pos"));
        })));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition) {
        commandlistenerwrapper.getWorld().v(blockposition);
        commandlistenerwrapper.getServer().getPlayerList().sendAll(new PacketPlayOutSpawnPosition(blockposition));
        commandlistenerwrapper.sendMessage(new ChatMessage("commands.setworldspawn.success", new Object[]{blockposition.getX(), blockposition.getY(), blockposition.getZ()}), true);
        return 1;
    }
}
