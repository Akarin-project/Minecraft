package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;

public class BlockSprawling extends Block {
    private static final EnumDirection[] t = EnumDirection.values();
    public static final BlockStateBoolean a = BlockProperties.D;
    public static final BlockStateBoolean b = BlockProperties.E;
    public static final BlockStateBoolean c = BlockProperties.F;
    public static final BlockStateBoolean o = BlockProperties.G;
    public static final BlockStateBoolean p = BlockProperties.B;
    public static final BlockStateBoolean q = BlockProperties.C;
    public static final Map<EnumDirection, BlockStateBoolean> r = (Map)SystemUtils.a(Maps.newEnumMap(EnumDirection.class), (enummap) -> {
        enummap.put(EnumDirection.NORTH, a);
        enummap.put(EnumDirection.EAST, b);
        enummap.put(EnumDirection.SOUTH, c);
        enummap.put(EnumDirection.WEST, o);
        enummap.put(EnumDirection.UP, p);
        enummap.put(EnumDirection.DOWN, q);
    });
    protected final VoxelShape[] s;

    protected BlockSprawling(float f, Block.Info block$info) {
        super(block$info);
        this.s = this.a(f);
    }

    private VoxelShape[] a(float f) {
        float f1 = 0.5F - f;
        float f2 = 0.5F + f;
        VoxelShape voxelshape = Block.a((double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f2 * 16.0F), (double)(f2 * 16.0F), (double)(f2 * 16.0F));
        VoxelShape[] avoxelshape = new VoxelShape[t.length];

        for(int i = 0; i < t.length; ++i) {
            EnumDirection enumdirection = t[i];
            avoxelshape[i] = VoxelShapes.a(0.5D + Math.min((double)(-f), (double)enumdirection.getAdjacentX() * 0.5D), 0.5D + Math.min((double)(-f), (double)enumdirection.getAdjacentY() * 0.5D), 0.5D + Math.min((double)(-f), (double)enumdirection.getAdjacentZ() * 0.5D), 0.5D + Math.max((double)f, (double)enumdirection.getAdjacentX() * 0.5D), 0.5D + Math.max((double)f, (double)enumdirection.getAdjacentY() * 0.5D), 0.5D + Math.max((double)f, (double)enumdirection.getAdjacentZ() * 0.5D));
        }

        VoxelShape[] avoxelshape1 = new VoxelShape[64];

        for(int k = 0; k < 64; ++k) {
            VoxelShape voxelshape1 = voxelshape;

            for(int j = 0; j < t.length; ++j) {
                if ((k & 1 << j) != 0) {
                    voxelshape1 = VoxelShapes.a(voxelshape1, avoxelshape[j]);
                }
            }

            avoxelshape1[k] = voxelshape1;
        }

        return avoxelshape1;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return this.s[this.k(iblockdata)];
    }

    protected int k(IBlockData iblockdata) {
        int i = 0;

        for(int j = 0; j < t.length; ++j) {
            if (iblockdata.get((IBlockState)r.get(t[j]))) {
                i |= 1 << j;
            }
        }

        return i;
    }
}
