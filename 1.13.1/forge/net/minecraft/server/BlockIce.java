package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockIce extends BlockHalfTransparent {
    public BlockIce(Block.Info block$info) {
        super(block$info);
    }

    public int j(IBlockData var1, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return Blocks.WATER.getBlockData().b(iblockaccess, blockposition);
    }

    public TextureType c() {
        return TextureType.TRANSLUCENT;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity var5, ItemStack itemstack) {
        entityhuman.b(StatisticList.BLOCK_MINED.b(this));
        entityhuman.applyExhaustion(0.005F);
        if (this.X_() && EnchantmentManager.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0) {
            a(world, blockposition, this.t(iblockdata));
        } else {
            if (world.worldProvider.isNether()) {
                world.setAir(blockposition);
                return;
            }

            int i = EnchantmentManager.getEnchantmentLevel(Enchantments.LOOT_BONUS_BLOCKS, itemstack);
            iblockdata.a(world, blockposition, i);
            Material material = world.getType(blockposition.down()).getMaterial();
            if (material.isSolid() || material.isLiquid()) {
                world.setTypeUpdate(blockposition, Blocks.WATER.getBlockData());
            }
        }

    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (world.getBrightness(EnumSkyBlock.BLOCK, blockposition) > 11 - iblockdata.b(world, blockposition)) {
            this.b(iblockdata, world, blockposition);
        }

    }

    protected void b(IBlockData iblockdata, World world, BlockPosition blockposition) {
        if (world.worldProvider.isNether()) {
            world.setAir(blockposition);
        } else {
            iblockdata.a(world, blockposition, 0);
            world.setTypeUpdate(blockposition, Blocks.WATER.getBlockData());
            world.a(blockposition, Blocks.WATER, blockposition);
        }
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.NORMAL;
    }
}
