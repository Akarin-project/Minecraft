package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

public class CommandDifficulty {
    private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.difficulty.failure", new Object[]{object});
    });

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        LiteralArgumentBuilder literalargumentbuilder = CommandDispatcher.a("difficulty");

        for(EnumDifficulty enumdifficulty : EnumDifficulty.values()) {
            literalargumentbuilder.then(CommandDispatcher.a(enumdifficulty.c()).executes((commandcontext) -> {
                return a((CommandListenerWrapper)commandcontext.getSource(), enumdifficulty);
            }));
        }

        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalargumentbuilder.requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).executes((commandcontext) -> {
            EnumDifficulty enumdifficulty1 = ((CommandListenerWrapper)commandcontext.getSource()).getWorld().getDifficulty();
            ((CommandListenerWrapper)commandcontext.getSource()).sendMessage(new ChatMessage("commands.difficulty.query", new Object[]{enumdifficulty1.b()}), false);
            return enumdifficulty1.a();
        }));
    }

    public static int a(CommandListenerWrapper commandlistenerwrapper, EnumDifficulty enumdifficulty) throws CommandSyntaxException {
        MinecraftServer minecraftserver = commandlistenerwrapper.getServer();
        if (minecraftserver.getWorldServer(DimensionManager.OVERWORLD).getDifficulty() == enumdifficulty) {
            throw a.create(enumdifficulty.c());
        } else {
            minecraftserver.a(enumdifficulty);
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.difficulty.success", new Object[]{enumdifficulty.b()}), true);
            return 0;
        }
    }
}
