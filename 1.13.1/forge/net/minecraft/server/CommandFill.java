package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class CommandFill {
    private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("commands.fill.toobig", new Object[]{object, object1});
    });
    private static final ArgumentTileLocation b = new ArgumentTileLocation(Blocks.AIR.getBlockData(), Collections.emptySet(), (NBTTagCompound)null);
    private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new ChatMessage("commands.fill.failed", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("fill").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("from", ArgumentPosition.a()).then(CommandDispatcher.a("to", ArgumentPosition.a()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("block", ArgumentTile.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new StructureBoundingBox(ArgumentPosition.a(commandcontext, "from"), ArgumentPosition.a(commandcontext, "to")), ArgumentTile.a(commandcontext, "block"), CommandFill.Mode.REPLACE, (Predicate)null);
        })).then(((LiteralArgumentBuilder)CommandDispatcher.a("replace").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new StructureBoundingBox(ArgumentPosition.a(commandcontext, "from"), ArgumentPosition.a(commandcontext, "to")), ArgumentTile.a(commandcontext, "block"), CommandFill.Mode.REPLACE, (Predicate)null);
        })).then(CommandDispatcher.a("filter", ArgumentBlockPredicate.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new StructureBoundingBox(ArgumentPosition.a(commandcontext, "from"), ArgumentPosition.a(commandcontext, "to")), ArgumentTile.a(commandcontext, "block"), CommandFill.Mode.REPLACE, ArgumentBlockPredicate.a(commandcontext, "filter"));
        })))).then(CommandDispatcher.a("keep").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new StructureBoundingBox(ArgumentPosition.a(commandcontext, "from"), ArgumentPosition.a(commandcontext, "to")), ArgumentTile.a(commandcontext, "block"), CommandFill.Mode.REPLACE, (shapedetectorblock) -> {
                return shapedetectorblock.c().isEmpty(shapedetectorblock.getPosition());
            });
        }))).then(CommandDispatcher.a("outline").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new StructureBoundingBox(ArgumentPosition.a(commandcontext, "from"), ArgumentPosition.a(commandcontext, "to")), ArgumentTile.a(commandcontext, "block"), CommandFill.Mode.OUTLINE, (Predicate)null);
        }))).then(CommandDispatcher.a("hollow").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new StructureBoundingBox(ArgumentPosition.a(commandcontext, "from"), ArgumentPosition.a(commandcontext, "to")), ArgumentTile.a(commandcontext, "block"), CommandFill.Mode.HOLLOW, (Predicate)null);
        }))).then(CommandDispatcher.a("destroy").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), new StructureBoundingBox(ArgumentPosition.a(commandcontext, "from"), ArgumentPosition.a(commandcontext, "to")), ArgumentTile.a(commandcontext, "block"), CommandFill.Mode.DESTROY, (Predicate)null);
        }))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, StructureBoundingBox structureboundingbox, ArgumentTileLocation argumenttilelocation, CommandFill.Mode commandfill$mode, @Nullable Predicate<ShapeDetectorBlock> predicate) throws CommandSyntaxException {
        int i = structureboundingbox.c() * structureboundingbox.d() * structureboundingbox.e();
        if (i > 32768) {
            throw a.create(32768, i);
        } else {
            ArrayList arraylist = Lists.newArrayList();
            WorldServer worldserver = commandlistenerwrapper.getWorld();
            int j = 0;

            for(BlockPosition blockposition : BlockPosition.MutableBlockPosition.a(structureboundingbox.a, structureboundingbox.b, structureboundingbox.c, structureboundingbox.d, structureboundingbox.e, structureboundingbox.f)) {
                if (predicate == null || predicate.test(new ShapeDetectorBlock(worldserver, blockposition, true))) {
                    ArgumentTileLocation argumenttilelocation1 = commandfill$mode.e.filter(structureboundingbox, blockposition, argumenttilelocation, worldserver);
                    if (argumenttilelocation1 != null) {
                        TileEntity tileentity = worldserver.getTileEntity(blockposition);
                        if (tileentity != null && tileentity instanceof IInventory) {
                            ((IInventory)tileentity).clear();
                        }

                        if (argumenttilelocation1.a(worldserver, blockposition, 2)) {
                            arraylist.add(blockposition.h());
                            ++j;
                        }
                    }
                }
            }

            for(BlockPosition blockposition1 : arraylist) {
                Block block = worldserver.getType(blockposition1).getBlock();
                worldserver.update(blockposition1, block);
            }

            if (j == 0) {
                throw c.create();
            } else {
                commandlistenerwrapper.sendMessage(new ChatMessage("commands.fill.success", new Object[]{j}), true);
                return j;
            }
        }
    }

    static enum Mode {
        REPLACE((var0, var1, argumenttilelocation, var3) -> {
            return argumenttilelocation;
        }),
        OUTLINE((structureboundingbox, blockposition, argumenttilelocation, var3) -> {
            return blockposition.getX() != structureboundingbox.a && blockposition.getX() != structureboundingbox.d && blockposition.getY() != structureboundingbox.b && blockposition.getY() != structureboundingbox.e && blockposition.getZ() != structureboundingbox.c && blockposition.getZ() != structureboundingbox.f ? null : argumenttilelocation;
        }),
        HOLLOW((structureboundingbox, blockposition, argumenttilelocation, var3) -> {
            return blockposition.getX() != structureboundingbox.a && blockposition.getX() != structureboundingbox.d && blockposition.getY() != structureboundingbox.b && blockposition.getY() != structureboundingbox.e && blockposition.getZ() != structureboundingbox.c && blockposition.getZ() != structureboundingbox.f ? CommandFill.b : argumenttilelocation;
        }),
        DESTROY((var0, blockposition, argumenttilelocation, worldserver) -> {
            worldserver.setAir(blockposition, true);
            return argumenttilelocation;
        });

        public final CommandSetBlock.Filter e;

        private Mode(CommandSetBlock.Filter commandsetblock$filter) {
            this.e = commandsetblock$filter;
        }
    }
}
