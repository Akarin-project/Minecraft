package net.minecraft.server;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class CommandSetBlock {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.setblock.failed", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("setblock").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("pos", ArgumentPosition.a()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("block", ArgumentTile.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentTile.a(commandcontext, "block"), CommandSetBlock.Mode.REPLACE, (Predicate)null);
        })).then(CommandDispatcher.a("destroy").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentTile.a(commandcontext, "block"), CommandSetBlock.Mode.DESTROY, (Predicate)null);
        }))).then(CommandDispatcher.a("keep").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentTile.a(commandcontext, "block"), CommandSetBlock.Mode.REPLACE, (shapedetectorblock) -> {
                return shapedetectorblock.c().isEmpty(shapedetectorblock.getPosition());
            });
        }))).then(CommandDispatcher.a("replace").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "pos"), ArgumentTile.a(commandcontext, "block"), CommandSetBlock.Mode.REPLACE, (Predicate)null);
        })))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition, ArgumentTileLocation argumenttilelocation, CommandSetBlock.Mode commandsetblock$mode, @Nullable Predicate<ShapeDetectorBlock> predicate) throws CommandSyntaxException {
        WorldServer worldserver = commandlistenerwrapper.getWorld();
        if (predicate != null && !predicate.test(new ShapeDetectorBlock(worldserver, blockposition, true))) {
            throw a.create();
        } else {
            boolean flag;
            if (commandsetblock$mode == CommandSetBlock.Mode.DESTROY) {
                worldserver.setAir(blockposition, true);
                flag = !argumenttilelocation.a().isAir();
            } else {
                TileEntity tileentity = worldserver.getTileEntity(blockposition);
                if (tileentity instanceof IInventory) {
                    ((IInventory)tileentity).clear();
                }

                flag = true;
            }

            if (flag && !argumenttilelocation.a(worldserver, blockposition, 2)) {
                throw a.create();
            } else {
                worldserver.update(blockposition, argumenttilelocation.a().getBlock());
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.setblock.success", new Object[]{blockposition.getX(), blockposition.getY(), blockposition.getZ()}), true);
                return 1;
            }
        }
    }

    public interface Filter {
        @Nullable
        ArgumentTileLocation filter(StructureBoundingBox var1, BlockPosition var2, ArgumentTileLocation var3, WorldServer var4);
    }

    public static enum Mode {
        REPLACE,
        OUTLINE,
        HOLLOW,
        DESTROY;

        private Mode() {
        }
    }
}
