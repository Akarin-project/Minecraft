package net.minecraft.server;

import java.util.Arrays;
import javax.annotation.Nullable;

public class TileEntityBrewingStand extends TileEntityContainer implements IWorldInventory, ITickable {
    private static final int[] a = new int[]{3};
    private static final int[] e = new int[]{0, 1, 2, 3};
    private static final int[] f = new int[]{0, 1, 2, 4};
    private NonNullList<ItemStack> items = NonNullList.<ItemStack>a(5, ItemStack.a);
    private int brewTime;
    private boolean[] i;
    private Item j;
    private IChatBaseComponent k;
    private int fuelLevel;

    public TileEntityBrewingStand() {
        super(TileEntityTypes.BREWING_STAND);
    }

    public IChatBaseComponent getDisplayName() {
        return (IChatBaseComponent)(this.k != null ? this.k : new ChatMessage("container.brewing", new Object[0]));
    }

    public boolean hasCustomName() {
        return this.k != null;
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return this.k;
    }

    public void setCustomName(@Nullable IChatBaseComponent ichatbasecomponent) {
        this.k = ichatbasecomponent;
    }

    public int getSize() {
        return this.items.size();
    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void Y_() {
        ItemStack itemstack = this.items.get(4);
        if (this.fuelLevel <= 0 && itemstack.getItem() == Items.BLAZE_POWDER) {
            this.fuelLevel = 20;
            itemstack.subtract(1);
            this.update();
        }

        boolean flag = this.q();
        boolean flag1 = this.brewTime > 0;
        ItemStack itemstack1 = this.items.get(3);
        if (flag1) {
            --this.brewTime;
            boolean flag2 = this.brewTime == 0;
            if (flag2 && flag) {
                this.r();
                this.update();
            } else if (!flag) {
                this.brewTime = 0;
                this.update();
            } else if (this.j != itemstack1.getItem()) {
                this.brewTime = 0;
                this.update();
            }
        } else if (flag && this.fuelLevel > 0) {
            --this.fuelLevel;
            this.brewTime = 400;
            this.j = itemstack1.getItem();
            this.update();
        }

        if (!this.world.isClientSide) {
            boolean[] aboolean = this.p();
            if (!Arrays.equals(aboolean, this.i)) {
                this.i = aboolean;
                IBlockData iblockdata = this.world.getType(this.getPosition());
                if (!(iblockdata.getBlock() instanceof BlockBrewingStand)) {
                    return;
                }

                for(int ix = 0; ix < BlockBrewingStand.HAS_BOTTLE.length; ++ix) {
                    iblockdata = (IBlockData)iblockdata.set(BlockBrewingStand.HAS_BOTTLE[ix], Boolean.valueOf(aboolean[ix]));
                }

                this.world.setTypeAndData(this.position, iblockdata, 2);
            }
        }

    }

    public boolean[] p() {
        boolean[] aboolean = new boolean[3];

        for(int ix = 0; ix < 3; ++ix) {
            if (!((ItemStack)this.items.get(ix)).isEmpty()) {
                aboolean[ix] = true;
            }
        }

        return aboolean;
    }

    private boolean q() {
        ItemStack itemstack = this.items.get(3);
        if (itemstack.isEmpty()) {
            return false;
        } else if (!PotionBrewer.a(itemstack)) {
            return false;
        } else {
            for(int ix = 0; ix < 3; ++ix) {
                ItemStack itemstack1 = this.items.get(ix);
                if (!itemstack1.isEmpty() && PotionBrewer.a(itemstack1, itemstack)) {
                    return true;
                }
            }

            return false;
        }
    }

    private void r() {
        ItemStack itemstack = this.items.get(3);

        for(int ix = 0; ix < 3; ++ix) {
            this.items.set(ix, PotionBrewer.d(itemstack, this.items.get(ix)));
        }

        itemstack.subtract(1);
        BlockPosition blockposition = this.getPosition();
        if (itemstack.getItem().p()) {
            ItemStack itemstack1 = new ItemStack(itemstack.getItem().o());
            if (itemstack.isEmpty()) {
                itemstack = itemstack1;
            } else {
                InventoryUtils.dropItem(this.world, (double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ(), itemstack1);
            }
        }

        this.items.set(3, itemstack);
        this.world.triggerEffect(1035, blockposition, 0);
    }

    public void load(NBTTagCompound nbttagcompound) {
        super.load(nbttagcompound);
        this.items = NonNullList.<ItemStack>a(this.getSize(), ItemStack.a);
        ContainerUtil.b(nbttagcompound, this.items);
        this.brewTime = nbttagcompound.getShort("BrewTime");
        if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
            this.k = IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("CustomName"));
        }

        this.fuelLevel = nbttagcompound.getByte("Fuel");
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setShort("BrewTime", (short)this.brewTime);
        ContainerUtil.a(nbttagcompound, this.items);
        if (this.k != null) {
            nbttagcompound.setString("CustomName", IChatBaseComponent.ChatSerializer.a(this.k));
        }

        nbttagcompound.setByte("Fuel", (byte)this.fuelLevel);
        return nbttagcompound;
    }

    public ItemStack getItem(int ix) {
        return ix >= 0 && ix < this.items.size() ? (ItemStack)this.items.get(ix) : ItemStack.a;
    }

    public ItemStack splitStack(int ix, int jx) {
        return ContainerUtil.a(this.items, ix, jx);
    }

    public ItemStack splitWithoutUpdate(int ix) {
        return ContainerUtil.a(this.items, ix);
    }

    public void setItem(int ix, ItemStack itemstack) {
        if (ix >= 0 && ix < this.items.size()) {
            this.items.set(ix, itemstack);
        }

    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean a(EntityHuman entityhuman) {
        if (this.world.getTileEntity(this.position) != this) {
            return false;
        } else {
            return !(entityhuman.d((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) > 64.0D);
        }
    }

    public void startOpen(EntityHuman var1) {
    }

    public void closeContainer(EntityHuman var1) {
    }

    public boolean b(int ix, ItemStack itemstack) {
        if (ix == 3) {
            return PotionBrewer.a(itemstack);
        } else {
            Item item = itemstack.getItem();
            if (ix == 4) {
                return item == Items.BLAZE_POWDER;
            } else {
                return (item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE) && this.getItem(ix).isEmpty();
            }
        }
    }

    public int[] getSlotsForFace(EnumDirection enumdirection) {
        if (enumdirection == EnumDirection.UP) {
            return a;
        } else {
            return enumdirection == EnumDirection.DOWN ? e : f;
        }
    }

    public boolean canPlaceItemThroughFace(int ix, ItemStack itemstack, @Nullable EnumDirection var3) {
        return this.b(ix, itemstack);
    }

    public boolean canTakeItemThroughFace(int ix, ItemStack itemstack, EnumDirection var3) {
        if (ix == 3) {
            return itemstack.getItem() == Items.GLASS_BOTTLE;
        } else {
            return true;
        }
    }

    public String getContainerName() {
        return "minecraft:brewing_stand";
    }

    public Container createContainer(PlayerInventory playerinventory, EntityHuman var2) {
        return new ContainerBrewingStand(playerinventory, this);
    }

    public int getProperty(int ix) {
        switch(ix) {
        case 0:
            return this.brewTime;
        case 1:
            return this.fuelLevel;
        default:
            return 0;
        }
    }

    public void setProperty(int ix, int jx) {
        switch(ix) {
        case 0:
            this.brewTime = jx;
            break;
        case 1:
            this.fuelLevel = jx;
        }

    }

    public int h() {
        return 2;
    }

    public void clear() {
        this.items.clear();
    }
}
