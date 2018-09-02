package net.minecraft.server;

import java.util.Random;

public class BlockIceFrost extends BlockIce {
    public static final BlockStateInteger a = BlockProperties.U;

    public BlockIceFrost(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, Integer.valueOf(0)));
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        if ((random.nextInt(3) == 0 || this.a(world, blockposition, 4)) && world.getLightLevel(blockposition) > 11 - iblockdata.get(a) - iblockdata.b(world, blockposition) && this.c(iblockdata, world, blockposition)) {
            try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
                for(EnumDirection enumdirection : EnumDirection.values()) {
                    blockposition$b.j(blockposition).d(enumdirection);
                    IBlockData iblockdata1 = world.getType(blockposition$b);
                    if (iblockdata1.getBlock() == this && !this.c(iblockdata1, world, blockposition$b)) {
                        world.J().a(blockposition$b, this, MathHelper.nextInt(random, 20, 40));
                    }
                }
            }

        } else {
            world.J().a(blockposition, this, MathHelper.nextInt(random, 20, 40));
        }
    }

    private boolean c(IBlockData iblockdata, World world, BlockPosition blockposition) {
        int i = iblockdata.get(a);
        if (i < 3) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(a, Integer.valueOf(i + 1)), 2);
            return false;
        } else {
            this.b(iblockdata, world, blockposition);
            return true;
        }
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1) {
        if (block == this && this.a(world, blockposition, 2)) {
            this.b(iblockdata, world, blockposition);
        }

        super.doPhysics(iblockdata, world, blockposition, block, blockposition1);
    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, int i) {
        int j = 0;

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(EnumDirection enumdirection : EnumDirection.values()) {
                blockposition$b.j(blockposition).d(enumdirection);
                if (iblockaccess.getType(blockposition$b).getBlock() == this) {
                    ++j;
                    if (j >= i) {
                        boolean flag = false;
                        return flag;
                    }
                }
            }

            return true;
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return ItemStack.a;
    }
}
