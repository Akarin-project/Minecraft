package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class InventorySubcontainer implements IInventory, AutoRecipeOutput {
    private final IChatBaseComponent a;
    private final int b;
    public final NonNullList<ItemStack> items;
    private List<IInventoryListener> d;
    private IChatBaseComponent e;

    public InventorySubcontainer(IChatBaseComponent ichatbasecomponent, int i) {
        this.a = ichatbasecomponent;
        this.b = i;
        this.items = NonNullList.<ItemStack>a(i, ItemStack.a);
    }

    public void a(IInventoryListener iinventorylistener) {
        if (this.d == null) {
            this.d = Lists.newArrayList();
        }

        this.d.add(iinventorylistener);
    }

    public void b(IInventoryListener iinventorylistener) {
        this.d.remove(iinventorylistener);
    }

    public ItemStack getItem(int i) {
        return i >= 0 && i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.a;
    }

    public ItemStack splitStack(int i, int j) {
        ItemStack itemstack = ContainerUtil.a(this.items, i, j);
        if (!itemstack.isEmpty()) {
            this.update();
        }

        return itemstack;
    }

    public ItemStack a(ItemStack itemstack) {
        ItemStack itemstack1 = itemstack.cloneItemStack();

        for(int i = 0; i < this.b; ++i) {
            ItemStack itemstack2 = this.getItem(i);
            if (itemstack2.isEmpty()) {
                this.setItem(i, itemstack1);
                this.update();
                return ItemStack.a;
            }

            if (ItemStack.c(itemstack2, itemstack1)) {
                int j = Math.min(this.getMaxStackSize(), itemstack2.getMaxStackSize());
                int k = Math.min(itemstack1.getCount(), j - itemstack2.getCount());
                if (k > 0) {
                    itemstack2.add(k);
                    itemstack1.subtract(k);
                    if (itemstack1.isEmpty()) {
                        this.update();
                        return ItemStack.a;
                    }
                }
            }
        }

        if (itemstack1.getCount() != itemstack.getCount()) {
            this.update();
        }

        return itemstack1;
    }

    public ItemStack splitWithoutUpdate(int i) {
        ItemStack itemstack = this.items.get(i);
        if (itemstack.isEmpty()) {
            return ItemStack.a;
        } else {
            this.items.set(i, ItemStack.a);
            return itemstack;
        }
    }

    public void setItem(int i, ItemStack itemstack) {
        this.items.set(i, itemstack);
        if (!itemstack.isEmpty() && itemstack.getCount() > this.getMaxStackSize()) {
            itemstack.setCount(this.getMaxStackSize());
        }

        this.update();
    }

    public int getSize() {
        return this.b;
    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public IChatBaseComponent getDisplayName() {
        return this.e != null ? this.e : this.a;
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return this.e;
    }

    public boolean hasCustomName() {
        return this.e != null;
    }

    public void a(@Nullable IChatBaseComponent ichatbasecomponent) {
        this.e = ichatbasecomponent;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {
        if (this.d != null) {
            for(int i = 0; i < this.d.size(); ++i) {
                ((IInventoryListener)this.d.get(i)).a(this);
            }
        }

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

    public void a(AutoRecipeStackManager autorecipestackmanager) {
        for(ItemStack itemstack : this.items) {
            autorecipestackmanager.b(itemstack);
        }

    }
}
