package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class CommandClone {
    private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("commands.clone.overlap", new Object[0]));
    private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((object, object1) -> {
        return new ChatMessage("commands.clone.toobig", new Object[]{object, object1});
    });
    private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new ChatMessage("commands.clone.failed", new Object[0]));
    public static final Predicate<ShapeDetectorBlock> a = (shapedetectorblock) -> {
        return !shapedetectorblock.a().isAir();
    };

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("clone").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(CommandDispatcher.a("begin", ArgumentPosition.a()).then(CommandDispatcher.a("end", ArgumentPosition.a()).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("destination", ArgumentPosition.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), (var0) -> {
                return true;
            }, CommandClone.Mode.NORMAL);
        })).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("replace").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), (var0) -> {
                return true;
            }, CommandClone.Mode.NORMAL);
        })).then(CommandDispatcher.a("force").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), (var0) -> {
                return true;
            }, CommandClone.Mode.FORCE);
        }))).then(CommandDispatcher.a("move").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), (var0) -> {
                return true;
            }, CommandClone.Mode.MOVE);
        }))).then(CommandDispatcher.a("normal").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), (var0) -> {
                return true;
            }, CommandClone.Mode.NORMAL);
        })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("masked").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), a, CommandClone.Mode.NORMAL);
        })).then(CommandDispatcher.a("force").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), a, CommandClone.Mode.FORCE);
        }))).then(CommandDispatcher.a("move").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), a, CommandClone.Mode.MOVE);
        }))).then(CommandDispatcher.a("normal").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), a, CommandClone.Mode.NORMAL);
        })))).then(CommandDispatcher.a("filtered").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandDispatcher.a("filter", ArgumentBlockPredicate.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), ArgumentBlockPredicate.a(commandcontext, "filter"), CommandClone.Mode.NORMAL);
        })).then(CommandDispatcher.a("force").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), ArgumentBlockPredicate.a(commandcontext, "filter"), CommandClone.Mode.FORCE);
        }))).then(CommandDispatcher.a("move").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), ArgumentBlockPredicate.a(commandcontext, "filter"), CommandClone.Mode.MOVE);
        }))).then(CommandDispatcher.a("normal").executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentPosition.a(commandcontext, "begin"), ArgumentPosition.a(commandcontext, "end"), ArgumentPosition.a(commandcontext, "destination"), ArgumentBlockPredicate.a(commandcontext, "filter"), CommandClone.Mode.NORMAL);
        }))))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, BlockPosition blockposition, BlockPosition blockposition1, BlockPosition blockposition2, Predicate<ShapeDetectorBlock> predicate, CommandClone.Mode commandclone$mode) throws CommandSyntaxException {
        StructureBoundingBox structureboundingbox = new StructureBoundingBox(blockposition, blockposition1);
        StructureBoundingBox structureboundingbox1 = new StructureBoundingBox(blockposition2, blockposition2.a(structureboundingbox.b()));
        if (!commandclone$mode.a() && structureboundingbox1.a(structureboundingbox)) {
            throw b.create();
        } else {
            int i = structureboundingbox.c() * structureboundingbox.d() * structureboundingbox.e();
            if (i > 32768) {
                throw c.create(32768, i);
            } else {
                WorldServer worldserver = commandlistenerwrapper.getWorld();
                if (worldserver.a(structureboundingbox) && worldserver.a(structureboundingbox1)) {
                    ArrayList arraylist = Lists.newArrayList();
                    ArrayList arraylist1 = Lists.newArrayList();
                    ArrayList arraylist2 = Lists.newArrayList();
                    LinkedList linkedlist = Lists.newLinkedList();
                    BlockPosition blockposition3 = new BlockPosition(structureboundingbox1.a - structureboundingbox.a, structureboundingbox1.b - structureboundingbox.b, structureboundingbox1.c - structureboundingbox.c);

                    for(int j = structureboundingbox.c; j <= structureboundingbox.f; ++j) {
                        for(int k = structureboundingbox.b; k <= structureboundingbox.e; ++k) {
                            for(int l = structureboundingbox.a; l <= structureboundingbox.d; ++l) {
                                BlockPosition blockposition4 = new BlockPosition(l, k, j);
                                BlockPosition blockposition5 = blockposition4.a(blockposition3);
                                ShapeDetectorBlock shapedetectorblock = new ShapeDetectorBlock(worldserver, blockposition4, false);
                                IBlockData iblockdata = shapedetectorblock.a();
                                if (predicate.test(shapedetectorblock)) {
                                    TileEntity tileentity = worldserver.getTileEntity(blockposition4);
                                    if (tileentity != null) {
                                        NBTTagCompound nbttagcompound = tileentity.save(new NBTTagCompound());
                                        arraylist1.add(new CommandClone.CommandCloneStoredTileEntity(blockposition5, iblockdata, nbttagcompound));
                                        linkedlist.addLast(blockposition4);
                                    } else if (!iblockdata.f(worldserver, blockposition4) && !iblockdata.g()) {
                                        arraylist2.add(new CommandClone.CommandCloneStoredTileEntity(blockposition5, iblockdata, (NBTTagCompound)null));
                                        linkedlist.addFirst(blockposition4);
                                    } else {
                                        arraylist.add(new CommandClone.CommandCloneStoredTileEntity(blockposition5, iblockdata, (NBTTagCompound)null));
                                        linkedlist.addLast(blockposition4);
                                    }
                                }
                            }
                        }
                    }

                    if (commandclone$mode == CommandClone.Mode.MOVE) {
                        for(BlockPosition blockposition6 : linkedlist) {
                            TileEntity tileentity1 = worldserver.getTileEntity(blockposition6);
                            if (tileentity1 instanceof IInventory) {
                                ((IInventory)tileentity1).clear();
                            }

                            worldserver.setTypeAndData(blockposition6, Blocks.BARRIER.getBlockData(), 2);
                        }

                        for(BlockPosition blockposition7 : linkedlist) {
                            worldserver.setTypeAndData(blockposition7, Blocks.AIR.getBlockData(), 3);
                        }
                    }

                    ArrayList arraylist3 = Lists.newArrayList();
                    arraylist3.addAll(arraylist);
                    arraylist3.addAll(arraylist1);
                    arraylist3.addAll(arraylist2);
                    List list = Lists.reverse(arraylist3);

                    for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity : list) {
                        TileEntity tileentity2 = worldserver.getTileEntity(commandclone$commandclonestoredtileentity.a);
                        if (tileentity2 instanceof IInventory) {
                            ((IInventory)tileentity2).clear();
                        }

                        worldserver.setTypeAndData(commandclone$commandclonestoredtileentity.a, Blocks.BARRIER.getBlockData(), 2);
                    }

                    int i1 = 0;

                    for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity1 : arraylist3) {
                        if (worldserver.setTypeAndData(commandclone$commandclonestoredtileentity1.a, commandclone$commandclonestoredtileentity1.b, 2)) {
                            ++i1;
                        }
                    }

                    for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity2 : arraylist1) {
                        TileEntity tileentity3 = worldserver.getTileEntity(commandclone$commandclonestoredtileentity2.a);
                        if (commandclone$commandclonestoredtileentity2.c != null && tileentity3 != null) {
                            commandclone$commandclonestoredtileentity2.c.setInt("x", commandclone$commandclonestoredtileentity2.a.getX());
                            commandclone$commandclonestoredtileentity2.c.setInt("y", commandclone$commandclonestoredtileentity2.a.getY());
                            commandclone$commandclonestoredtileentity2.c.setInt("z", commandclone$commandclonestoredtileentity2.a.getZ());
                            tileentity3.load(commandclone$commandclonestoredtileentity2.c);
                            tileentity3.update();
                        }

                        worldserver.setTypeAndData(commandclone$commandclonestoredtileentity2.a, commandclone$commandclonestoredtileentity2.b, 2);
                    }

                    for(CommandClone.CommandCloneStoredTileEntity commandclone$commandclonestoredtileentity3 : list) {
                        worldserver.update(commandclone$commandclonestoredtileentity3.a, commandclone$commandclonestoredtileentity3.b.getBlock());
                    }

                    worldserver.x().a(structureboundingbox, blockposition3);
                    if (i1 == 0) {
                        throw d.create();
                    } else {
                        commandlistenerwrapper.sendMessage(new ChatMessage("commands.clone.success", new Object[]{i1}), true);
                        return i1;
                    }
                } else {
                    throw ArgumentPosition.a.create();
                }
            }
        }
    }

    static class CommandCloneStoredTileEntity {
        public final BlockPosition a;
        public final IBlockData b;
        @Nullable
        public final NBTTagCompound c;

        public CommandCloneStoredTileEntity(BlockPosition blockposition, IBlockData iblockdata, @Nullable NBTTagCompound nbttagcompound) {
            this.a = blockposition;
            this.b = iblockdata;
            this.c = nbttagcompound;
        }
    }

    static enum Mode {
        FORCE(true),
        MOVE(true),
        NORMAL(false);

        private final boolean d;

        private Mode(boolean flag) {
            this.d = flag;
        }

        public boolean a() {
            return this.d;
        }
    }
}
