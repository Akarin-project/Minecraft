package net.minecraft.server;

public interface IInventory extends INamableTileEntity {
    int getSize();

    boolean P_();

    ItemStack getItem(int var1);

    ItemStack splitStack(int var1, int var2);

    ItemStack splitWithoutUpdate(int var1);

    void setItem(int var1, ItemStack var2);

    int getMaxStackSize();

    void update();

    boolean a(EntityHuman var1);

    void startOpen(EntityHuman var1);

    void closeContainer(EntityHuman var1);

    boolean b(int var1, ItemStack var2);

    int getProperty(int var1);

    void setProperty(int var1, int var2);

    int h();

    void clear();

    default int n() {
        return 0;
    }

    default int U_() {
        return 0;
    }
}
