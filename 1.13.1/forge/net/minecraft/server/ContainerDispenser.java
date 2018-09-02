package net.minecraft.server;

public class ContainerDispenser extends Container {
    public final IInventory items;

    public ContainerDispenser(IInventory iinventory, IInventory iinventory1) {
        this.items = iinventory1;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.a(new Slot(iinventory1, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(iinventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.a(new Slot(iinventory, l, 8 + l * 18, 142));
        }

    }

    public boolean canUse(EntityHuman entityhuman) {
        return this.items.a(entityhuman);
    }

    public ItemStack shiftClick(EntityHuman entityhuman, int i) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if (i < 9) {
                if (!this.a(itemstack1, 9, 45, true)) {
                    return ItemStack.a;
                }
            } else if (!this.a(itemstack1, 0, 9, false)) {
                return ItemStack.a;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.a);
            } else {
                slot.f();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.a;
            }

            slot.a(entityhuman, itemstack1);
        }

        return itemstack;
    }
}
