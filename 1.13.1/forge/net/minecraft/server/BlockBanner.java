package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;

public class BlockBanner extends BlockBannerAbstract {
    public static final BlockStateInteger ROTATION = BlockProperties.an;
    private static final Map<EnumColor, Block> b = Maps.newHashMap();
    private static final VoxelShape c = Block.a(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public BlockBanner(EnumColor enumcolor, Block.Info block$info) {
        super(enumcolor, block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(ROTATION, Integer.valueOf(0)));
        b.put(enumcolor, this);
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        return iworldreader.getType(blockposition.down()).getMaterial().isBuildable();
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return c;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(ROTATION, Integer.valueOf(MathHelper.floor((double)((180.0F + blockactioncontext.h()) * 16.0F / 360.0F) + 0.5D) & 15));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == EnumDirection.DOWN && !iblockdata.canPlace(generatoraccess, blockposition) ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(ROTATION, Integer.valueOf(enumblockrotation.a(iblockdata.get(ROTATION), 16)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return (IBlockData)iblockdata.set(ROTATION, Integer.valueOf(enumblockmirror.a(iblockdata.get(ROTATION), 16)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(ROTATION);
    }

    public static Block a(EnumColor enumcolor) {
        return (Block)b.getOrDefault(enumcolor, Blocks.WHITE_BANNER);
    }
}
