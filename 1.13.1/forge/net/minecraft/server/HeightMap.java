package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class HeightMap {
    private final DataBits a = new DataBits(9, 256);
    private final PredicateBlock<IBlockData> b;
    private final IChunkAccess c;

    public HeightMap(IChunkAccess ichunkaccess, HeightMap.Type heightmap$type) {
        this.b = PredicateBlocks.a(PredicateBlocks.b(heightmap$type.a()));
        this.c = ichunkaccess;
    }

    public void a() {
        int i = this.c.b() + 16;

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(int j = 0; j < 16; ++j) {
                for(int k = 0; k < 16; ++k) {
                    this.a(j, k, this.a(blockposition$b, j, k, this.b, i));
                }
            }
        }

    }

    public boolean a(int i, int j, int k, @Nullable IBlockData iblockdata) {
        int l = this.a(i, k);
        if (j <= l - 2) {
            return false;
        } else {
            if (this.b.test(iblockdata, this.c, new BlockPosition(i, j, k))) {
                if (j >= l) {
                    this.a(i, k, j + 1);
                    return true;
                }
            } else if (l - 1 == j) {
                this.a(i, k, this.a((BlockPosition.MutableBlockPosition)null, i, k, this.b, j));
                return true;
            }

            return false;
        }
    }

    private int a(@Nullable BlockPosition.MutableBlockPosition blockposition$mutableblockposition, int i, int j, PredicateBlock<IBlockData> predicateblock, int k) {
        if (blockposition$mutableblockposition == null) {
            blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
        }

        for(int l = k - 1; l >= 0; --l) {
            blockposition$mutableblockposition.c(i, l, j);
            IBlockData iblockdata = this.c.getType(blockposition$mutableblockposition);
            if (predicateblock.test(iblockdata, this.c, blockposition$mutableblockposition)) {
                return l + 1;
            }
        }

        return 0;
    }

    public int a(int i, int j) {
        return this.a(b(i, j));
    }

    private int a(int i) {
        return this.a.a(i);
    }

    private void a(int i, int j, int k) {
        this.a.a(b(i, j), k);
    }

    public void a(long[] along) {
        System.arraycopy(along, 0, this.a.a(), 0, along.length);
    }

    public long[] b() {
        return this.a.a();
    }

    private static int b(int i, int j) {
        return i + j * 16;
    }

    public static enum Type {
        WORLD_SURFACE_WG("WORLD_SURFACE_WG", HeightMap.Use.WORLDGEN, new PredicateBlock[]{PredicateBlockType.a(Blocks.AIR)}),
        OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", HeightMap.Use.WORLDGEN, new PredicateBlock[]{PredicateBlockType.a(Blocks.AIR), PredicateBlockLiquid.a()}),
        LIGHT_BLOCKING("LIGHT_BLOCKING", HeightMap.Use.LIVE_WORLD, new PredicateBlock[]{PredicateBlockType.a(Blocks.AIR), PredicateBlockLightTransmission.a()}),
        MOTION_BLOCKING("MOTION_BLOCKING", HeightMap.Use.LIVE_WORLD, new PredicateBlock[]{PredicateBlockType.a(Blocks.AIR), PredicateBlockNotSolidOrLiquid.a()}),
        MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES", HeightMap.Use.LIVE_WORLD, new PredicateBlock[]{PredicateBlockType.a(Blocks.AIR), PredicateBlockTag.a(TagsBlock.LEAVES), PredicateBlockNotSolidOrLiquid.a()}),
        OCEAN_FLOOR("OCEAN_FLOOR", HeightMap.Use.LIVE_WORLD, new PredicateBlock[]{PredicateBlockType.a(Blocks.AIR), PredicateBlockSolid.a()}),
        WORLD_SURFACE("WORLD_SURFACE", HeightMap.Use.LIVE_WORLD, new PredicateBlock[]{PredicateBlockType.a(Blocks.AIR)});

        private final PredicateBlock<IBlockData>[] h;
        private final String i;
        private final HeightMap.Use j;
        private static final Map<String, HeightMap.Type> k = (Map)SystemUtils.a(Maps.newHashMap(), (hashmap) -> {
            for(HeightMap.Type heightmap$type : values()) {
                hashmap.put(heightmap$type.i, heightmap$type);
            }

        });

        private Type(String s1, HeightMap.Use heightmap$use, PredicateBlock... apredicateblock) {
            this.i = s1;
            this.h = apredicateblock;
            this.j = heightmap$use;
        }

        public PredicateBlock<IBlockData>[] a() {
            return this.h;
        }

        public String b() {
            return this.i;
        }

        public HeightMap.Use c() {
            return this.j;
        }

        public static HeightMap.Type a(String s) {
            return (HeightMap.Type)k.get(s);
        }
    }

    public static enum Use {
        WORLDGEN,
        LIVE_WORLD;

        private Use() {
        }
    }
}
