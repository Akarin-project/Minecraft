package net.minecraft.server;

import java.util.UUID;
import javax.annotation.Nullable;

public class EntityItem extends Entity {
    private static final DataWatcherObject<ItemStack> b = DataWatcher.a(EntityItem.class, DataWatcherRegistry.g);
    private int age;
    public int pickupDelay;
    private int e;
    private UUID f;
    private UUID g;
    public float a;

    public EntityItem(World world) {
        super(EntityTypes.ITEM, world);
        this.e = 5;
        this.a = (float)(Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
    }

    public EntityItem(World world, double d0, double d1, double d2) {
        this(world);
        this.setPosition(d0, d1, d2);
        this.yaw = (float)(Math.random() * 360.0D);
        this.motX = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
        this.motY = (double)0.2F;
        this.motZ = (double)((float)(Math.random() * (double)0.2F - (double)0.1F));
    }

    public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
        this(world, d0, d1, d2);
        this.setItemStack(itemstack);
    }

    protected boolean playStepSound() {
        return false;
    }

    protected void x_() {
        this.getDataWatcher().register(b, ItemStack.a);
    }

    public void tick() {
        if (this.getItemStack().isEmpty()) {
            this.die();
        } else {
            super.tick();
            if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
                --this.pickupDelay;
            }

            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            double d0 = this.motX;
            double d1 = this.motY;
            double d2 = this.motZ;
            if (this.a(TagsFluid.WATER)) {
                this.u();
            } else if (!this.isNoGravity()) {
                this.motY -= (double)0.04F;
            }

            if (this.world.isClientSide) {
                this.noclip = false;
            } else {
                this.noclip = this.i(this.locX, (this.getBoundingBox().b + this.getBoundingBox().e) / 2.0D, this.locZ);
            }

            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
            boolean flag = (int)this.lastX != (int)this.locX || (int)this.lastY != (int)this.locY || (int)this.lastZ != (int)this.locZ;
            if (flag || this.ticksLived % 25 == 0) {
                if (this.world.b(new BlockPosition(this)).a(TagsFluid.LAVA)) {
                    this.motY = (double)0.2F;
                    this.motX = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    this.motZ = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    this.a(SoundEffects.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                }

                if (!this.world.isClientSide) {
                    this.v();
                }
            }

            float fx = 0.98F;
            if (this.onGround) {
                fx = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().n() * 0.98F;
            }

            this.motX *= (double)fx;
            this.motY *= (double)0.98F;
            this.motZ *= (double)fx;
            if (this.onGround) {
                this.motY *= -0.5D;
            }

            if (this.age != -32768) {
                ++this.age;
            }

            this.impulse |= this.at();
            if (!this.world.isClientSide) {
                double d3 = this.motX - d0;
                double d4 = this.motY - d1;
                double d5 = this.motZ - d2;
                double d6 = d3 * d3 + d4 * d4 + d5 * d5;
                if (d6 > 0.01D) {
                    this.impulse = true;
                }
            }

            if (!this.world.isClientSide && this.age >= 6000) {
                this.die();
            }

        }
    }

    private void u() {
        if (this.motY < (double)0.06F) {
            this.motY += (double)5.0E-4F;
        }

        this.motX *= (double)0.99F;
        this.motZ *= (double)0.99F;
    }

    private void v() {
        for(EntityItem entityitem1 : this.world.a(EntityItem.class, this.getBoundingBox().grow(0.5D, 0.0D, 0.5D))) {
            this.a(entityitem1);
        }

    }

