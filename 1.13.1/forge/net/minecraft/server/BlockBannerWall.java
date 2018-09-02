package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class BlockBannerWall extends BlockBannerAbstract {
    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private static final Map<EnumDirection, VoxelShape> b = Maps.newEnumMap(ImmutableMap.of(EnumDirection.NORTH, Block.a(0.0D, 0.0D, 14.0D, 16.0D, 12.5D, 16.0D), EnumDirection.SOUTH, Block.a(0.0D, 0.0D, 0.0D, 16.0D, 12.5D, 2.0D), EnumDirection.WEST, Block.a(14.0D, 0.0D, 0.0D, 16.0D, 12.5D, 16.0D), EnumDirection.EAST, Block.a(0.0D, 0.0D, 0.0D, 2.0D, 12.5D, 16.0D)));

    public BlockBannerWall(EnumColor enumcolor, Block.Info block$info) {
        super(enumcolor, block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH));
    }

    public String m() {
        return this.getItem().getName();
    }

    public boolean canPlace(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
        return iworldreader.getType(blockposition.shift(((EnumDirection)iblockdata.get(a)).opposite())).getMaterial().isBuildable();
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == ((EnumDirection)iblockdata.get(a)).opposite() && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return (VoxelShape)b.get(iblockdata.get(a));
    }

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
