package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class BlockSkullWall extends BlockSkullAbstract {
    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private static final Map<EnumDirection, VoxelShape> b = Maps.newEnumMap(ImmutableMap.of(EnumDirection.NORTH, Block.a(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D), EnumDirection.SOUTH, Block.a(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D), EnumDirection.EAST, Block.a(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D), EnumDirection.WEST, Block.a(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)));

    protected BlockSkullWall(BlockSkull.a blockskull$a, Block.Info block$info) {
        super(blockskull$a, block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH));
    }

    public String m() {
        return this.getItem().getName();
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
                if (!world.getType(blockposition.shift(enumdirection)).a(blockactioncontext)) {
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
