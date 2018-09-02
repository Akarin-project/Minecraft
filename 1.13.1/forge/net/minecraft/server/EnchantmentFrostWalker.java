package net.minecraft.server;

public class EnchantmentFrostWalker extends Enchantment {
    public EnchantmentFrostWalker(Enchantment.Rarity enchantment$rarity, EnumItemSlot... aenumitemslot) {
        super(enchantment$rarity, EnchantmentSlotType.ARMOR_FEET, aenumitemslot);
    }

    public int a(int i) {
        return i * 10;
    }

    public int b(int i) {
        return this.a(i) + 15;
    }

    public boolean isTreasure() {
        return true;
    }

    public int getMaxLevel() {
        return 2;
    }

    public static void a(EntityLiving entityliving, World world, BlockPosition blockposition, int i) {
        if (entityliving.onGround) {
            IBlockData iblockdata = Blocks.FROSTED_ICE.getBlockData();
            float f = (float)Math.min(16, 2 + i);
            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(0, 0, 0);

            for(BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 : BlockPosition.b(blockposition.a((double)(-f), -1.0D, (double)(-f)), blockposition.a((double)f, -1.0D, (double)f))) {
                if (blockposition$mutableblockposition1.g(entityliving.locX, entityliving.locY, entityliving.locZ) <= (double)(f * f)) {
                    blockposition$mutableblockposition.c(blockposition$mutableblockposition1.getX(), blockposition$mutableblockposition1.getY() + 1, blockposition$mutableblockposition1.getZ());
                    IBlockData iblockdata1 = world.getType(blockposition$mutableblockposition);
                    if (iblockdata1.isAir()) {
                        IBlockData iblockdata2 = world.getType(blockposition$mutableblockposition1);
                        if (iblockdata2.getMaterial() == Material.WATER && iblockdata2.get(BlockFluids.LEVEL) == 0 && iblockdata.canPlace(world, blockposition$mutableblockposition1) && world.a(iblockdata, blockposition$mutableblockposition1)) {
                            world.setTypeUpdate(blockposition$mutableblockposition1, iblockdata);
                            world.J().a(blockposition$mutableblockposition1.h(), Blocks.FROSTED_ICE, MathHelper.nextInt(entityliving.getRandom(), 60, 120));
                        }
                    }
                }
            }

        }
    }

    public boolean a(Enchantment enchantment) {
        return super.a(enchantment) && enchantment != Enchantments.DEPTH_STRIDER;
    }
}
