package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class Container {
    public NonNullList<ItemStack> items = NonNullList.<ItemStack>a();
    public List<Slot> slots = Lists.newArrayList();
    public int windowId;
    private int dragType = -1;
    private int g;
    private final Set<Slot> h = Sets.newHashSet();
    protected List<ICrafting> listeners = Lists.newArrayList();
    private final Set<EntityHuman> i = Sets.newHashSet();

    public Container() {
    }

    protected Slot a(Slot slot) {
        // $FF: Couldn't be decompiled
    }

    public void addSlotListener(ICrafting icrafting) {
        if (this.listeners.contains(icrafting)) {
            throw new IllegalArgumentException("Listener already listening");
        } else {
            this.listeners.add(icrafting);
            icrafting.a(this, this.a());
            this.b();
        }
    }

    public NonNullList<ItemStack> a() {
        NonNullList nonnulllist = NonNullList.a();

        for(int ix = 0; ix < this.slots.size(); ++ix) {
            nonnulllist.add(((Slot)this.slots.get(ix)).getItem());
        }

        return nonnulllist;
    }

    public void b() {
        for(int ix = 0; ix < this.slots.size(); ++ix) {
            ItemStack itemstack = ((Slot)this.slots.get(ix)).getItem();
            ItemStack itemstack1 = this.items.get(ix);
            if (!ItemStack.matches(itemstack1, itemstack)) {
                itemstack1 = itemstack.isEmpty() ? ItemStack.a : itemstack.cloneItemStack();
                this.items.set(ix, itemstack1);

                for(int j = 0; j < this.listeners.size(); ++j) {
                    ((ICrafting)this.listeners.get(j)).a(this, ix, itemstack1);
                }
            }
        }

    }

    public boolean a(EntityHuman var1, int var2) {
        return false;
    }

    @Nullable
    public Slot getSlot(IInventory iinventory, int ix) {
        for(int j = 0; j < this.slots.size(); ++j) {
            Slot slot = (Slot)this.slots.get(j);
            if (slot.a(iinventory, ix)) {
                return slot;
            }
        }

        return null;
    }

    public Slot getSlot(int ix) {
        return (Slot)this.slots.get(ix);
    }

    public ItemStack shiftClick(EntityHuman var1, int ix) {
        Slot slot = (Slot)this.slots.get(ix);
        return slot != null ? slot.getItem() : ItemStack.a;
    }

    public ItemStack a(int ix, int j, InventoryClickType inventoryclicktype, EntityHuman entityhuman) {
        ItemStack itemstack = ItemStack.a;
        PlayerInventory playerinventory = entityhuman.inventory;
        if (inventoryclicktype == InventoryClickType.QUICK_CRAFT) {
            int l1 = this.g;
            this.g = c(j);
            if ((l1 != 1 || this.g != 2) && l1 != this.g) {
                this.c();
            } else if (playerinventory.getCarried().isEmpty()) {
                this.c();
            } else if (this.g == 0) {
                this.dragType = b(j);
                if (a(this.dragType, entityhuman)) {
                    this.g = 1;
                    this.h.clear();
                } else {
                    this.c();
                }
            } else if (this.g == 1) {
                Slot slot7 = (Slot)this.slots.get(ix);
                ItemStack itemstack12 = playerinventory.getCarried();
                if (slot7 != null && a(slot7, itemstack12, true) && slot7.isAllowed(itemstack12) && (this.dragType == 2 || itemstack12.getCount() > this.h.size()) && this.b(slot7)) {
                    this.h.add(slot7);
                }
            } else if (this.g == 2) {
                if (!this.h.isEmpty()) {
                    ItemStack itemstack9 = playerinventory.getCarried().cloneItemStack();
                    int i2 = playerinventory.getCarried().getCount();

                    for(Slot slot8 : this.h) {
                        ItemStack itemstack13 = playerinventory.getCarried();
                        if (slot8 != null && a(slot8, itemstack13, true) && slot8.isAllowed(itemstack13) && (this.dragType == 2 || itemstack13.getCount() >= this.h.size()) && this.b(slot8)) {
                            ItemStack itemstack14 = itemstack9.cloneItemStack();
                            int l3 = slot8.hasItem() ? slot8.getItem().getCount() : 0;
                            a(this.h, this.dragType, itemstack14, l3);
                            int i4 = Math.min(itemstack14.getMaxStackSize(), slot8.getMaxStackSize(itemstack14));
                            if (itemstack14.getCount() > i4) {
                                itemstack14.setCount(i4);
                            }

                            i2 -= itemstack14.getCount() - l3;
                            slot8.set(itemstack14);
                        }
                    }

                    itemstack9.setCount(i2);
                    playerinventory.setCarried(itemstack9);
                }

                this.c();
            } else {
                this.c();
            }
        } else if (this.g != 0) {
            this.c();
        } else if ((inventoryclicktype == InventoryClickType.PICKUP || inventoryclicktype == InventoryClickType.QUICK_MOVE) && (j == 0 || j == 1)) {
            if (ix == -999) {
                if (!playerinventory.getCarried().isEmpty()) {
                    if (j == 0) {
                        entityhuman.drop(playerinventory.getCarried(), true);
                        playerinventory.setCarried(ItemStack.a);
                    }

                    if (j == 1) {
                        entityhuman.drop(playerinventory.getCarried().cloneAndSubtract(1), true);
                    }
                }
            } else if (inventoryclicktype == InventoryClickType.QUICK_MOVE) {
                if (ix < 0) {
                    return ItemStack.a;
                }

                Slot slot5 = (Slot)this.slots.get(ix);
                if (slot5 == null || !slot5.isAllowed(entityhuman)) {
                    return ItemStack.a;
                }

                for(ItemStack itemstack7 = this.shiftClick(entityhuman, ix); !itemstack7.isEmpty() && ItemStack.c(slot5.getItem(), itemstack7); itemstack7 = this.shiftClick(entityhuman, ix)) {
                    itemstack = itemstack7.cloneItemStack();
                }
            } else {
                if (ix < 0) {
                    return ItemStack.a;
                }

                Slot slot6 = (Slot)this.slots.get(ix);
                if (slot6 != null) {
                    ItemStack itemstack8 = slot6.getItem();
                    ItemStack itemstack11 = playerinventory.getCarried();
                    if (!itemstack8.isEmpty()) {
                        itemstack = itemstack8.cloneItemStack();
                    }

                    if (itemstack8.isEmpty()) {
                        if (!itemstack11.isEmpty() && slot6.isAllowed(itemstack11)) {
                            int l2 = j == 0 ? itemstack11.getCount() : 1;
                            if (l2 > slot6.getMaxStackSize(itemstack11)) {
                                l2 = slot6.getMaxStackSize(itemstack11);
                            }

                            slot6.set(itemstack11.cloneAndSubtract(l2));
                        }
                    } else if (slot6.isAllowed(entityhuman)) {
                        if (itemstack11.isEmpty()) {
                            if (itemstack8.isEmpty()) {
                                slot6.set(ItemStack.a);
                                playerinventory.setCarried(ItemStack.a);
                            } else {
                                int i3 = j == 0 ? itemstack8.getCount() : (itemstack8.getCount() + 1) / 2;
                                playerinventory.setCarried(slot6.a(i3));
                                if (itemstack8.isEmpty()) {
                                    slot6.set(ItemStack.a);
                                }

                                slot6.a(entityhuman, playerinventory.getCarried());
                            }
                        } else if (slot6.isAllowed(itemstack11)) {
                            if (a(itemstack8, itemstack11)) {
                                int j3 = j == 0 ? itemstack11.getCount() : 1;
                                if (j3 > slot6.getMaxStackSize(itemstack11) - itemstack8.getCount()) {
                                    j3 = slot6.getMaxStackSize(itemstack11) - itemstack8.getCount();
                                }

                                if (j3 > itemstack11.getMaxStackSize() - itemstack8.getCount()) {
                                    j3 = itemstack11.getMaxStackSize() - itemstack8.getCount();
                                }

                                itemstack11.subtract(j3);
                                itemstack8.add(j3);
                            } else if (itemstack11.getCount() <= slot6.getMaxStackSize(itemstack11)) {
                                slot6.set(itemstack11);
                                playerinventory.setCarried(itemstack8);
                            }
                        } else if (itemstack11.getMaxStackSize() > 1 && a(itemstack8, itemstack11) && !itemstack8.isEmpty()) {
                            int k3 = itemstack8.getCount();
                            if (k3 + itemstack11.getCount() <= itemstack11.getMaxStackSize()) {
                                itemstack11.add(k3);
                                itemstack8 = slot6.a(k3);
                                if (itemstack8.isEmpty()) {
                                    slot6.set(ItemStack.a);
                                }

                                slot6.a(entityhuman, playerinventory.getCarried());
                            }
                        }
                    }

                    slot6.f();
                }
            }
        } else if (inventoryclicktype == InventoryClickType.SWAP && j >= 0 && j < 9) {
            Slot slot4 = (Slot)this.slots.get(ix);
            ItemStack itemstack6 = playerinventory.getItem(j);
            ItemStack itemstack10 = slot4.getItem();
            if (!itemstack6.isEmpty() || !itemstack10.isEmpty()) {
                if (itemstack6.isEmpty()) {
                    if (slot4.isAllowed(entityhuman)) {
                        playerinventory.setItem(j, itemstack10);
                        slot4.b(itemstack10.getCount());
                        slot4.set(ItemStack.a);
                        slot4.a(entityhuman, itemstack10);
                    }
                } else if (itemstack10.isEmpty()) {
                    if (slot4.isAllowed(itemstack6)) {
                        int j2 = slot4.getMaxStackSize(itemstack6);
                        if (itemstack6.getCount() > j2) {
                            slot4.set(itemstack6.cloneAndSubtract(j2));
                        } else {
                            slot4.set(itemstack6);
                            playerinventory.setItem(j, ItemStack.a);
                        }
                    }
                } else if (slot4.isAllowed(entityhuman) && slot4.isAllowed(itemstack6)) {
                    int k2 = slot4.getMaxStackSize(itemstack6);
                    if (itemstack6.getCount() > k2) {
                        slot4.set(itemstack6.cloneAndSubtract(k2));
                        slot4.a(entityhuman, itemstack10);
                        if (!playerinventory.pickup(itemstack10)) {
                            entityhuman.drop(itemstack10, true);
                        }
                    } else {
                        slot4.set(itemstack6);
                        playerinventory.setItem(j, itemstack10);
                        slot4.a(entityhuman, itemstack10);
                    }
                }
            }
        } else if (inventoryclicktype == InventoryClickType.CLONE && entityhuman.abilities.canInstantlyBuild && playerinventory.getCarried().isEmpty() && ix >= 0) {
            Slot slot3 = (Slot)this.slots.get(ix);
            if (slot3 != null && slot3.hasItem()) {
                ItemStack itemstack5 = slot3.getItem().cloneItemStack();
                itemstack5.setCount(itemstack5.getMaxStackSize());
                playerinventory.setCarried(itemstack5);
            }
        } else if (inventoryclicktype == InventoryClickType.THROW && playerinventory.getCarried().isEmpty() && ix >= 0) {
            Slot slot2 = (Slot)this.slots.get(ix);
            if (slot2 != null && slot2.hasItem() && slot2.isAllowed(entityhuman)) {
                ItemStack itemstack4 = slot2.a(j == 0 ? 1 : slot2.getItem().getCount());
                slot2.a(entityhuman, itemstack4);
                entityhuman.drop(itemstack4, true);
            }
        } else if (inventoryclicktype == InventoryClickType.PICKUP_ALL && ix >= 0) {
            Slot slot = (Slot)this.slots.get(ix);
            ItemStack itemstack1 = playerinventory.getCarried();
            if (!itemstack1.isEmpty() && (slot == null || !slot.hasItem() || !slot.isAllowed(entityhuman))) {
                int k = j == 0 ? 0 : this.slots.size() - 1;
                int l = j == 0 ? 1 : -1;

                for(int i1 = 0; i1 < 2; ++i1) {
                    for(int j1 = k; j1 >= 0 && j1 < this.slots.size() && itemstack1.getCount() < itemstack1.getMaxStackSize(); j1 += l) {
                        Slot slot1 = (Slot)this.slots.get(j1);
                        if (slot1.hasItem() && a(slot1, itemstack1, true) && slot1.isAllowed(entityhuman) && this.a(itemstack1, slot1)) {
                            ItemStack itemstack2 = slot1.getItem();
                            if (i1 != 0 || itemstack2.getCount() != itemstack2.getMaxStackSize()) {
                                int k1 = Math.min(itemstack1.getMaxStackSize() - itemstack1.getCount(), itemstack2.getCount());
                                ItemStack itemstack3 = slot1.a(k1);
                                itemstack1.add(k1);
                                if (itemstack3.isEmpty()) {
                                    slot1.set(ItemStack.a);
                                }

                                slot1.a(entityhuman, itemstack3);
                            }
                        }
                    }
                }
            }

            this.b();
        }

        return itemstack;
    }

    public static boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack.getItem() == itemstack1.getItem() && ItemStack.equals(itemstack, itemstack1);
    }

    public boolean a(ItemStack var1, Slot var2) {
        return true;
    }

    public void b(EntityHuman entityhuman) {
        PlayerInventory playerinventory = entityhuman.inventory;
        if (!playerinventory.getCarried().isEmpty()) {
            entityhuman.drop(playerinventory.getCarried(), false);
            playerinventory.setCarried(ItemStack.a);
        }

    }

    protected void a(EntityHuman entityhuman, World world, IInventory iinventory) {
        if (!entityhuman.isAlive() || entityhuman instanceof EntityPlayer && ((EntityPlayer)entityhuman).o()) {
            for(int j = 0; j < iinventory.getSize(); ++j) {
                entityhuman.drop(iinventory.splitWithoutUpdate(j), false);
            }

        } else {
            for(int ix = 0; ix < iinventory.getSize(); ++ix) {
                entityhuman.inventory.a(world, iinventory.splitWithoutUpdate(ix));
            }

        }
    }

    public void a(IInventory var1) {
        this.b();
    }

    public void setItem(int ix, ItemStack itemstack) {
        this.getSlot(ix).set(itemstack);
    }

    public boolean c(EntityHuman entityhuman) {
        return !this.i.contains(entityhuman);
    }

    public void a(EntityHuman entityhuman, boolean flag) {
        if (flag) {
            this.i.remove(entityhuman);
        } else {
            this.i.add(entityhuman);
        }

    }

    public abstract boolean canUse(EntityHuman var1);

    protected boolean a(ItemStack itemstack, int ix, int j, boolean flag) {
        boolean flag1 = false;
        int k = ix;
        if (flag) {
            k = j - 1;
        }

        if (itemstack.isStackable()) {
            while(!itemstack.isEmpty()) {
                if (flag) {
                    if (k < ix) {
                        break;
                    }
                } else if (k >= j) {
                    break;
                }

                Slot slot = (Slot)this.slots.get(k);
                ItemStack itemstack1 = slot.getItem();
                if (!itemstack1.isEmpty() && a(itemstack, itemstack1)) {
                    int l = itemstack1.getCount() + itemstack.getCount();
                    if (l <= itemstack.getMaxStackSize()) {
                        itemstack.setCount(0);
                        itemstack1.setCount(l);
                        slot.f();
                        flag1 = true;
                    } else if (itemstack1.getCount() < itemstack.getMaxStackSize()) {
                        itemstack.subtract(itemstack.getMaxStackSize() - itemstack1.getCount());
                        itemstack1.setCount(itemstack.getMaxStackSize());
                        slot.f();
                        flag1 = true;
                    }
                }

                if (flag) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (!itemstack.isEmpty()) {
            if (flag) {
                k = j - 1;
            } else {
                k = ix;
            }

            while(true) {
                if (flag) {
                    if (k < ix) {
                        break;
                    }
                } else if (k >= j) {
                    break;
                }

                Slot slot1 = (Slot)this.slots.get(k);
                ItemStack itemstack2 = slot1.getItem();
                if (itemstack2.isEmpty() && slot1.isAllowed(itemstack)) {
                    if (itemstack.getCount() > slot1.getMaxStackSize()) {
                        slot1.set(itemstack.cloneAndSubtract(slot1.getMaxStackSize()));
                    } else {
                        slot1.set(itemstack.cloneAndSubtract(itemstack.getCount()));
                    }

                    slot1.f();
                    flag1 = true;
                    break;
                }

                if (flag) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        return flag1;
    }

    public static int b(int ix) {
        return ix >> 2 & 3;
    }

    public static int c(int ix) {
        return ix & 3;
    }

    public static boolean a(int ix, EntityHuman entityhuman) {
        if (ix == 0) {
            return true;
        } else if (ix == 1) {
            return true;
        } else {
            return ix == 2 && entityhuman.abilities.canInstantlyBuild;
        }
    }

    protected void c() {
        this.g = 0;
        this.h.clear();
    }

    public static boolean a(@Nullable Slot slot, ItemStack itemstack, boolean flag) {
        boolean flag1 = slot == null || !slot.hasItem();
        if (!flag1 && itemstack.doMaterialsMatch(slot.getItem()) && ItemStack.equals(slot.getItem(), itemstack)) {
            return slot.getItem().getCount() + (flag ? 0 : itemstack.getCount()) <= itemstack.getMaxStackSize();
        } else {
            return flag1;
        }
    }

    public static void a(Set<Slot> set, int ix, ItemStack itemstack, int j) {
        switch(ix) {
        case 0:
            itemstack.setCount(MathHelper.d((float)itemstack.getCount() / (float)set.size()));
            break;
        case 1:
            itemstack.setCount(1);
            break;
        case 2:
            itemstack.setCount(itemstack.getItem().getMaxStackSize());
        }

        itemstack.add(j);
    }

    public boolean b(Slot var1) {
        return true;
    }

    public static int a(@Nullable TileEntity tileentity) {
        return tileentity instanceof IInventory ? b((IInventory)tileentity) : 0;
    }

    public static int b(@Nullable IInventory iinventory) {
        if (iinventory == null) {
            return 0;
        } else {
            int ix = 0;
            float f = 0.0F;

            for(int j = 0; j < iinventory.getSize(); ++j) {
                ItemStack itemstack = iinventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    f += (float)itemstack.getCount() / (float)Math.min(iinventory.getMaxStackSize(), itemstack.getMaxStackSize());
                    ++ix;
                }
            }

            f = f / (float)iinventory.getSize();
            return MathHelper.d(f * 14.0F) + (ix > 0 ? 1 : 0);
        }
    }

    protected void a(World world, EntityHuman entityhuman, IInventory iinventory, InventoryCraftResult inventorycraftresult) {
        if (!world.isClientSide) {
            EntityPlayer entityplayer = (EntityPlayer)entityhuman;
            ItemStack itemstack = ItemStack.a;
            IRecipe irecipe = world.getMinecraftServer().getCraftingManager().b(iinventory, world);
            if (inventorycraftresult.a(world, entityplayer, irecipe) && irecipe != null) {
                itemstack = irecipe.craftItem(iinventory);
            }

            inventorycraftresult.setItem(0, itemstack);
            entityplayer.playerConnection.sendPacket(new PacketPlayOutSetSlot(this.windowId, 0, itemstack));
        }
    }
}
