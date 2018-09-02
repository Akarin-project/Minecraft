package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkConverter {
    private static final Logger b = LogManager.getLogger();
    public static final ChunkConverter a = new ChunkConverter();
    private static final EnumDirection8[] c = EnumDirection8.values();
    private final EnumSet<EnumDirection8> d;
    private final int[][] e;
    private static final Map<Block, ChunkConverter.a> f = new IdentityHashMap();
    private static final Set<ChunkConverter.a> g = Sets.newHashSet();

    private ChunkConverter() {
        this.d = EnumSet.noneOf(EnumDirection8.class);
        this.e = new int[16][];
    }

    public ChunkConverter(NBTTagCompound nbttagcompound) {
        this();
        if (nbttagcompound.hasKeyOfType("Indices", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Indices");

            for(int i = 0; i < this.e.length; ++i) {
                String s = String.valueOf(i);
                if (nbttagcompound1.hasKeyOfType(s, 11)) {
                    this.e[i] = nbttagcompound1.getIntArray(s);
                }
            }
        }

        int j = nbttagcompound.getInt("Sides");

        for(EnumDirection8 enumdirection8 : EnumDirection8.values()) {
            if ((j & 1 << enumdirection8.ordinal()) != 0) {
                this.d.add(enumdirection8);
            }
        }

    }

    public void a(Chunk chunk) {
        this.b(chunk);

        for(EnumDirection8 enumdirection8 : c) {
            a(chunk, enumdirection8);
        }

        World world = chunk.getWorld();
        g.forEach((chunkconverter$a) -> {
            chunkconverter$a.a(world);
        });
    }

    private static void a(Chunk chunk, EnumDirection8 enumdirection8) {
        World world = chunk.getWorld();
        if (chunk.F().d.remove(enumdirection8)) {
            Set set = enumdirection8.a();
            boolean flag = false;
            boolean flag1 = true;
            boolean flag2 = set.contains(EnumDirection.EAST);
            boolean flag3 = set.contains(EnumDirection.WEST);
            boolean flag4 = set.contains(EnumDirection.SOUTH);
            boolean flag5 = set.contains(EnumDirection.NORTH);
            boolean flag6 = set.size() == 1;
            int i = (chunk.locX << 4) + (!flag6 || !flag5 && !flag4 ? (flag3 ? 0 : 15) : 1);
            int j = (chunk.locX << 4) + (!flag6 || !flag5 && !flag4 ? (flag3 ? 0 : 15) : 14);
            int k = (chunk.locZ << 4) + (!flag6 || !flag2 && !flag3 ? (flag5 ? 0 : 15) : 1);
            int l = (chunk.locZ << 4) + (!flag6 || !flag2 && !flag3 ? (flag5 ? 0 : 15) : 14);
            EnumDirection[] aenumdirection = EnumDirection.values();
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

            for(BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 : BlockPosition.b(i, 0, k, j, world.getHeight() - 1, l)) {
                IBlockData iblockdata = world.getType(blockposition$mutableblockposition1);
                IBlockData iblockdata1 = iblockdata;

                for(EnumDirection enumdirection : aenumdirection) {
                    blockposition$mutableblockposition.g(blockposition$mutableblockposition1).c(enumdirection);
                    iblockdata1 = a(iblockdata1, enumdirection, world, blockposition$mutableblockposition1, blockposition$mutableblockposition);
                }

                Block.a(iblockdata, iblockdata1, world, blockposition$mutableblockposition1, 18);
            }

        }
    }

    private static IBlockData a(IBlockData iblockdata, EnumDirection enumdirection, GeneratorAccess generatoraccess, BlockPosition.MutableBlockPosition blockposition$mutableblockposition, BlockPosition.MutableBlockPosition blockposition$mutableblockposition1) {
        return ((ChunkConverter.a)f.getOrDefault(iblockdata.getBlock(), ChunkConverter.Type.DEFAULT)).a(iblockdata, enumdirection, generatoraccess.getType(blockposition$mutableblockposition1), generatoraccess, blockposition$mutableblockposition, blockposition$mutableblockposition1);
    }

    private void b(Chunk chunk) {
        try (
            BlockPosition.b blockposition$b = BlockPosition.b.r();
            BlockPosition.b blockposition$b1 = BlockPosition.b.r();
        ) {
            World world = chunk.getWorld();

            for(int i = 0; i < 16; ++i) {
                ChunkSection chunksection = chunk.getSections()[i];
                int[] aint = this.e[i];
                this.e[i] = null;
                if (chunksection != null && aint != null && aint.length > 0) {
                    EnumDirection[] aenumdirection = EnumDirection.values();
                    DataPaletteBlock datapaletteblock = chunksection.getBlocks();

                    for(int j : aint) {
                        int k = j & 15;
                        int l = j >> 8 & 15;
                        int i1 = j >> 4 & 15;
                        blockposition$b.f(k + (chunk.locX << 4), l + (i << 4), i1 + (chunk.locZ << 4));
                        IBlockData iblockdata = (IBlockData)datapaletteblock.a(j);
                        IBlockData iblockdata1 = iblockdata;

                        for(EnumDirection enumdirection : aenumdirection) {
                            blockposition$b1.j(blockposition$b).d(enumdirection);
                            if (blockposition$b.getX() >> 4 == chunk.locX && blockposition$b.getZ() >> 4 == chunk.locZ) {
                                iblockdata1 = a(iblockdata1, enumdirection, world, blockposition$b, blockposition$b1);
                            }
                        }

                        Block.a(iblockdata, iblockdata1, world, blockposition$b, 18);
                    }
                }
            }

            for(int j1 = 0; j1 < this.e.length; ++j1) {
                if (this.e[j1] != null) {
                    b.warn("Discarding update data for section {} for chunk ({} {})", j1, chunk.locX, chunk.locZ);
                }

                this.e[j1] = null;
            }
        }

    }

    public boolean a() {
        for(int[] aint : this.e) {
            if (aint != null) {
                return false;
            }
        }

        return this.d.isEmpty();
    }

    public NBTTagCompound b() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        for(int i = 0; i < this.e.length; ++i) {
            String s = String.valueOf(i);
            if (this.e[i] != null && this.e[i].length != 0) {
                nbttagcompound1.setIntArray(s, this.e[i]);
            }
        }

        if (!nbttagcompound1.isEmpty()) {
            nbttagcompound.set("Indices", nbttagcompound1);
        }

        int j = 0;

        for(EnumDirection8 enumdirection8 : this.d) {
            j |= 1 << enumdirection8.ordinal();
        }

        nbttagcompound.setByte("Sides", (byte)j);
        return nbttagcompound;
    }

    static enum Type implements ChunkConverter.a {
        BLACKLIST(new Block[]{Blocks.OBSERVER, Blocks.NETHER_PORTAL, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL, Blocks.DRAGON_EGG, Blocks.GRAVEL, Blocks.SAND, Blocks.RED_SAND, Blocks.SIGN, Blocks.WALL_SIGN}) {
            public IBlockData a(IBlockData iblockdata, EnumDirection var2, IBlockData var3, GeneratorAccess var4, BlockPosition var5, BlockPosition var6) {
                return iblockdata;
            }
        },
        DEFAULT(new Block[0]) {
            public IBlockData a(IBlockData iblockdata, EnumDirection enumdirection, IBlockData var3, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
                return iblockdata.updateState(enumdirection, generatoraccess.getType(blockposition1), generatoraccess, blockposition, blockposition1);
            }
        },
        CHEST(new Block[]{Blocks.CHEST, Blocks.TRAPPED_CHEST}) {
            public IBlockData a(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
                if (iblockdata1.getBlock() == iblockdata.getBlock() && enumdirection.k().c() && iblockdata.get(BlockChest.b) == BlockPropertyChestType.SINGLE && iblockdata1.get(BlockChest.b) == BlockPropertyChestType.SINGLE) {
                    EnumDirection enumdirection1 = (EnumDirection)iblockdata.get(BlockChest.FACING);
                    if (enumdirection.k() != enumdirection1.k() && enumdirection1 == iblockdata1.get(BlockChest.FACING)) {
                        BlockPropertyChestType blockpropertychesttype = enumdirection == enumdirection1.e() ? BlockPropertyChestType.LEFT : BlockPropertyChestType.RIGHT;
                        generatoraccess.setTypeAndData(blockposition1, (IBlockData)iblockdata1.set(BlockChest.b, blockpropertychesttype.a()), 18);
                        if (enumdirection1 == EnumDirection.NORTH || enumdirection1 == EnumDirection.EAST) {
                            TileEntity tileentity = generatoraccess.getTileEntity(blockposition);
                            TileEntity tileentity1 = generatoraccess.getTileEntity(blockposition1);
                            if (tileentity instanceof TileEntityChest && tileentity1 instanceof TileEntityChest) {
                                TileEntityChest.a((TileEntityChest)tileentity, (TileEntityChest)tileentity1);
                            }
                        }

                        return (IBlockData)iblockdata.set(BlockChest.b, blockpropertychesttype);
                    }
                }

                return iblockdata;
            }
        },
        LEAVES(true, new Block[]{Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES}) {
            private final ThreadLocal<List<ObjectSet<BlockPosition>>> g = ThreadLocal.withInitial(() -> {
                return Lists.newArrayListWithCapacity(7);
            });

            public IBlockData a(IBlockData iblockdata, EnumDirection enumdirection, IBlockData var3, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
                IBlockData iblockdata1 = iblockdata.updateState(enumdirection, generatoraccess.getType(blockposition1), generatoraccess, blockposition, blockposition1);
                if (iblockdata != iblockdata1) {
                    int i = iblockdata1.get(BlockProperties.ab);
                    List list = (List)this.g.get();
                    if (list.isEmpty()) {
                        for(int j = 0; j < 7; ++j) {
                            list.add(new ObjectOpenHashSet());
                        }
                    }

                    ((ObjectSet)list.get(i)).add(blockposition.h());
                }

                return iblockdata;
            }

            public void a(GeneratorAccess generatoraccess) {
                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
                List list = (List)this.g.get();

                for(int i = 2; i < list.size(); ++i) {
                    int j = i - 1;
                    ObjectSet objectset = (ObjectSet)list.get(j);
                    ObjectSet objectset1 = (ObjectSet)list.get(i);
                    ObjectIterator objectiterator = objectset.iterator();

                    while(objectiterator.hasNext()) {
                        BlockPosition blockposition = (BlockPosition)objectiterator.next();
                        IBlockData iblockdata = generatoraccess.getType(blockposition);
                        if (iblockdata.get(BlockProperties.ab) >= j) {
                            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(BlockProperties.ab, Integer.valueOf(j)), 18);
                            if (i != 7) {
                                for(EnumDirection enumdirection : f) {
                                    blockposition$mutableblockposition.g(blockposition).c(enumdirection);
                                    IBlockData iblockdata1 = generatoraccess.getType(blockposition$mutableblockposition);
                                    if (iblockdata1.b(BlockProperties.ab) && iblockdata.get(BlockProperties.ab) > i) {
                                        objectset1.add(blockposition$mutableblockposition.h());
                                    }
                                }
                            }
                        }
                    }
                }

                list.clear();
            }
        },
        STEM_BLOCK(new Block[]{Blocks.MELON_STEM, Blocks.PUMPKIN_STEM}) {
            public IBlockData a(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess var4, BlockPosition var5, BlockPosition var6) {
                if (iblockdata.get(BlockStem.AGE) == 7) {
                    BlockStemmed blockstemmed = ((BlockStem)iblockdata.getBlock()).e();
                    if (iblockdata1.getBlock() == blockstemmed) {
                        return (IBlockData)blockstemmed.e().getBlockData().set(BlockFacingHorizontal.FACING, enumdirection);
                    }
                }

                return iblockdata;
            }
        };

        public static final EnumDirection[] f = EnumDirection.values();

        private Type(Block... ablock) {
            this(false, ablock);
        }

        private Type(boolean flag, Block... ablock) {
            for(Block block : ablock) {
                ChunkConverter.f.put(block, this);
            }

            if (flag) {
                ChunkConverter.g.add(this);
            }

        }
    }

    public interface a {
        IBlockData a(IBlockData var1, EnumDirection var2, IBlockData var3, GeneratorAccess var4, BlockPosition var5, BlockPosition var6);

        default void a(GeneratorAccess var1) {
        }
    }
}
