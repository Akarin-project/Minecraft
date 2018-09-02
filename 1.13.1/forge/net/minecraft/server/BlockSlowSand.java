package net.minecraft.server;

import java.util.Random;

public class BlockSlowSand extends Block {
    protected static final VoxelShape a = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public BlockSlowSand(Block.Info block$info) {
        super(block$info);
    }

    public VoxelShape f(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return a;
    }

    public void a(IBlockData var1, World var2, BlockPosition var3, Entity entity) {
        entity.motX *= 0.4D;
        entity.motZ *= 0.4D;
    }

    public void a(IBlockData var1, World world, BlockPosition blockposition, Random var4) {
        BlockBubbleColumn.a(world, blockposition.up(), false);
    }

    public void doPhysics(IBlockData var1, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        world.J().a(blockposition, this, this.a(world));
    }

    public int a(IWorldReader var1) {
        return 20;
    }

    public void onPlace(IBlockData var1, World world, BlockPosition blockposition, IBlockData var4) {
        world.J().a(blockposition, this, this.a(world));
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
