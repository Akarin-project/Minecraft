package net.minecraft.server;

public class ContainerHopper extends Container {
    private final IInventory hopper;

    public ContainerHopper(PlayerInventory playerinventory, IInventory iinventory, EntityHuman entityhuman) {
        this.hopper = iinventory;
        iinventory.startOpen(entityhuman);
        boolean flag = true;

        for(int i = 0; i < iinventory.getSize(); ++i) {
            this.a(new Slot(iinventory, i, 44 + i * 18, 20));
        }

        for(int k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + k * 9 + 9, 8 + j * 18, k * 18 + 51));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.a(new Slot(playerinventory, l, 8 + l * 18, 109));
        }

    }

    public boolean canUse(EntityHuman entityhuman) {
        return this.hopper.a(entityhuman);
    }

    public ItemStack shiftClick(EntityHuman var1, int i) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if (i < this.hopper.getSize()) {
                if (!this.a(itemstack1, this.hopper.getSize(), this.slots.size(), true)) {
                    return ItemStack.a;
                }
            } else if (!this.a(itemstack1, 0, this.hopper.getSize(), false)) {
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
        this.hopper.closeContainer(entityhuman);
    }
}
