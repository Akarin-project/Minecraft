package net.minecraft.server;

import java.util.Random;

public class BlockRedstoneOre extends Block {
    public static final BlockStateBoolean a = BlockRedstoneTorch.LIT;

    public BlockRedstoneOre(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)this.getBlockData().set(a, Boolean.valueOf(false)));
    }

    public int m(IBlockData iblockdata) {
        return iblockdata.get(a) ? super.m(iblockdata) : 0;
    }

    public void attack(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
        interact(iblockdata, world, blockposition);
        super.attack(iblockdata, world, blockposition, entityhuman);
    }

    public void stepOn(World world, BlockPosition blockposition, Entity entity) {
        interact(world.getType(blockposition), world, blockposition);
        super.stepOn(world, blockposition, entity);
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand enumhand, EnumDirection enumdirection, float f, float f1, float f2) {
        interact(iblockdata, world, blockposition);
        return super.interact(iblockdata, world, blockposition, entityhuman, enumhand, enumdirection, f, f1, f2);
    }

    private static void interact(IBlockData iblockdata, World world, BlockPosition blockposition) {
        playEffect(world, blockposition);
        if (!iblockdata.get(a)) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(a, Boolean.valueOf(true)), 3);
        }

    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (iblockdata.get(a)) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(a, Boolean.valueOf(false)), 3);
        }

    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.REDSTONE;
    }

    public int getDropCount(IBlockData iblockdata, int i, World var3, BlockPosition var4, Random random) {
        return this.a(iblockdata, random) + random.nextInt(i + 1);
    }

    public int a(IBlockData var1, Random random) {
        return 4 + random.nextInt(2);
    }

    public void dropNaturally(IBlockData iblockdata, World world, BlockPosition blockposition, float f, int i) {
        super.dropNaturally(iblockdata, world, blockposition, f, i);
        if (this.getDropType(iblockdata, world, blockposition, i) != this) {
            int j = 1 + world.random.nextInt(5);
            this.dropExperience(world, blockposition, j);
        }

    }

    private static void playEffect(World world, BlockPosition blockposition) {
        double d0 = 0.5625D;
        Random random = world.random;

        for(EnumDirection enumdirection : EnumDirection.values()) {
            BlockPosition blockposition1 = blockposition.shift(enumdirection);
            if (!world.getType(blockposition1).f(world, blockposition1)) {
                EnumDirection.EnumAxis enumdirection$enumaxis = enumdirection.k();
                double d1 = enumdirection$enumaxis == EnumDirection.EnumAxis.X ? 0.5D + 0.5625D * (double)enumdirection.getAdjacentX() : (double)random.nextFloat();
                double d2 = enumdirection$enumaxis == EnumDirection.EnumAxis.Y ? 0.5D + 0.5625D * (double)enumdirection.getAdjacentY() : (double)random.nextFloat();
                double d3 = enumdirection$enumaxis == EnumDirection.EnumAxis.Z ? 0.5D + 0.5625D * (double)enumdirection.getAdjacentZ() : (double)random.nextFloat();
                world.addParticle(ParticleParamRedstone.a, (double)blockposition.getX() + d1, (double)blockposition.getY() + d2, (double)blockposition.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }
}
