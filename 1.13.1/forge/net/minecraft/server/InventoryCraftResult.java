package net.minecraft.server;

import javax.annotation.Nullable;

public class InventoryCraftResult implements IInventory, RecipeHolder {
    private final NonNullList<ItemStack> items = NonNullList.<ItemStack>a(1, ItemStack.a);
    private IRecipe b;

    public InventoryCraftResult() {
    }

    public int getSize() {
        return 1;
    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int var1) {
        return this.items.get(0);
    }

    public IChatBaseComponent getDisplayName() {
        return new ChatComponentText("Result");
    }

    public boolean hasCustomName() {
        return false;
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return null;
    }

    public ItemStack splitStack(int var1, int var2) {
        return ContainerUtil.a(this.items, 0);
    }

    public ItemStack splitWithoutUpdate(int var1) {
        return ContainerUtil.a(this.items, 0);
    }

    public void setItem(int var1, ItemStack itemstack) {
        this.items.set(0, itemstack);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {
    }

    public boolean a(EntityHuman var1) {
        return true;
    }

    public void startOpen(EntityHuman var1) {
    }

    public void closeContainer(EntityHuman var1) {
    }

    public boolean b(int var1, ItemStack var2) {
        return true;
    }

    public int getProperty(int var1) {
        return 0;
    }

    public void setProperty(int var1, int var2) {
    }

    public int h() {
        return 0;
    }

    public void clear() {
        this.items.clear();
    }

    public void a(@Nullable IRecipe irecipe) {
        this.b = irecipe;
    }

    @Nullable
    public IRecipe i() {
        return this.b;
    }
}
