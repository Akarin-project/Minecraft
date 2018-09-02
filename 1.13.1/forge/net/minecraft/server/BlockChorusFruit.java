package net.minecraft.server;

import java.util.Random;

public class BlockChorusFruit extends BlockSprawling {
    protected BlockChorusFruit(Block.Info block$info) {
        super(0.3125F, block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(a, Boolean.valueOf(false))).set(b, Boolean.valueOf(false))).set(c, Boolean.valueOf(false))).set(o, Boolean.valueOf(false))).set(p, Boolean.valueOf(false))).set(q, Boolean.valueOf(false)));
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return this.a(blockactioncontext.getWorld(), blockactioncontext.getClickPosition());
    }

    public IBlockData a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        Block block = iblockaccess.getType(blockposition.down()).getBlock();
        Block block1 = iblockaccess.getType(blockposition.up()).getBlock();
        Block block2 = iblockaccess.getType(blockposition.north()).getBlock();
        Block block3 = iblockaccess.getType(blockposition.east()).getBlock();
        Block block4 = iblockaccess.getType(blockposition.south()).getBlock();
        Block block5 = iblockaccess.getType(blockposition.west()).getBlock();
        return (IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)((IBlockData)this.getBlockData().set(q, Boolean.valueOf(block == this || block == Blocks.CHORUS_FLOWER || block == Blocks.END_STONE))).set(p, Boolean.valueOf(block1 == this || block1 == Blocks.CHORUS_FLOWER))).set(a, Boolean.valueOf(block2 == this || block2 == Blocks.CHORUS_FLOWER))).set(b, Boolean.valueOf(block3 == this || block3 == Blocks.CHORUS_FLOWER))).set(c, Boolean.valueOf(block4 == this || block4 == Blocks.CHORUS_FLOWER))).set(o, Boolean.valueOf(block5 == this || block5 == Blocks.CHORUS_FLOWER));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (!iblockdata.canPlace(generatoraccess, blockposition)) {
            generatoraccess.J().a(blockposition, this, 1);
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        } else {
            Block block = iblockdata1.getBlock();
            boolean flag = block == this || block == Blocks.CHORUS_FLOWER || enumdirection == EnumDirection.DOWN && block == Blocks.END_STONE;
            return (IBlockData)iblockdata.set((IBlockState)r.get(enumdirection), Boolean.valueOf(flag));
        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!iblockdata.canPlace(world, blockposition)) {
            world.setAir(blockposition, true);
        }

    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.CHORUS_FRUIT;
    }

    public int a(IBlockData var1, Random random) {
        return random.nextInt(2);
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean canPlace(IBlockData var1, IWorldReader iworldreader, BlockPosition blockposition) {
        IBlockData iblockdata = iworldreader.getType(blockposition.down());
        boolean flag = !iworldreader.getType(blockposition.up()).isAir() && !iblockdata.isAir();

        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            BlockPosition blockposition1 = blockposition.shift(enumdirection);
            Block block = iworldreader.getType(blockposition1).getBlock();
            if (block == this) {
                if (flag) {
                    return false;
                }

                Block block1 = iworldreader.getType(blockposition1.down()).getBlock();
                if (block1 == this || block1 == Blocks.END_STONE) {
                    return true;
                }
            }
        }

        Block block2 = iblockdata.getBlock();
        return block2 == this || block2 == Blocks.END_STONE;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a, b, c, o, p, q);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
