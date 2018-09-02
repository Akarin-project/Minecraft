package net.minecraft.server;

import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class BlockHugeMushroom extends Block {
    public static final BlockStateBoolean a = BlockSprawling.a;
    public static final BlockStateBoolean b = BlockSprawling.b;
    public static final BlockStateBoolean c = BlockSprawling.c;
    public static final BlockStateBoolean o = BlockSprawling.o;
    public static final BlockStateBoolean p = BlockSprawling.p;
    public static final BlockStateBoolean q = BlockSprawling.q;
    private static final Map<EnumDirection, BlockStateBoolean> r = BlockSprawling.r;
    @Nullable
    private final Block s;

    public BlockHugeMushroom(@Nullable Block block, Block.Info block$info) {
        super(block$info);
        this.s = block;
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(a, Boolean.valueOf(true))).set(b, Boolean.valueOf(true))).set(c, Boolean.valueOf(true))).set(o, Boolean.valueOf(true))).set(p, Boolean.valueOf(true))).set(q, Boolean.valueOf(true)));
    }

    public int a(IBlockData var1, Random random) {
        return Math.max(0, random.nextInt(9) - 6);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return (IMaterial)(this.s == null ? Items.AIR : this.s);
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)this.getBlockData().set(q, Boolean.valueOf(this != world.getType(blockposition.down()).getBlock()))).set(p, Boolean.valueOf(this != world.getType(blockposition.up()).getBlock()))).set(a, Boolean.valueOf(this != world.getType(blockposition.north()).getBlock()))).set(b, Boolean.valueOf(this != world.getType(blockposition.east()).getBlock()))).set(c, Boolean.valueOf(this != world.getType(blockposition.south()).getBlock()))).set(o, Boolean.valueOf(this != world.getType(blockposition.west()).getBlock()));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return iblockdata1.getBlock() == this ? (IBlockData)iblockdata.set((IBlockState)r.get(enumdirection), Boolean.valueOf(false)) : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set((IBlockState)r.get(enumblockrotation.a(EnumDirection.NORTH)), iblockdata.get(a))).set((IBlockState)r.get(enumblockrotation.a(EnumDirection.SOUTH)), iblockdata.get(c))).set((IBlockState)r.get(enumblockrotation.a(EnumDirection.EAST)), iblockdata.get(b))).set((IBlockState)r.get(enumblockrotation.a(EnumDirection.WEST)), iblockdata.get(o))).set((IBlockState)r.get(enumblockrotation.a(EnumDirection.UP)), iblockdata.get(p))).set((IBlockState)r.get(enumblockrotation.a(EnumDirection.DOWN)), iblockdata.get(q));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)iblockdata.set((IBlockState)r.get(enumblockmirror.b(EnumDirection.NORTH)), iblockdata.get(a))).set((IBlockState)r.get(enumblockmirror.b(EnumDirection.SOUTH)), iblockdata.get(c))).set((IBlockState)r.get(enumblockmirror.b(EnumDirection.EAST)), iblockdata.get(b))).set((IBlockState)r.get(enumblockmirror.b(EnumDirection.WEST)), iblockdata.get(o))).set((IBlockState)r.get(enumblockmirror.b(EnumDirection.UP)), iblockdata.get(p))).set((IBlockState)r.get(enumblockmirror.b(EnumDirection.DOWN)), iblockdata.get(q));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(p, q, a, b, c, o);
    }
}
