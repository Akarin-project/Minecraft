package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class PlayerInventory implements IInventory {
    public final NonNullList<ItemStack> items = NonNullList.<ItemStack>a(36, ItemStack.a);
    public final NonNullList<ItemStack> armor = NonNullList.<ItemStack>a(4, ItemStack.a);
    public final NonNullList<ItemStack> extraSlots = NonNullList.<ItemStack>a(1, ItemStack.a);
    private final List<NonNullList<ItemStack>> f;
    public int itemInHandIndex;
    public EntityHuman player;
    private ItemStack carried;
    private int h;

    public PlayerInventory(EntityHuman entityhuman) {
        this.f = ImmutableList.of(this.items, this.armor, this.extraSlots);
        this.carried = ItemStack.a;
        this.player = entityhuman;
    }

    public ItemStack getItemInHand() {
        return e(this.itemInHandIndex) ? (ItemStack)this.items.get(this.itemInHandIndex) : ItemStack.a;
    }

    public static int getHotbarSize() {
        return 9;
    }

    private boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return !itemstack.isEmpty() && this.b(itemstack, itemstack1) && itemstack.isStackable() && itemstack.getCount() < itemstack.getMaxStackSize() && itemstack.getCount() < this.getMaxStackSize();
    }

    private boolean b(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack.getItem() == itemstack1.getItem() && ItemStack.equals(itemstack, itemstack1);
    }

    public int getFirstEmptySlotIndex() {
        for(int i = 0; i < this.items.size(); ++i) {
            if (((ItemStack)this.items.get(i)).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    public void d(int i) {
        this.itemInHandIndex = this.l();
        ItemStack itemstack = this.items.get(this.itemInHandIndex);
        this.items.set(this.itemInHandIndex, this.items.get(i));
        this.items.set(i, itemstack);
    }

    public static boolean e(int i) {
        return i >= 0 && i < 9;
    }

    public int c(ItemStack itemstack) {
        for(int i = 0; i < this.items.size(); ++i) {
            ItemStack itemstack1 = this.items.get(i);
            if (!((ItemStack)this.items.get(i)).isEmpty() && this.b(itemstack, this.items.get(i)) && !((ItemStack)this.items.get(i)).f() && !itemstack1.hasEnchantments() && !itemstack1.hasName()) {
                return i;
            }
        }

        return -1;
    }

    public int l() {
        for(int i = 0; i < 9; ++i) {
            int j = (this.itemInHandIndex + i) % 9;
            if (((ItemStack)this.items.get(j)).isEmpty()) {
                return j;
            }
        }

        for(int k = 0; k < 9; ++k) {
            int l = (this.itemInHandIndex + k) % 9;
            if (!((ItemStack)this.items.get(l)).hasEnchantments()) {
                return l;
            }
        }

        return this.itemInHandIndex;
    }

    public int a(Predicate<ItemStack> predicate, int i) {
        int j = 0;

        for(int k = 0; k < this.getSize(); ++k) {
            ItemStack itemstack = this.getItem(k);
            if (!itemstack.isEmpty() && predicate.test(itemstack)) {
                int l = i <= 0 ? itemstack.getCount() : Math.min(i - j, itemstack.getCount());
                j += l;
                if (i != 0) {
                    itemstack.subtract(l);
                    if (itemstack.isEmpty()) {
                        this.setItem(k, ItemStack.a);
                    }

                    if (i > 0 && j >= i) {
                        return j;
                    }
                }
            }
        }

        if (!this.carried.isEmpty() && predicate.test(this.carried)) {
            int i1 = i <= 0 ? this.carried.getCount() : Math.min(i - j, this.carried.getCount());
            j += i1;
            if (i != 0) {
                this.carried.subtract(i1);
                if (this.carried.isEmpty()) {
                    this.carried = ItemStack.a;
                }

                if (i > 0 && j >= i) {
                    return j;
                }
            }
        }

        return j;
    }

    private int i(ItemStack itemstack) {
        int i = this.firstPartial(itemstack);
        if (i == -1) {
            i = this.getFirstEmptySlotIndex();
        }

        return i == -1 ? itemstack.getCount() : this.d(i, itemstack);
    }

    private int d(int i, ItemStack itemstack) {
        Item item = itemstack.getItem();
        int j = itemstack.getCount();
        ItemStack itemstack1 = this.getItem(i);
        if (itemstack1.isEmpty()) {
            itemstack1 = new ItemStack(item, 0);
            if (itemstack.hasTag()) {
                itemstack1.setTag(itemstack.getTag().clone());
            }

            this.setItem(i, itemstack1);
        }

        int k = j;
        if (j > itemstack1.getMaxStackSize() - itemstack1.getCount()) {
            k = itemstack1.getMaxStackSize() - itemstack1.getCount();
        }

        if (k > this.getMaxStackSize() - itemstack1.getCount()) {
            k = this.getMaxStackSize() - itemstack1.getCount();
        }

        if (k == 0) {
            return j;
        } else {
            j = j - k;
            itemstack1.add(k);
            itemstack1.d(5);
            return j;
        }
    }

    public int firstPartial(ItemStack itemstack) {
        if (this.a(this.getItem(this.itemInHandIndex), itemstack)) {
            return this.itemInHandIndex;
        } else if (this.a(this.getItem(40), itemstack)) {
            return 40;
        } else {
            for(int i = 0; i < this.items.size(); ++i) {
                if (this.a(this.items.get(i), itemstack)) {
                    return i;
                }
            }

            return -1;
        }
    }

    public void p() {
        for(NonNullList nonnulllist : this.f) {
            for(int i = 0; i < nonnulllist.size(); ++i) {
                if (!((ItemStack)nonnulllist.get(i)).isEmpty()) {
                    ((ItemStack)nonnulllist.get(i)).a(this.player.world, this.player, i, this.itemInHandIndex == i);
                }
            }
        }

    }

    public boolean pickup(ItemStack itemstack) {
        return this.c(-1, itemstack);
    }

    public boolean c(int i, ItemStack itemstack) {
        if (itemstack.isEmpty()) {
            return false;
        } else {
            try {
                if (itemstack.f()) {
                    if (i == -1) {
                        i = this.getFirstEmptySlotIndex();
                    }

                    if (i >= 0) {
                        this.items.set(i, itemstack.cloneItemStack());
                        ((ItemStack)this.items.get(i)).d(5);
                        itemstack.setCount(0);
                        return true;
                    } else if (this.player.abilities.canInstantlyBuild) {
                        itemstack.setCount(0);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    int j;
                    while(true) {
                        j = itemstack.getCount();
                        if (i == -1) {
                            itemstack.setCount(this.i(itemstack));
                        } else {
                            itemstack.setCount(this.d(i, itemstack));
                        }

                        if (itemstack.isEmpty() || itemstack.getCount() >= j) {
                            break;
                        }
                    }

                    if (itemstack.getCount() == j && this.player.abilities.canInstantlyBuild) {
                        itemstack.setCount(0);
                        return true;
                    } else {
                        return itemstack.getCount() < j;
                    }
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Adding item to inventory");
                CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Item being added");
                crashreportsystemdetails.a("Item ID", Item.getId(itemstack.getItem()));
                crashreportsystemdetails.a("Item data", itemstack.getDamage());
                crashreportsystemdetails.a("Item name", () -> {
                    return itemstack.getName().getString();
                });
                throw new ReportedException(crashreport);
            }
        }
    }

    public void a(World world, ItemStack itemstack) {
        if (!world.isClientSide) {
            while(!itemstack.isEmpty()) {
                int i = this.firstPartial(itemstack);
                if (i == -1) {
                    i = this.getFirstEmptySlotIndex();
                }

                if (i == -1) {
                    this.player.drop(itemstack, false);
                    break;
                }

                int j = itemstack.getMaxStackSize() - this.getItem(i).getCount();
                if (this.c(i, itemstack.cloneAndSubtract(j))) {
                    ((EntityPlayer)this.player).playerConnection.sendPacket(new PacketPlayOutSetSlot(-2, i, this.getItem(i)));
                }
            }

        }
    }

    public ItemStack splitStack(int i, int j) {
        NonNullList nonnulllist = null;

        for(NonNullList nonnulllist1 : this.f) {
            if (i < nonnulllist1.size()) {
                nonnulllist = nonnulllist1;
                break;
            }

            i -= nonnulllist1.size();
        }

        return nonnulllist != null && !((ItemStack)nonnulllist.get(i)).isEmpty() ? ContainerUtil.a(nonnulllist, i, j) : ItemStack.a;
    }

    public void f(ItemStack itemstack) {
        for(NonNullList nonnulllist : this.f) {
            for(int i = 0; i < nonnulllist.size(); ++i) {
                if (nonnulllist.get(i) == itemstack) {
                    nonnulllist.set(i, ItemStack.a);
                    break;
                }
            }
        }

    }

    public ItemStack splitWithoutUpdate(int i) {
        NonNullList nonnulllist = null;

        for(NonNullList nonnulllist1 : this.f) {
            if (i < nonnulllist1.size()) {
                nonnulllist = nonnulllist1;
                break;
            }

            i -= nonnulllist1.size();
        }

        if (nonnulllist != null && !((ItemStack)nonnulllist.get(i)).isEmpty()) {
            ItemStack itemstack = (ItemStack)nonnulllist.get(i);
            nonnulllist.set(i, ItemStack.a);
            return itemstack;
        } else {
            return ItemStack.a;
        }
    }

    public void setItem(int i, ItemStack itemstack) {
        NonNullList nonnulllist = null;

        for(NonNullList nonnulllist1 : this.f) {
            if (i < nonnulllist1.size()) {
                nonnulllist = nonnulllist1;
                break;
            }

            i -= nonnulllist1.size();
        }

        if (nonnulllist != null) {
            nonnulllist.set(i, itemstack);
        }

    }

    public float a(IBlockData iblockdata) {
        return ((ItemStack)this.items.get(this.itemInHandIndex)).a(iblockdata);
    }

    public NBTTagList a(NBTTagList nbttaglist) {
        for(int i = 0; i < this.items.size(); ++i) {
            if (!((ItemStack)this.items.get(i)).isEmpty()) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                ((ItemStack)this.items.get(i)).save(nbttagcompound);
                nbttaglist.add((NBTBase)nbttagcompound);
            }
        }

        for(int j = 0; j < this.armor.size(); ++j) {
            if (!((ItemStack)this.armor.get(j)).isEmpty()) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)(j + 100));
                ((ItemStack)this.armor.get(j)).save(nbttagcompound1);
                nbttaglist.add((NBTBase)nbttagcompound1);
            }
        }

        for(int k = 0; k < this.extraSlots.size(); ++k) {
            if (!((ItemStack)this.extraSlots.get(k)).isEmpty()) {
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.setByte("Slot", (byte)(k + 150));
                ((ItemStack)this.extraSlots.get(k)).save(nbttagcompound2);
                nbttaglist.add((NBTBase)nbttagcompound2);
            }
        }

        return nbttaglist;
    }

    public void b(NBTTagList nbttaglist) {
        this.items.clear();
        this.armor.clear();
        this.extraSlots.clear();

        for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.a(nbttagcompound);
            if (!itemstack.isEmpty()) {
                if (j >= 0 && j < this.items.size()) {
                    this.items.set(j, itemstack);
                } else if (j >= 100 && j < this.armor.size() + 100) {
                    this.armor.set(j - 100, itemstack);
                } else if (j >= 150 && j < this.extraSlots.size() + 150) {
                    this.extraSlots.set(j - 150, itemstack);
                }
            }
        }

    }

    public int getSize() {
        return this.items.size() + this.armor.size() + this.extraSlots.size();
    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        for(ItemStack itemstack1 : this.armor) {
            if (!itemstack1.isEmpty()) {
                return false;
            }
        }

        for(ItemStack itemstack2 : this.extraSlots) {
            if (!itemstack2.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int i) {
        NonNullList nonnulllist = null;

        for(NonNullList nonnulllist1 : this.f) {
            if (i < nonnulllist1.size()) {
                nonnulllist = nonnulllist1;
                break;
            }

            i -= nonnulllist1.size();
        }

        return nonnulllist == null ? ItemStack.a : (ItemStack)nonnulllist.get(i);
    }

    public IChatBaseComponent getDisplayName() {
        return new ChatMessage("container.inventory", new Object[0]);
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return null;
    }

    public boolean hasCustomName() {
        return false;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean b(IBlockData iblockdata) {
        return this.getItem(this.itemInHandIndex).b(iblockdata);
    }

    public void a(float fx) {
        if (!(fx <= 0.0F)) {
            fx = fx / 4.0F;
            if (fx < 1.0F) {
                fx = 1.0F;
            }

            for(int i = 0; i < this.armor.size(); ++i) {
                ItemStack itemstack = this.armor.get(i);
                if (itemstack.getItem() instanceof ItemArmor) {
                    itemstack.damage((int)fx, this.player);
                }
            }

        }
    }

    public void q() {
        for(List list : this.f) {
            for(int i = 0; i < list.size(); ++i) {
                ItemStack itemstack = (ItemStack)list.get(i);
                if (!itemstack.isEmpty()) {
                    this.player.a(itemstack, true, false);
                    list.set(i, ItemStack.a);
                }
            }
        }

    }

    public void update() {
        ++this.h;
    }

    public void setCarried(ItemStack itemstack) {
        this.carried = itemstack;
    }

    public ItemStack getCarried() {
        return this.carried;
    }

    public boolean a(EntityHuman entityhuman) {
        if (this.player.dead) {
            return false;
        } else {
            return !(entityhuman.h(this.player) > 64.0D);
        }
    }

    public boolean h(ItemStack itemstack) {
        label23:
        for(List list : this.f) {
            Iterator iterator = list.iterator();

            while(true) {
                if (!iterator.hasNext()) {
                    continue label23;
                }

                ItemStack itemstack1 = (ItemStack)iterator.next();
                if (!itemstack1.isEmpty() && itemstack1.doMaterialsMatch(itemstack)) {
                    break;
                }
            }

            return true;
        }

        return false;
    }

    public void startOpen(EntityHuman var1) {
    }

    public void closeContainer(EntityHuman var1) {
    }

    public boolean b(int var1, ItemStack var2) {
        return true;
    }

    public void a(PlayerInventory playerinventory1) {
        for(int i = 0; i < this.getSize(); ++i) {
            this.setItem(i, playerinventory1.getItem(i));
        }

        this.itemInHandIndex = playerinventory1.itemInHandIndex;
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
        for(List list : this.f) {
            list.clear();
        }

    }

    public void a(AutoRecipeStackManager autorecipestackmanager) {
        for(ItemStack itemstack : this.items) {
            autorecipestackmanager.a(itemstack);
        }

    }
}
