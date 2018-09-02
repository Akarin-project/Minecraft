package net.minecraft.server;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LightEngine implements ILightEngine {
    private static final Logger a = LogManager.getLogger();
    private static final EnumDirection[] b = EnumDirection.values();
    private final IntPriorityQueue c = new IntArrayFIFOQueue(786);

    public LightEngine() {
    }

    public int a(IWorldReader iworldreader, BlockPosition blockposition) {
        return iworldreader.getBrightness(this.a(), blockposition);
    }

    public void a(IWorldWriter iworldwriter, BlockPosition blockposition, int i) {
        iworldwriter.a(this.a(), blockposition, i);
    }

    protected int a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockaccess.getType(blockposition).b(iblockaccess, blockposition);
    }

    protected int b(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockaccess.getType(blockposition).e();
    }

    private int a(@Nullable EnumDirection enumdirection, int i, int j, int k, int l) {
        int i1 = 7;
        if (enumdirection != null) {
            i1 = enumdirection.ordinal();
        }

        return i1 << 24 | i << 18 | j << 10 | k << 4 | l << 0;
    }

    private int a(int i) {
        return i >> 18 & 63;
    }

    private int b(int i) {
        return i >> 10 & 255;
    }

    private int c(int i) {
        return i >> 4 & 63;
    }

    private int d(int i) {
        return i >> 0 & 15;
    }

    @Nullable
    private EnumDirection e(int i) {
        int j = i >> 24 & 7;
        return j == 7 ? null : EnumDirection.values()[i >> 24 & 7];
    }

    protected void a(GeneratorAccess generatoraccess, ChunkCoordIntPair chunkcoordintpair) {
        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            while(!this.c.isEmpty()) {
                int i = this.c.dequeueInt();
                int j = this.d(i);
                int k = this.a(i) - 16;
                int l = this.b(i);
                int i1 = this.c(i) - 16;
                EnumDirection enumdirection = this.e(i);

                for(EnumDirection enumdirection1 : b) {
                    if (enumdirection1 != enumdirection) {
                        int j1 = k + enumdirection1.getAdjacentX();
                        int k1 = l + enumdirection1.getAdjacentY();
                        int l1 = i1 + enumdirection1.getAdjacentZ();
                        if (k1 <= 255 && k1 >= 0) {
                            blockposition$b.f(j1 + chunkcoordintpair.d(), k1, l1 + chunkcoordintpair.e());
                            int i2 = this.a((IBlockAccess)generatoraccess, blockposition$b);
                            int j2 = j - Math.max(i2, 1);
                            if (j2 > 0 && j2 > this.a((IWorldReader)generatoraccess, blockposition$b)) {
                                this.a(generatoraccess, blockposition$b, j2);
                                this.a(chunkcoordintpair, blockposition$b, j2);
                            }
                        }
                    }
                }
            }
        }

    }

    protected void a(ChunkCoordIntPair chunkcoordintpair, int i, int j, int k, int l) {
        int i1 = i - chunkcoordintpair.d() + 16;
        int j1 = k - chunkcoordintpair.e() + 16;
        this.c.enqueue(this.a((EnumDirection)null, i1, j, j1, l));
    }

    protected void a(ChunkCoordIntPair chunkcoordintpair, BlockPosition blockposition, int i) {
        this.a(chunkcoordintpair, blockposition.getX(), blockposition.getY(), blockposition.getZ(), i);
    }
}
