package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class CommandSaveOn {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.save.alreadyOn", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("save-on").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(4);
        })).executes((commandcontext) -> {
            CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
            boolean flag = false;

            for(WorldServer worldserver : commandlistenerwrapper.getServer().getWorlds()) {
                if (worldserver != null && worldserver.savingDisabled) {
                    worldserver.savingDisabled = false;
                    flag = true;
                }
            }

            if (!flag) {
                throw a.create();
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.save.enabled", new Object[0]), true);
                return 1;
            }
        }));
    }
}
