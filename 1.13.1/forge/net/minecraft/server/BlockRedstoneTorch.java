package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch {
    public static final BlockStateBoolean LIT = BlockProperties.o;
    private static final Map<IBlockAccess, List<BlockRedstoneTorch.RedstoneUpdateInfo>> b = Maps.newHashMap();

    protected BlockRedstoneTorch(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(LIT, Boolean.valueOf(true)));
    }

    public int a(IWorldReader var1) {
        return 2;
    }

    public void onPlace(IBlockData var1, World world, BlockPosition blockposition, IBlockData var4) {
        for(EnumDirection enumdirection : EnumDirection.values()) {
            world.applyPhysics(blockposition.shift(enumdirection), this);
        }

    }

    public void remove(IBlockData var1, World world, BlockPosition blockposition, IBlockData var4, boolean flag) {
        if (!flag) {
            for(EnumDirection enumdirection : EnumDirection.values()) {
                world.applyPhysics(blockposition.shift(enumdirection), this);
            }

        }
    }

    public int a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3, EnumDirection enumdirection) {
        return iblockdata.get(LIT) && EnumDirection.UP != enumdirection ? 15 : 0;
    }

    protected boolean a(World world, BlockPosition blockposition, IBlockData var3) {
        return world.isBlockFacePowered(blockposition.down(), EnumDirection.DOWN);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        a(iblockdata, world, blockposition, random, this.a(world, blockposition, iblockdata));
    }

    public static void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random, boolean flag) {
        List list = (List)b.get(world);

        while(list != null && !list.isEmpty() && world.getTime() - ((BlockRedstoneTorch.RedstoneUpdateInfo)list.get(0)).b > 60L) {
            list.remove(0);
        }

        if (iblockdata.get(LIT)) {
            if (flag) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(LIT, Boolean.valueOf(false)), 3);
                if (a(world, blockposition, true)) {
                    world.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                    for(int i = 0; i < 5; ++i) {
                        double d0 = (double)blockposition.getX() + random.nextDouble() * 0.6D + 0.2D;
                        double d1 = (double)blockposition.getY() + random.nextDouble() * 0.6D + 0.2D;
                        double d2 = (double)blockposition.getZ() + random.nextDouble() * 0.6D + 0.2D;
                        world.addParticle(Particles.M, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }

                    world.J().a(blockposition, world.getType(blockposition).getBlock(), 160);
                }
            }
        } else if (!flag && !a(world, blockposition, false)) {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(LIT, Boolean.valueOf(true)), 3);
        }

    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (iblockdata.get(LIT) == this.a(world, blockposition, iblockdata) && !world.J().b(blockposition, this)) {
            world.J().a(blockposition, this, this.a(world));
        }

    }

    public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? iblockdata.a(iblockaccess, blockposition, enumdirection) : 0;
    }

    public boolean isPowerSource(IBlockData var1) {
        return true;
    }

    public int m(IBlockData iblockdata) {
        return iblockdata.get(LIT) ? super.m(iblockdata) : 0;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(LIT);
    }

    private static boolean a(World world, BlockPosition blockposition, boolean flag) {
        Object object = (List)b.get(world);
        if (object == null) {
            object = Lists.newArrayList();
            b.put(world, object);
        }

        if (flag) {
            ((List)object).add(new BlockRedstoneTorch.RedstoneUpdateInfo(blockposition.h(), world.getTime()));
        }

        int i = 0;

        for(int j = 0; j < ((List)object).size(); ++j) {
            BlockRedstoneTorch.RedstoneUpdateInfo blockredstonetorch$redstoneupdateinfo = (BlockRedstoneTorch.RedstoneUpdateInfo)((List)object).get(j);
            if (blockredstonetorch$redstoneupdateinfo.a.equals(blockposition)) {
                ++i;
                if (i >= 8) {
                    return true;
                }
            }
        }

        return false;
    }

    public static class RedstoneUpdateInfo {
        private final BlockPosition a;
        private final long b;

        public RedstoneUpdateInfo(BlockPosition blockposition, long i) {
            this.a = blockposition;
            this.b = i;
        }
    }
}
