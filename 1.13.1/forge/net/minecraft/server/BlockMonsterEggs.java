package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;

public class BlockMonsterEggs extends Block {
    private final Block a;
    private static final Map<Block, Block> b = Maps.newIdentityHashMap();

    public BlockMonsterEggs(Block block, Block.Info block$info) {
        super(block$info);
        this.a = block;
        b.put(block, this);
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public Block d() {
        return this.a;
    }

    public static boolean k(IBlockData iblockdata) {
        return b.containsKey(iblockdata.getBlock());
    }

    protected ItemStack t(IBlockData var1) {
        return new ItemStack(this.a);
    }

    public void dropNaturally(IBlockData var1, World world, BlockPosition blockposition, float var4, int var5) {
        if (!world.isClientSide && world.getGameRules().getBoolean("doTileDrops")) {
            EntitySilverfish entitysilverfish = new EntitySilverfish(world);
            entitysilverfish.setPositionRotation((double)blockposition.getX() + 0.5D, (double)blockposition.getY(), (double)blockposition.getZ() + 0.5D, 0.0F, 0.0F);
            world.addEntity(entitysilverfish);
            entitysilverfish.doSpawnEffect();
        }

    }

    public static IBlockData f(Block block) {
        return ((Block)b.get(block)).getBlockData();
    }
}
