package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class BlockTorchWall extends BlockTorch {
    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private static final Map<EnumDirection, VoxelShape> b = Maps.newEnumMap(ImmutableMap.of(EnumDirection.NORTH, Block.a(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), EnumDirection.SOUTH, Block.a(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), EnumDirection.WEST, Block.a(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), EnumDirection.EAST, Block.a(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));

    protected BlockTorchWall(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH));
    }

    public String m() {
        return this.getItem().getName();
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return (VoxelShape)b.get(iblockdata.get(a));
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(a);
        BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
        IBlockData iblockdata1 = iworldreader.getType(blockposition1);
        return iblockdata1.c(iworldreader, blockposition1, enumdirection) == EnumBlockFaceShape.SOLID && !b(iblockdata1.getBlock());
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = this.getBlockData();
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        EnumDirection[] aenumdirection = blockactioncontext.e();

        for(EnumDirection enumdirection : aenumdirection) {
            if (enumdirection.k().c()) {
                EnumDirection enumdirection1 = enumdirection.opposite();
                iblockdata = (IBlockData)iblockdata.set(a, enumdirection1);
                if (iblockdata.canPlace(world, blockposition)) {
                    return iblockdata;
                }
            }
        }

        return null;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData var3, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition var6) {
        return enumdirection.opposite() == iblockdata.get(a) && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : iblockdata;
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
