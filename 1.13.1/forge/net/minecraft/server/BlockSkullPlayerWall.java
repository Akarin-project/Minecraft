package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockSkullPlayerWall extends BlockSkullWall {
    protected BlockSkullPlayerWall(Block.Info block$info) {
        super(BlockSkull.Type.PLAYER, block$info);
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
        Blocks.PLAYER_HEAD.postPlace(world, blockposition, iblockdata, entityliving, itemstack);
    }
}
