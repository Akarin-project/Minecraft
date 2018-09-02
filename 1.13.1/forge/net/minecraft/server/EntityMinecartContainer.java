package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public abstract class EntityMinecartContainer extends EntityMinecartAbstract implements ITileInventory, ILootable {
    private NonNullList<ItemStack> items = NonNullList.<ItemStack>a(36, ItemStack.a);
    private boolean b = true;
    private MinecraftKey c;
    public long lootTableSeed;

    protected EntityMinecartContainer(EntityTypes<?> entitytypes, World world) {
        super(entitytypes, world);
    }

    protected EntityMinecartContainer(EntityTypes<?> entitytypes, double d0, double d1, double d2, World world) {
        super(entitytypes, world, d0, d1, d2);
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
            InventoryUtils.dropEntity(this.world, this, this);
        }

    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int i) {
        this.f((EntityHuman)null);
        return this.items.get(i);
    }

    public ItemStack splitStack(int i, int j) {
        this.f((EntityHuman)null);
        return ContainerUtil.a(this.items, i, j);
    }

    public ItemStack splitWithoutUpdate(int i) {
        this.f((EntityHuman)null);
        ItemStack itemstack = this.items.get(i);
        if (itemstack.isEmpty()) {
            return ItemStack.a;
        } else {
            this.items.set(i, ItemStack.a);
            return itemstack;
        }
    }

    public void setItem(int i, ItemStack itemstack) {
        this.f((EntityHuman)null);
        this.items.set(i, itemstack);
        if (!itemstack.isEmpty() && itemstack.getCount() > this.getMaxStackSize()) {
            itemstack.setCount(this.getMaxStackSize());
        }

    }

    public boolean c(int i, ItemStack itemstack) {
        if (i >= 0 && i < this.getSize()) {
            this.setItem(i, itemstack);
            return true;
        } else {
            return false;
        }
    }

    public void update() {
    }

    public boolean a(EntityHuman entityhuman) {
        if (this.dead) {
            return false;
        } else {
            return !(entityhuman.h(this) > 64.0D);
        }
    }

    public void startOpen(EntityHuman var1) {
    }

    public void closeContainer(EntityHuman var1) {
    }

    public boolean b(int var1, ItemStack var2) {
        return true;
    }

    public int getMaxStackSize() {
        return 64;
    }

    @Nullable
    public Entity a(DimensionManager dimensionmanager) {
        this.b = false;
        return super.a(dimensionmanager);
    }

    public void die() {
        if (this.b) {
            InventoryUtils.dropEntity(this.world, this, this);
        }

        super.die();
    }

    public void b(boolean flag) {
        this.b = flag;
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.c != null) {
            nbttagcompound.setString("LootTable", this.c.toString());
            if (this.lootTableSeed != 0L) {
                nbttagcompound.setLong("LootTableSeed", this.lootTableSeed);
            }
        } else {
            ContainerUtil.a(nbttagcompound, this.items);
        }

    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.items = NonNullList.<ItemStack>a(this.getSize(), ItemStack.a);
        if (nbttagcompound.hasKeyOfType("LootTable", 8)) {
            this.c = new MinecraftKey(nbttagcompound.getString("LootTable"));
            this.lootTableSeed = nbttagcompound.getLong("LootTableSeed");
        } else {
            ContainerUtil.b(nbttagcompound, this.items);
        }

    }

    public boolean b(EntityHuman entityhuman, EnumHand var2) {
        if (!this.world.isClientSide) {
            entityhuman.openContainer(this);
        }

        return true;
    }

    protected void r() {
        float f = 0.98F;
        if (this.c == null) {
            int i = 15 - Container.b(this);
            f += (float)i * 0.001F;
        }

        this.motX *= (double)f;
        this.motY *= 0.0D;
        this.motZ *= (double)f;
    }

    public int getProperty(int var1) {
        return 0;
    }

    public void setProperty(int var1, int var2) {
    }

    public int h() {
        return 0;
    }

    public boolean isLocked() {
        return false;
    }

    public void setLock(ChestLock var1) {
    }

    public ChestLock getLock() {
        return ChestLock.a;
    }

    public void f(@Nullable EntityHuman entityhuman) {
        if (this.c != null && this.world.getMinecraftServer() != null) {
            LootTable loottable = this.world.getMinecraftServer().getLootTableRegistry().getLootTable(this.c);
            this.c = null;
            Random random;
            if (this.lootTableSeed == 0L) {
                random = new Random();
            } else {
                random = new Random(this.lootTableSeed);
            }

            LootTableInfo.Builder loottableinfo$builder = (new LootTableInfo.Builder((WorldServer)this.world)).position(new BlockPosition(this));
            if (entityhuman != null) {
                loottableinfo$builder.luck(entityhuman.dJ());
            }

            loottable.a(this, random, loottableinfo$builder.build());
        }

    }

    public void clear() {
        this.f((EntityHuman)null);
        this.items.clear();
    }

    public void a(MinecraftKey minecraftkey, long i) {
        this.c = minecraftkey;
        this.lootTableSeed = i;
    }

    public MinecraftKey getLootTable() {
        return this.c;
    }
}
