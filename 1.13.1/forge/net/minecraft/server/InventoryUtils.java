package net.minecraft.server;

import java.util.Random;

public class InventoryUtils {
    private static final Random a = new Random();

    public static void dropInventory(World world, BlockPosition blockposition, IInventory iinventory) {
        dropInventory(world, (double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ(), iinventory);
    }

    public static void dropEntity(World world, Entity entity, IInventory iinventory) {
        dropInventory(world, entity.locX, entity.locY, entity.locZ, iinventory);
    }

    private static void dropInventory(World world, double d0, double d1, double d2, IInventory iinventory) {
        for(int i = 0; i < iinventory.getSize(); ++i) {
            ItemStack itemstack = iinventory.getItem(i);
            if (!itemstack.isEmpty()) {
                dropItem(world, d0, d1, d2, itemstack);
            }
        }

    }

    public static void dropItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
        float f = 0.75F;
        float f1 = 0.125F;
        float f2 = a.nextFloat() * 0.75F + 0.125F;
        float f3 = a.nextFloat() * 0.75F;
        float f4 = a.nextFloat() * 0.75F + 0.125F;

        while(!itemstack.isEmpty()) {
            EntityItem entityitem = new EntityItem(world, d0 + (double)f2, d1 + (double)f3, d2 + (double)f4, itemstack.cloneAndSubtract(a.nextInt(21) + 10));
            float f5 = 0.05F;
            entityitem.motX = a.nextGaussian() * (double)0.05F;
            entityitem.motY = a.nextGaussian() * (double)0.05F + (double)0.2F;
            entityitem.motZ = a.nextGaussian() * (double)0.05F;
            world.addEntity(entityitem);
        }

    }
}
