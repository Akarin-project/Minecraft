package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

public class CommandTellRaw {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("tellraw").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("targets", ArgumentEntity.d()).then(CommandDispatcher.a("message", ArgumentChatComponent.a()).executes((commandcontext) -> {
            int i = 0;

            for(EntityPlayer entityplayer : ArgumentEntity.f(commandcontext, "targets")) {
                entityplayer.sendMessage(ChatComponentUtils.filterForDisplay((CommandListenerWrapper)commandcontext.getSource(), ArgumentChatComponent.a(commandcontext, "message"), entityplayer));
                ++i;
            }

            return i;
        }))));
    }
}