    private boolean a(EntityItem entityitem1) {
        if (entityitem1 == this) {
            return false;
        } else if (entityitem1.isAlive() && this.isAlive()) {
            ItemStack itemstack = this.getItemStack();
            ItemStack itemstack1 = entityitem1.getItemStack().cloneItemStack();
            if (this.pickupDelay != 32767 && entityitem1.pickupDelay != 32767) {
                if (this.age != -32768 && entityitem1.age != -32768) {
                    if (itemstack1.getItem() != itemstack.getItem()) {
                        return false;
                    } else if (itemstack1.hasTag() ^ itemstack.hasTag()) {
                        return false;
                    } else if (itemstack1.hasTag() && !itemstack1.getTag().equals(itemstack.getTag())) {
                        return false;
                    } else if (itemstack1.getItem() == null) {
                        return false;
                    } else if (itemstack1.getCount() < itemstack.getCount()) {
                        return entityitem1.a(this);
                    } else if (itemstack1.getCount() + itemstack.getCount() > itemstack1.getMaxStackSize()) {
                        return false;
                    } else {
                        itemstack1.add(itemstack.getCount());
                        entityitem1.pickupDelay = Math.max(entityitem1.pickupDelay, this.pickupDelay);
                        entityitem1.age = Math.min(entityitem1.age, this.age);
                        entityitem1.setItemStack(itemstack1);
                        this.die();
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void f() {
        this.age = 4800;
    }

    protected void burn(int i) {
        this.damageEntity(DamageSource.FIRE, (float)i);
    }

    public boolean damageEntity(DamageSource damagesource, float fx) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else if (!this.getItemStack().isEmpty() && this.getItemStack().getItem() == Items.NETHER_STAR && damagesource.isExplosion()) {
            return false;
        } else {
            this.aA();
            this.e = (int)((float)this.e - fx);
            if (this.e <= 0) {
                this.die();
            }

            return false;
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("Health", (short)this.e);
        nbttagcompound.setShort("Age", (short)this.age);
        nbttagcompound.setShort("PickupDelay", (short)this.pickupDelay);
        if (this.l() != null) {
            nbttagcompound.set("Thrower", GameProfileSerializer.a(this.l()));
        }

        if (this.k() != null) {
            nbttagcompound.set("Owner", GameProfileSerializer.a(this.k()));
        }

        if (!this.getItemStack().isEmpty()) {
            nbttagcompound.set("Item", this.getItemStack().save(new NBTTagCompound()));
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.getShort("Health");
        this.age = nbttagcompound.getShort("Age");
        if (nbttagcompound.hasKey("PickupDelay")) {
            this.pickupDelay = nbttagcompound.getShort("PickupDelay");
        }

        if (nbttagcompound.hasKeyOfType("Owner", 10)) {
            this.g = GameProfileSerializer.b(nbttagcompound.getCompound("Owner"));
        }

        if (nbttagcompound.hasKeyOfType("Thrower", 10)) {
            this.f = GameProfileSerializer.b(nbttagcompound.getCompound("Thrower"));
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Item");
        this.setItemStack(ItemStack.a(nbttagcompound1));
        if (this.getItemStack().isEmpty()) {
            this.die();
        }

    }

    public void d(EntityHuman entityhuman) {
        if (!this.world.isClientSide) {
            ItemStack itemstack = this.getItemStack();
            Item item = itemstack.getItem();
            int i = itemstack.getCount();
            if (this.pickupDelay == 0 && (this.g == null || 6000 - this.age <= 200 || this.g.equals(entityhuman.getUniqueID())) && entityhuman.inventory.pickup(itemstack)) {
                entityhuman.receive(this, i);
                if (itemstack.isEmpty()) {
                    this.die();
                    itemstack.setCount(i);
                }

                entityhuman.a(StatisticList.ITEM_PICKED_UP.b(item), i);
            }

        }
    }

    public IChatBaseComponent getDisplayName() {
        IChatBaseComponent ichatbasecomponent = this.getCustomName();
        return (IChatBaseComponent)(ichatbasecomponent != null ? ichatbasecomponent : new ChatMessage(this.getItemStack().j(), new Object[0]));
    }

    public boolean bk() {
        return false;
    }

    @Nullable
    public Entity a(DimensionManager dimensionmanager) {
        Entity entity = super.a(dimensionmanager);
        if (!this.world.isClientSide && entity instanceof EntityItem) {
            ((EntityItem)entity).v();
        }

        return entity;
    }

    public ItemStack getItemStack() {
        return (ItemStack)this.getDataWatcher().get(b);
    }

    public void setItemStack(ItemStack itemstack) {
        this.getDataWatcher().set(b, itemstack);
    }

    @Nullable
    public UUID k() {
        return this.g;
    }

    public void b(@Nullable UUID uuid) {
        this.g = uuid;
    }

    @Nullable
    public UUID l() {
        return this.f;
    }

    public void c(@Nullable UUID uuid) {
        this.f = uuid;
    }

    public void n() {
        this.pickupDelay = 10;
    }

    public void o() {
        this.pickupDelay = 0;
    }

    public void p() {
        this.pickupDelay = 32767;
    }

    public void a(int i) {
        this.pickupDelay = i;
    }

    public boolean q() {
        return this.pickupDelay > 0;
    }

    public void s() {
        this.age = -6000;
    }

    public void t() {
        this.p();
        this.age = 5999;
    }
}
