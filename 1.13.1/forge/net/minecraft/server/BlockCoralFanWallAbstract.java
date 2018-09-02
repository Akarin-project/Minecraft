package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class BlockCoralFanWallAbstract extends BlockCoralFanAbstract {
    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private static final Map<EnumDirection, VoxelShape> c = Maps.newEnumMap(ImmutableMap.of(EnumDirection.NORTH, Block.a(0.0D, 4.0D, 5.0D, 16.0D, 12.0D, 16.0D), EnumDirection.SOUTH, Block.a(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 11.0D), EnumDirection.WEST, Block.a(5.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D), EnumDirection.EAST, Block.a(0.0D, 4.0D, 0.0D, 11.0D, 12.0D, 16.0D)));

    protected BlockCoralFanWallAbstract(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH)).set(b, Boolean.valueOf(true)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return (VoxelShape)c.get(iblockdata.get(a));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(a, enumblockrotation.a((EnumDirection)iblockdata.get(a)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(a)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a, b);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData var3, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition var6) {
        if (iblockdata.get(b)) {
            generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
        }

        return enumdirection.opposite() == iblockdata.get(a) && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : iblockdata;
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(a);
        BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
        IBlockData iblockdata1 = iworldreader.getType(blockposition1);
        return iblockdata1.c(iworldreader, blockposition1, enumdirection) == EnumBlockFaceShape.SOLID && !b(iblockdata1.getBlock());
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = super.getPlacedState(blockactioncontext);
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        EnumDirection[] aenumdirection = blockactioncontext.e();

        for(EnumDirection enumdirection : aenumdirection) {
            if (enumdirection.k().c()) {
                iblockdata = (IBlockData)iblockdata.set(a, enumdirection.opposite());
                if (iblockdata.canPlace(world, blockposition)) {
                    return iblockdata;
                }
            }
        }

        return null;
    }
}
