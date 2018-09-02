package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collection;

public class CommandReplaceItem {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.replaceitem.block.failed", new Object[0]));
    private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.replaceitem.slot.inapplicable", new Object[]{object});
    });
    private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("commands.replaceitem.entity.failed", new Object[]{object, object1});
    });

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("replaceitem").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("block").then(CommandDispatcher.a("pos", ArgumentPosition.a()).then(CommandDispatcher.a("slot", ArgumentInventorySlot.a()).then(((RequiredArgumentBuilder)CommandDispatcher.a("item", ArgumentItemStack.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(1, false));
        })).then(CommandDispatcher.a("count", IntegerArgumentType.integer(1, 64)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(IntegerArgumentType.getInteger(commandcontext, "count"), true));
        }))))))).then(CommandDispatcher.a("entity").then(CommandDispatcher.a("targets", ArgumentEntity.b()).then(CommandDispatcher.a("slot", ArgumentInventorySlot.a()).then(((RequiredArgumentBuilder)CommandDispatcher.a("item", ArgumentItemStack.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.b(commandcontext, "targets"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(1, false));
        })).then(CommandDispatcher.a("count", IntegerArgumentType.integer(1, 64)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.b(commandcontext, "targets"), ArgumentInventorySlot.a(commandcontext, "slot"), ArgumentItemStack.a(commandcontext, "item").a(IntegerArgumentType.getInteger(commandcontext, "count"), true));
        })))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition, int i, ItemStack itemstack) throws CommandSyntaxException {
        TileEntity tileentity = commandlistenerwrapper.getWorld().getTileEntity(blockposition);
        if (!(tileentity instanceof IInventory)) {
            throw a.create();
        } else {
            IInventory iinventory = (IInventory)tileentity;
            if (i >= 0 && i < iinventory.getSize()) {
                iinventory.setItem(i, itemstack);
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.replaceitem.block.success", new Object[]{blockposition.getX(), blockposition.getY(), blockposition.getZ(), itemstack.A()}), true);
                return 1;
            } else {
                throw b.create(i);
            }
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<? extends Entity> collection, int i, ItemStack itemstack) throws CommandSyntaxException {
        ArrayList arraylist = Lists.newArrayListWithCapacity(collection.size());

        for(Entity entity : collection) {
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer)entity).defaultContainer.b();
            }

            if (entity.c(i, itemstack.cloneItemStack())) {
                arraylist.add(entity);
                if (entity instanceof EntityPlayer) {
                    ((EntityPlayer)entity).defaultContainer.b();
                }
            }
        }

        if (arraylist.isEmpty()) {
            throw c.create(itemstack.A(), i);
        } else {
            if (arraylist.size() == 1) {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.replaceitem.entity.success.single", new Object[]{((Entity)arraylist.iterator().next()).getScoreboardDisplayName(), itemstack.A()}), true);
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.replaceitem.entity.success.multiple", new Object[]{arraylist.size(), itemstack.A()}), true);
            }

            return arraylist.size();
        }
    }
}
