package net.minecraft.server;

public interface ICrafting {
    void a(Container var1, NonNullList<ItemStack> var2);

    void a(Container var1, int var2, ItemStack var3);

    void setContainerData(Container var1, int var2, int var3);

    void setContainerData(Container var1, IInventory var2);
}
