package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public abstract class TileEntityLootable extends TileEntityContainer implements ILootable {
    protected MinecraftKey g;
    protected long h;
    protected IChatBaseComponent i;

    protected TileEntityLootable(TileEntityTypes<?> tileentitytypes) {
        super(tileentitytypes);
    }

    public static void a(IBlockAccess iblockaccess, Random random, BlockPosition blockposition, MinecraftKey minecraftkey) {
        TileEntity tileentity = iblockaccess.getTileEntity(blockposition);
        if (tileentity instanceof TileEntityLootable) {
            ((TileEntityLootable)tileentity).setLootTable(minecraftkey, random.nextLong());
        }

    }

    protected boolean d(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKeyOfType("LootTable", 8)) {
            this.g = new MinecraftKey(nbttagcompound.getString("LootTable"));
            this.h = nbttagcompound.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    protected boolean e(NBTTagCompound nbttagcompound) {
        if (this.g == null) {
            return false;
        } else {
            nbttagcompound.setString("LootTable", this.g.toString());
            if (this.h != 0L) {
                nbttagcompound.setLong("LootTableSeed", this.h);
            }

            return true;
        }
    }

    public void d(@Nullable EntityHuman entityhuman) {
        if (this.g != null && this.world.getMinecraftServer() != null) {
            LootTable loottable = this.world.getMinecraftServer().getLootTableRegistry().getLootTable(this.g);
            this.g = null;
            Random random;
            if (this.h == 0L) {
                random = new Random();
            } else {
                random = new Random(this.h);
            }

            LootTableInfo.Builder loottableinfo$builder = new LootTableInfo.Builder((WorldServer)this.world);
            loottableinfo$builder.position(this.position);
            if (entityhuman != null) {
                loottableinfo$builder.luck(entityhuman.dJ());
            }

            loottable.a(this, random, loottableinfo$builder.build());
        }

    }

    public MinecraftKey getLootTable() {
        return this.g;
    }

    public void setLootTable(MinecraftKey minecraftkey, long ix) {
        this.g = minecraftkey;
        this.h = ix;
    }

    public boolean hasCustomName() {
        return this.i != null;
    }

    public void setCustomName(@Nullable IChatBaseComponent ichatbasecomponent) {
        this.i = ichatbasecomponent;
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return this.i;
    }

    public ItemStack getItem(int ix) {
        this.d((EntityHuman)null);
        return (ItemStack)this.q().get(ix);
    }

    public ItemStack splitStack(int ix, int j) {
        this.d((EntityHuman)null);
        ItemStack itemstack = ContainerUtil.a(this.q(), ix, j);
        if (!itemstack.isEmpty()) {
            this.update();
        }

        return itemstack;
    }

    public ItemStack splitWithoutUpdate(int ix) {
        this.d((EntityHuman)null);
        return ContainerUtil.a(this.q(), ix);
    }

    public void setItem(int ix, @Nullable ItemStack itemstack) {
        this.d((EntityHuman)null);
        this.q().set(ix, itemstack);
        if (itemstack.getCount() > this.getMaxStackSize()) {
            itemstack.setCount(this.getMaxStackSize());
        }

        this.update();
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

    public boolean b(int var1, ItemStack var2) {
        return true;
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
        this.q().clear();
    }

    protected abstract NonNullList<ItemStack> q();

    protected abstract void a(NonNullList<ItemStack> var1);
}
