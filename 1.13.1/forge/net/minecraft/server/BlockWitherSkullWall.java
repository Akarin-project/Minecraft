package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockWitherSkullWall extends BlockSkullWall {
    protected BlockWitherSkullWall(Block.Info block$info) {
        super(BlockSkull.Type.WITHER_SKELETON, block$info);
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
        Blocks.WITHER_SKELETON_SKULL.postPlace(world, blockposition, iblockdata, entityliving, itemstack);
    }
}
