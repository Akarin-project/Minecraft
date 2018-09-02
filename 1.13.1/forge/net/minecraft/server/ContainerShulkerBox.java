package net.minecraft.server;

public class ContainerShulkerBox extends Container {
    private final IInventory a;

    public ContainerShulkerBox(PlayerInventory playerinventory, IInventory iinventory, EntityHuman entityhuman) {
        this.a = iinventory;
        iinventory.startOpen(entityhuman);
        boolean flag = true;
        boolean flag1 = true;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.a(new SlotShulkerBox(iinventory, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(playerinventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.a(new Slot(playerinventory, l, 8 + l * 18, 142));
        }

    }

    public boolean canUse(EntityHuman entityhuman) {
        return this.a.a(entityhuman);
    }

    public ItemStack shiftClick(EntityHuman var1, int i) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if (i < this.a.getSize()) {
                if (!this.a(itemstack1, this.a.getSize(), this.slots.size(), true)) {
                    return ItemStack.a;
                }
            } else if (!this.a(itemstack1, 0, this.a.getSize(), false)) {
                return ItemStack.a;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.a);
            } else {
                slot.f();
            }
        }

        return itemstack;
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        this.a.closeContainer(entityhuman);
    }
}
