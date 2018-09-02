package net.minecraft.server;

import javax.annotation.Nullable;

public class InventoryCrafting implements IInventory, AutoRecipeOutput {
    private final NonNullList<ItemStack> items;
    private final int b;
    private final int c;
    public final Container container;

    public InventoryCrafting(Container containerx, int i, int j) {
        this.items = NonNullList.<ItemStack>a(i * j, ItemStack.a);
        this.container = containerx;
        this.b = i;
        this.c = j;
    }

    public int getSize() {
        return this.items.size();
    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int i) {
        return i >= this.getSize() ? ItemStack.a : (ItemStack)this.items.get(i);
    }

    public IChatBaseComponent getDisplayName() {
        return new ChatMessage("container.crafting", new Object[0]);
    }

    public boolean hasCustomName() {
        return false;
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return null;
    }

    public ItemStack splitWithoutUpdate(int i) {
        return ContainerUtil.a(this.items, i);
    }

    public ItemStack splitStack(int i, int j) {
        ItemStack itemstack = ContainerUtil.a(this.items, i, j);
        if (!itemstack.isEmpty()) {
            this.container.a(this);
        }

        return itemstack;
    }

    public void setItem(int i, ItemStack itemstack) {
        this.items.set(i, itemstack);
        this.container.a(this);
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

    public int n() {
        return this.c;
    }

    public int U_() {
        return this.b;
    }

    public void a(AutoRecipeStackManager autorecipestackmanager) {
        for(ItemStack itemstack : this.items) {
            autorecipestackmanager.a(itemstack);
        }

    }
}
