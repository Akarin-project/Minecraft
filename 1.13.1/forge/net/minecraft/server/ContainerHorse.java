package net.minecraft.server;

public class ContainerHorse extends Container {
    private final IInventory a;
    private final EntityHorseAbstract f;

    public ContainerHorse(IInventory iinventory, IInventory iinventory1, final EntityHorseAbstract entityhorseabstract, EntityHuman entityhuman) {
        this.a = iinventory1;
        this.f = entityhorseabstract;
        boolean flag = true;
        iinventory1.startOpen(entityhuman);
        boolean flag1 = true;
        this.a(new Slot(iinventory1, 0, 8, 18) {
            public boolean isAllowed(ItemStack itemstack) {
                return itemstack.getItem() == Items.SADDLE && !this.hasItem() && entityhorseabstract.dU();
            }
        });
        this.a(new Slot(iinventory1, 1, 8, 36) {
            public boolean isAllowed(ItemStack itemstack) {
                return entityhorseabstract.g(itemstack);
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        if (entityhorseabstract instanceof EntityHorseChestedAbstract && ((EntityHorseChestedAbstract)entityhorseabstract).isCarryingChest()) {
            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < ((EntityHorseChestedAbstract)entityhorseabstract).dH(); ++j) {
                    this.a(new Slot(iinventory1, 2 + j + i * ((EntityHorseChestedAbstract)entityhorseabstract).dH(), 80 + j * 18, 18 + i * 18));
                }
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(iinventory, i1 + k * 9 + 9, 8 + i1 * 18, 102 + k * 18 + -18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.a(new Slot(iinventory, l, 8 + l * 18, 142));
        }

    }

    public boolean canUse(EntityHuman entityhuman) {
        return this.a.a(entityhuman) && this.f.isAlive() && this.f.g((Entity)entityhuman) < 8.0F;
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
            } else if (this.getSlot(1).isAllowed(itemstack1) && !this.getSlot(1).hasItem()) {
                if (!this.a(itemstack1, 1, 2, false)) {
                    return ItemStack.a;
                }
            } else if (this.getSlot(0).isAllowed(itemstack1)) {
                if (!this.a(itemstack1, 0, 1, false)) {
                    return ItemStack.a;
                }
            } else if (this.a.getSize() <= 2 || !this.a(itemstack1, 2, this.a.getSize(), false)) {
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
