package net.minecraft.server;

import javax.annotation.Nullable;

public interface IWorldInventory extends IInventory {
    int[] getSlotsForFace(EnumDirection var1);

    boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable EnumDirection var3);

    boolean canTakeItemThroughFace(int var1, ItemStack var2, EnumDirection var3);
}
