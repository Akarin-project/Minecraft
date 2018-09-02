package net.minecraft.server;

public class Slot {
    public final int index;
    public final IInventory inventory;
    public int rawSlotIndex;
    public int f;
    public int g;

    public Slot(IInventory iinventory, int i, int j, int k) {
        this.inventory = iinventory;
        this.index = i;
        this.f = j;
        this.g = k;
    }

    public void a(ItemStack itemstack, ItemStack itemstack1) {
        int i = itemstack1.getCount() - itemstack.getCount();
        if (i > 0) {
            this.a(itemstack1, i);
        }

    }

    protected void a(ItemStack var1, int var2) {
    }

    protected void b(int var1) {
    }

    protected void c(ItemStack var1) {
    }

    public ItemStack a(EntityHuman var1, ItemStack itemstack) {
        this.f();
        return itemstack;
    }

    public boolean isAllowed(ItemStack var1) {
        return true;
    }

    public ItemStack getItem() {
        return this.inventory.getItem(this.index);
    }

    public boolean hasItem() {
        return !this.getItem().isEmpty();
    }

    public void set(ItemStack itemstack) {
        this.inventory.setItem(this.index, itemstack);
        this.f();
    }

    public void f() {
        this.inventory.update();
    }

    public int getMaxStackSize() {
        return this.inventory.getMaxStackSize();
    }

    public int getMaxStackSize(ItemStack var1) {
        return this.getMaxStackSize();
    }

    public ItemStack a(int i) {
        return this.inventory.splitStack(this.index, i);
    }

    public boolean a(IInventory iinventory, int i) {
        return iinventory == this.inventory && i == this.index;
    }

    public boolean isAllowed(EntityHuman var1) {
        return true;
    }
}
