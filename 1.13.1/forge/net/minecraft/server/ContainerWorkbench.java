package net.minecraft.server;

public class ContainerWorkbench extends ContainerRecipeBook {
    public InventoryCrafting craftInventory = new InventoryCrafting(this, 3, 3);
    public InventoryCraftResult resultInventory = new InventoryCraftResult();
    private final World g;
    private final BlockPosition h;
    private final EntityHuman i;

    public ContainerWorkbench(PlayerInventory playerinventory, World world, BlockPosition blockposition) {
        this.g = world;
        this.h = blockposition;
        this.i = playerinventory.player;
        this.a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 124, 35));

        for(int ix = 0; ix < 3; ++ix) {
            for(int j = 0; j < 3; ++j) {
                this.a(new Slot(this.craftInventory, j + ix * 3, 30 + j * 18, 17 + ix * 18));
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

    public void a(IInventory var1) {
        this.a(this.g, this.i, this.craftInventory, this.resultInventory);
    }

    public void a(AutoRecipeStackManager autorecipestackmanager) {
        this.craftInventory.a(autorecipestackmanager);
    }

    public void d() {
        this.craftInventory.clear();
        this.resultInventory.clear();
    }

    public boolean a(IRecipe irecipe) {
        return irecipe.a(this.craftInventory, this.i.world);
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        if (!this.g.isClientSide) {
            this.a(entityhuman, this.g, this.craftInventory);
        }
    }

    public boolean canUse(EntityHuman entityhuman) {
        if (this.g.getType(this.h).getBlock() != Blocks.CRAFTING_TABLE) {
            return false;
        } else {
            return entityhuman.d((double)this.h.getX() + 0.5D, (double)this.h.getY() + 0.5D, (double)this.h.getZ() + 0.5D) <= 64.0D;
        }
    }

    public ItemStack shiftClick(EntityHuman entityhuman, int ix) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(ix);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if (ix == 0) {
                itemstack1.getItem().b(itemstack1, this.g, entityhuman);
                if (!this.a(itemstack1, 10, 46, true)) {
                    return ItemStack.a;
                }

                slot.a(itemstack1, itemstack);
            } else if (ix >= 10 && ix < 37) {
                if (!this.a(itemstack1, 37, 46, false)) {
                    return ItemStack.a;
                }
            } else if (ix >= 37 && ix < 46) {
                if (!this.a(itemstack1, 10, 37, false)) {
                    return ItemStack.a;
                }
            } else if (!this.a(itemstack1, 10, 46, false)) {
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

            ItemStack itemstack2 = slot.a(entityhuman, itemstack1);
            if (ix == 0) {
                entityhuman.drop(itemstack2, false);
            }
        }

        return itemstack;
    }

    public boolean a(ItemStack itemstack, Slot slot) {
        return slot.inventory != this.resultInventory && super.a(itemstack, slot);
    }

    public int e() {
        return 0;
    }

    public int f() {
        return this.craftInventory.U_();
    }

    public int g() {
        return this.craftInventory.n();
    }
}
