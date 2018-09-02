package net.minecraft.server;

public class ContainerPlayer extends ContainerRecipeBook {
    private static final String[] h = new String[]{"item/empty_armor_slot_boots", "item/empty_armor_slot_leggings", "item/empty_armor_slot_chestplate", "item/empty_armor_slot_helmet"};
    private static final EnumItemSlot[] i = new EnumItemSlot[]{EnumItemSlot.HEAD, EnumItemSlot.CHEST, EnumItemSlot.LEGS, EnumItemSlot.FEET};
    public InventoryCrafting craftInventory = new InventoryCrafting(this, 2, 2);
    public InventoryCraftResult resultInventory = new InventoryCraftResult();
    public boolean g;
    private final EntityHuman owner;

    public ContainerPlayer(PlayerInventory playerinventory, boolean flag, EntityHuman entityhuman) {
        this.g = flag;
        this.owner = entityhuman;
        this.a(new SlotResult(playerinventory.player, this.craftInventory, this.resultInventory, 0, 154, 28));

        for(int ix = 0; ix < 2; ++ix) {
            for(int j = 0; j < 2; ++j) {
                this.a(new Slot(this.craftInventory, j + ix * 2, 98 + j * 18, 18 + ix * 18));
            }
        }

        for(int k = 0; k < 4; ++k) {
            final EnumItemSlot enumitemslot = i[k];
            this.a(new Slot(playerinventory, 39 - k, 8, 8 + k * 18) {
                public int getMaxStackSize() {
                    return 1;
                }

                public boolean isAllowed(ItemStack itemstack) {
                    return enumitemslot == EntityInsentient.e(itemstack);
                }

                public boolean isAllowed(EntityHuman entityhuman1) {
                    ItemStack itemstack = this.getItem();
                    return !itemstack.isEmpty() && !entityhuman1.u() && EnchantmentManager.d(itemstack) ? false : super.isAllowed(entityhuman1);
                }
            });
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.a(new Slot(playerinventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.a(new Slot(playerinventory, i1, 8 + i1 * 18, 142));
        }

        this.a(new Slot(playerinventory, 40, 77, 62) {
        });
    }

    public void a(AutoRecipeStackManager autorecipestackmanager) {
        this.craftInventory.a(autorecipestackmanager);
    }

    public void d() {
        this.resultInventory.clear();
        this.craftInventory.clear();
    }

    public boolean a(IRecipe irecipe) {
        return irecipe.a(this.craftInventory, this.owner.world);
    }

    public void a(IInventory var1) {
        this.a(this.owner.world, this.owner, this.craftInventory, this.resultInventory);
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        this.resultInventory.clear();
        if (!entityhuman.world.isClientSide) {
            this.a(entityhuman, entityhuman.world, this.craftInventory);
        }
    }

    public boolean canUse(EntityHuman var1) {
        return true;
    }

    public ItemStack shiftClick(EntityHuman entityhuman, int ix) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(ix);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            EnumItemSlot enumitemslot = EntityInsentient.e(itemstack);
            if (ix == 0) {
                if (!this.a(itemstack1, 9, 45, true)) {
                    return ItemStack.a;
                }

                slot.a(itemstack1, itemstack);
            } else if (ix >= 1 && ix < 5) {
                if (!this.a(itemstack1, 9, 45, false)) {
                    return ItemStack.a;
                }
            } else if (ix >= 5 && ix < 9) {
                if (!this.a(itemstack1, 9, 45, false)) {
                    return ItemStack.a;
                }
            } else if (enumitemslot.a() == EnumItemSlot.Function.ARMOR && !((Slot)this.slots.get(8 - enumitemslot.b())).hasItem()) {
                int j = 8 - enumitemslot.b();
                if (!this.a(itemstack1, j, j + 1, false)) {
                    return ItemStack.a;
                }
            } else if (enumitemslot == EnumItemSlot.OFFHAND && !((Slot)this.slots.get(45)).hasItem()) {
                if (!this.a(itemstack1, 45, 46, false)) {
                    return ItemStack.a;
                }
            } else if (ix >= 9 && ix < 36) {
                if (!this.a(itemstack1, 36, 45, false)) {
                    return ItemStack.a;
                }
            } else if (ix >= 36 && ix < 45) {
                if (!this.a(itemstack1, 9, 36, false)) {
                    return ItemStack.a;
                }
            } else if (!this.a(itemstack1, 9, 45, false)) {
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
