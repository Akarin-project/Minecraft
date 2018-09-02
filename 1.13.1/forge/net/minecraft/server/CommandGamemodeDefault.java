package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandGamemodeDefault {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        LiteralArgumentBuilder literalargumentbuilder = (LiteralArgumentBuilder)CommandDispatcher.a("defaultgamemode").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        });

        for(EnumGamemode enumgamemode : EnumGamemode.values()) {
            if (enumgamemode != EnumGamemode.NOT_SET) {
                literalargumentbuilder.then(CommandDispatcher.a(enumgamemode.b()).executes((commandcontext) -> {
                    return a((CommandListenerWrapper)commandcontext.getSource(), enumgamemode);
                }));
            }
        }

        commanddispatcher.register(literalargumentbuilder);
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, EnumGamemode enumgamemode) {
        int i = 0;
        MinecraftServer minecraftserver = commandlistenerwrapper.getServer();
        minecraftserver.setGamemode(enumgamemode);
        if (minecraftserver.getForceGamemode()) {
            for(EntityPlayer entityplayer : minecraftserver.getPlayerList().v()) {
                if (entityplayer.playerInteractManager.getGameMode() != enumgamemode) {
                    entityplayer.a(enumgamemode);
                    ++i;
                }
            }
        }

        commandlistenerwrapper.sendMessage(new ChatMessage("commands.defaultgamemode.success", new Object[]{enumgamemode.c()}), true);
        return i;
    }
}