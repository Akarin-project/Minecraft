package net.minecraft.server;

import java.util.Map;

public class BlockTall extends Block implements IFluidSource, IFluidContainer {
    public static final BlockStateBoolean NORTH = BlockSprawling.a;
    public static final BlockStateBoolean EAST = BlockSprawling.b;
    public static final BlockStateBoolean SOUTH = BlockSprawling.c;
    public static final BlockStateBoolean WEST = BlockSprawling.o;
    public static final BlockStateBoolean p = BlockProperties.y;
    protected static final Map<EnumDirection, BlockStateBoolean> q = (Map)BlockSprawling.r.entrySet().stream().filter((entry) -> {
        return ((EnumDirection)entry.getKey()).k().c();
    }).collect(SystemUtils.a());
    protected final VoxelShape[] r;
    protected final VoxelShape[] s;

    protected BlockTall(float f, float f1, float f2, float f3, float f4, Block.Info block$info) {
        super(block$info);
        this.r = this.a(f, f1, f4, 0.0F, f4);
        this.s = this.a(f, f1, f2, 0.0F, f3);
    }

    protected VoxelShape[] a(float f, float f1, float f2, float f3, float f4) {
        float f5 = 8.0F - f;
        float f6 = 8.0F + f;
        float f7 = 8.0F - f1;
        float f8 = 8.0F + f1;
        VoxelShape voxelshape = Block.a((double)f5, 0.0D, (double)f5, (double)f6, (double)f2, (double)f6);
        VoxelShape voxelshape1 = Block.a((double)f7, (double)f3, 0.0D, (double)f8, (double)f4, (double)f8);
        VoxelShape voxelshape2 = Block.a((double)f7, (double)f3, (double)f7, (double)f8, (double)f4, 16.0D);
        VoxelShape voxelshape3 = Block.a(0.0D, (double)f3, (double)f7, (double)f8, (double)f4, (double)f8);
        VoxelShape voxelshape4 = Block.a((double)f7, (double)f3, (double)f7, 16.0D, (double)f4, (double)f8);
        VoxelShape voxelshape5 = VoxelShapes.a(voxelshape1, voxelshape4);
        VoxelShape voxelshape6 = VoxelShapes.a(voxelshape2, voxelshape3);
        VoxelShape[] avoxelshape = new VoxelShape[]{VoxelShapes.a(), voxelshape2, voxelshape3, voxelshape6, voxelshape1, VoxelShapes.a(voxelshape2, voxelshape1), VoxelShapes.a(voxelshape3, voxelshape1), VoxelShapes.a(voxelshape6, voxelshape1), voxelshape4, VoxelShapes.a(voxelshape2, voxelshape4), VoxelShapes.a(voxelshape3, voxelshape4), VoxelShapes.a(voxelshape6, voxelshape4), voxelshape5, VoxelShapes.a(voxelshape2, voxelshape5), VoxelShapes.a(voxelshape3, voxelshape5), VoxelShapes.a(voxelshape6, voxelshape5)};

        for(int i = 0; i < 16; ++i) {
            avoxelshape[i] = VoxelShapes.a(voxelshape, avoxelshape[i]);
        }

        return avoxelshape;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return this.s[this.k(iblockdata)];
    }

    public VoxelShape f(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return this.r[this.k(iblockdata)];
    }

    private static int a(EnumDirection enumdirection) {
        return 1 << enumdirection.get2DRotationValue();
    }

    protected int k(IBlockData iblockdata) {
        int i = 0;
        if (iblockdata.get(NORTH)) {
            i |= a(EnumDirection.NORTH);
        }

        if (iblockdata.get(EAST)) {
            i |= a(EnumDirection.EAST);
        }

        if (iblockdata.get(SOUTH)) {
            i |= a(EnumDirection.SOUTH);
        }

        if (iblockdata.get(WEST)) {
            i |= a(EnumDirection.WEST);
        }

        return i;
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (iblockdata.get(p)) {
            generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(p, Boolean.valueOf(false)), 3);
            return FluidTypes.c;
        } else {
            return FluidTypes.a;
        }
    }

    public Fluid h(IBlockData iblockdata) {
        return iblockdata.get(p) ? FluidTypes.c.a(false) : super.h(iblockdata);
    }

    public boolean canPlace(IBlockAccess var1, BlockPosition var2, IBlockData iblockdata, FluidType fluidtype) {
        return !iblockdata.get(p) && fluidtype == FluidTypes.c;
    }

    public boolean place(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
        if (!iblockdata.get(p) && fluid.c() == FluidTypes.c) {
            if (!generatoraccess.e()) {
                generatoraccess.setTypeAndData(blockposition, (IBlockData)iblockdata.set(p, Boolean.valueOf(true)), 3);
                generatoraccess.I().a(blockposition, fluid.c(), fluid.c().a(generatoraccess));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch(enumblockrotation) {
        case CLOCKWISE_180:
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(SOUTH))).set(EAST, iblockdata.get(WEST))).set(SOUTH, iblockdata.get(NORTH))).set(WEST, iblockdata.get(EAST));
        case COUNTERCLOCKWISE_90:
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(EAST))).set(EAST, iblockdata.get(SOUTH))).set(SOUTH, iblockdata.get(WEST))).set(WEST, iblockdata.get(NORTH));
        case CLOCKWISE_90:
            return (IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(WEST))).set(EAST, iblockdata.get(NORTH))).set(SOUTH, iblockdata.get(EAST))).set(WEST, iblockdata.get(SOUTH));
        default:
            return iblockdata;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        switch(enumblockmirror) {
        case LEFT_RIGHT:
            return (IBlockData)((IBlockData)iblockdata.set(NORTH, iblockdata.get(SOUTH))).set(SOUTH, iblockdata.get(NORTH));
        case FRONT_BACK:
            return (IBlockData)((IBlockData)iblockdata.set(EAST, iblockdata.get(WEST))).set(WEST, iblockdata.get(EAST));
        default:
            return super.a(iblockdata, enumblockmirror);
        }
    }
}
