package net.minecraft.server;

import java.util.Random;

public class BlockFalling extends Block {
    public static boolean instaFall;

    public BlockFalling(Block.Info block$info) {
        super(block$info);
    }

    public void onPlace(IBlockData var1, World world, BlockPosition blockposition, IBlockData var4) {
        world.J().a(blockposition, this, this.a(world));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        generatoraccess.J().a(blockposition, this, this.a(generatoraccess));
        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public void a(IBlockData var1, World world, BlockPosition blockposition, Random var4) {
        if (!world.isClientSide) {
            this.b(world, blockposition);
        }

    }

    private void b(World world, BlockPosition blockposition) {
        if (k(world.getType(blockposition.down())) && blockposition.getY() >= 0) {
            boolean flag = true;
            if (!instaFall && world.areChunksLoadedBetween(blockposition.a(-32, -32, -32), blockposition.a(32, 32, 32))) {
                if (!world.isClientSide) {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)blockposition.getX() + 0.5D, (double)blockposition.getY(), (double)blockposition.getZ() + 0.5D, world.getType(blockposition));
                    this.a(entityfallingblock);
                    world.addEntity(entityfallingblock);
                }
            } else {
                if (world.getType(blockposition).getBlock() == this) {
                    world.setAir(blockposition);
                }

                BlockPosition blockposition1;
                for(blockposition1 = blockposition.down(); k(world.getType(blockposition1)) && blockposition1.getY() > 0; blockposition1 = blockposition1.down()) {
                    ;
                }

                if (blockposition1.getY() > 0) {
                    world.setTypeUpdate(blockposition1.up(), this.getBlockData());
                }
            }

        }
    }

    protected void a(EntityFallingBlock var1) {
    }

    public int a(IWorldReader var1) {
        return 2;
    }

    public static boolean k(IBlockData iblockdata) {
        Block block = iblockdata.getBlock();
        Material material = iblockdata.getMaterial();
        return iblockdata.isAir() || block == Blocks.FIRE || material.isLiquid() || material.isReplaceable();
    }

    public void a(World var1, BlockPosition var2, IBlockData var3, IBlockData var4) {
    }

    public void a(World var1, BlockPosition var2) {
    }
}
