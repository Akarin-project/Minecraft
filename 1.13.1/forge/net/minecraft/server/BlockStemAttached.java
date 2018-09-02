package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class BlockStemAttached extends BlockPlant {
    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private final BlockStemmed b;
    private static final Map<EnumDirection, VoxelShape> c = Maps.newEnumMap(ImmutableMap.of(EnumDirection.SOUTH, Block.a(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 16.0D), EnumDirection.WEST, Block.a(0.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D), EnumDirection.NORTH, Block.a(6.0D, 0.0D, 0.0D, 10.0D, 10.0D, 10.0D), EnumDirection.EAST, Block.a(6.0D, 0.0D, 6.0D, 16.0D, 10.0D, 10.0D)));

    protected BlockStemAttached(BlockStemmed blockstemmed, Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH));
        this.b = blockstemmed;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return (VoxelShape)c.get(iblockdata.get(a));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return iblockdata1.getBlock() != this.b && enumdirection == iblockdata.get(a) ? (IBlockData)this.b.d().getBlockData().set(BlockStem.AGE, Integer.valueOf(7)) : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    protected boolean b(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return iblockdata.getBlock() == Blocks.FARMLAND;
    }

    protected Item b() {
        if (this.b == Blocks.PUMPKIN) {
            return Items.PUMPKIN_SEEDS;
        } else {
            return this.b == Blocks.MELON ? Items.MELON_SEEDS : Items.AIR;
        }
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return new ItemStack(this.b());
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(a, enumblockrotation.a((EnumDirection)iblockdata.get(a)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(a)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }
}
