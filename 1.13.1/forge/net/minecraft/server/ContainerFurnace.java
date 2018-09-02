package net.minecraft.server;

public class ContainerFurnace extends ContainerRecipeBook {
    private final IInventory furnace;
    private final World f;
    private int g;
    private int h;
    private int i;
    private int j;

    public ContainerFurnace(PlayerInventory playerinventory, IInventory iinventory) {
        this.furnace = iinventory;
        this.f = playerinventory.player.world;
        this.a(new Slot(iinventory, 0, 56, 17));
        this.a(new SlotFurnaceFuel(iinventory, 1, 56, 53));
        this.a(new SlotFurnaceResult(playerinventory.player, iinventory, 2, 116, 35));

        for(int ix = 0; ix < 3; ++ix) {
            for(int jx = 0; jx < 9; ++jx) {
                this.a(new Slot(playerinventory, jx + ix * 9 + 9, 8 + jx * 18, 84 + ix * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k, 8 + k * 18, 142));
        }

    }

    public void addSlotListener(ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, this.furnace);
    }

    public void a(AutoRecipeStackManager autorecipestackmanager) {
        if (this.furnace instanceof AutoRecipeOutput) {
            ((AutoRecipeOutput)this.furnace).a(autorecipestackmanager);
        }

    }

    public void d() {
        this.furnace.clear();
    }

    public boolean a(IRecipe irecipe) {
        return irecipe.a(this.furnace, this.f);
    }

    public int e() {
        return 2;
    }

    public int f() {
        return 1;
    }

    public int g() {
        return 1;
    }

    public void b() {
        super.b();

        for(ICrafting icrafting : this.listeners) {
            if (this.g != this.furnace.getProperty(2)) {
                icrafting.setContainerData(this, 2, this.furnace.getProperty(2));
            }

            if (this.i != this.furnace.getProperty(0)) {
                icrafting.setContainerData(this, 0, this.furnace.getProperty(0));
            }

            if (this.j != this.furnace.getProperty(1)) {
                icrafting.setContainerData(this, 1, this.furnace.getProperty(1));
            }

            if (this.h != this.furnace.getProperty(3)) {
                icrafting.setContainerData(this, 3, this.furnace.getProperty(3));
            }
        }

        this.g = this.furnace.getProperty(2);
        this.i = this.furnace.getProperty(0);
        this.j = this.furnace.getProperty(1);
        this.h = this.furnace.getProperty(3);
    }

    public boolean canUse(EntityHuman entityhuman) {
        return this.furnace.a(entityhuman);
    }

    public ItemStack shiftClick(EntityHuman entityhuman, int ix) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(ix);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if (ix == 2) {
                if (!this.a(itemstack1, 3, 39, true)) {
                    return ItemStack.a;
                }

                slot.a(itemstack1, itemstack);
            } else if (ix != 1 && ix != 0) {
                if (this.a(itemstack1)) {
                    if (!this.a(itemstack1, 0, 1, false)) {
                        return ItemStack.a;
                    }
                } else if (TileEntityFurnace.isFuel(itemstack1)) {
                    if (!this.a(itemstack1, 1, 2, false)) {
                        return ItemStack.a;
                    }
                } else if (ix >= 3 && ix < 30) {
                    if (!this.a(itemstack1, 30, 39, false)) {
                        return ItemStack.a;
                    }
                } else if (ix >= 30 && ix < 39 && !this.a(itemstack1, 3, 30, false)) {
                    return ItemStack.a;
                }
            } else if (!this.a(itemstack1, 3, 39, false)) {
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

    private boolean a(ItemStack itemstack) {
        for(IRecipe irecipe : this.f.E().b()) {
            if (irecipe instanceof FurnaceRecipe && ((RecipeItemStack)irecipe.e().get(0)).a(itemstack)) {
                return true;
            }
        }

        return false;
    }
}
