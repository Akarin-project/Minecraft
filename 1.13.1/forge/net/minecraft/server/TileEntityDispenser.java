package net.minecraft.server;

import java.util.Random;

public class TileEntityDispenser extends TileEntityLootable {
    private static final Random a = new Random();
    private NonNullList<ItemStack> items;

    protected TileEntityDispenser(TileEntityTypes<?> tileentitytypes) {
        super(tileentitytypes);
        this.items = NonNullList.<ItemStack>a(9, ItemStack.a);
    }

    public TileEntityDispenser() {
        this(TileEntityTypes.DISPENSER);
    }

    public int getSize() {
        return 9;
    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public int p() {
        this.d((EntityHuman)null);
        int i = -1;
        int j = 1;

        for(int k = 0; k < this.items.size(); ++k) {
            if (!((ItemStack)this.items.get(k)).isEmpty() && a.nextInt(j++) == 0) {
                i = k;
            }
        }

        return i;
    }

    public int addItem(ItemStack itemstack) {
        for(int i = 0; i < this.items.size(); ++i) {
            if (((ItemStack)this.items.get(i)).isEmpty()) {
                this.setItem(i, itemstack);
                return i;
            }
        }

        return -1;
    }

    public IChatBaseComponent getDisplayName() {
        IChatBaseComponent ichatbasecomponent = this.getCustomName();
        return (IChatBaseComponent)(ichatbasecomponent != null ? ichatbasecomponent : new ChatMessage("container.dispenser", new Object[0]));
    }

    public void load(NBTTagCompound nbttagcompound) {
        super.load(nbttagcompound);
        this.items = NonNullList.<ItemStack>a(this.getSize(), ItemStack.a);
        if (!this.d(nbttagcompound)) {
            ContainerUtil.b(nbttagcompound, this.items);
        }

        if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
            this.i = IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("CustomName"));
        }

    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        if (!this.e(nbttagcompound)) {
            ContainerUtil.a(nbttagcompound, this.items);
        }

        IChatBaseComponent ichatbasecomponent = this.getCustomName();
        if (ichatbasecomponent != null) {
            nbttagcompound.setString("CustomName", IChatBaseComponent.ChatSerializer.a(ichatbasecomponent));
        }

        return nbttagcompound;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public String getContainerName() {
        return "minecraft:dispenser";
    }

    public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
        this.d(entityhuman);
        return new ContainerDispenser(playerinventory, this);
    }

    protected NonNullList<ItemStack> q() {
        return this.items;
    }

    protected void a(NonNullList<ItemStack> nonnulllist) {
        this.items = nonnulllist;
    }
}
