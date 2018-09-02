package net.minecraft.server;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;

public class CommandGive {
    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("give").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("targets", ArgumentEntity.d()).then(((RequiredArgumentBuilder)CommandDispatcher.a("item", ArgumentItemStack.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentItemStack.a(commandcontext, "item"), ArgumentEntity.f(commandcontext, "targets"), 1);
        })).then(CommandDispatcher.a("count", IntegerArgumentType.integer(1)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentItemStack.a(commandcontext, "item"), ArgumentEntity.f(commandcontext, "targets"), IntegerArgumentType.getInteger(commandcontext, "count"));
        })))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, ArgumentPredicateItemStack argumentpredicateitemstack, Collection<EntityPlayer> collection, int i) throws CommandSyntaxException {
        for(EntityPlayer entityplayer : collection) {
            int j = i;

            while(j > 0) {
                int k = Math.min(argumentpredicateitemstack.a().getMaxStackSize(), j);
                j -= k;
                ItemStack itemstack = argumentpredicateitemstack.a(k, false);
                boolean flag = entityplayer.inventory.pickup(itemstack);
                if (flag && itemstack.isEmpty()) {
                    itemstack.setCount(1);
                    EntityItem entityitem1 = entityplayer.drop(itemstack, false);
                    if (entityitem1 != null) {
                        entityitem1.t();
                    }

                    entityplayer.world.a((EntityHuman)null, entityplayer.locX, entityplayer.locY, entityplayer.locZ, SoundEffects.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((entityplayer.getRandom().nextFloat() - entityplayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    entityplayer.defaultContainer.b();
                } else {
                    EntityItem entityitem = entityplayer.drop(itemstack, false);
                    if (entityitem != null) {
                        entityitem.o();
                        entityitem.b(entityplayer.getUniqueID());
                    }
                }
            }
        }

        if (collection.size() == 1) {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.give.success.single", new Object[]{i, argumentpredicateitemstack.a(i, false).A(), ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
        } else {
            commandlistenerwrapper.sendMessage(new ChatMessage("commands.give.success.single", new Object[]{i, argumentpredicateitemstack.a(i, false).A(), collection.size()}), true);
        }

        return collection.size();
    }
}
