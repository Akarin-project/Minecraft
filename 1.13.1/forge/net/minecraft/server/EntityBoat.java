package net.minecraft.server;

import java.util.List;
import javax.annotation.Nullable;

public class EntityBoat extends Entity {
    private static final DataWatcherObject<Integer> a = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> b = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Float> c = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.c);
    private static final DataWatcherObject<Integer> d = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Boolean> e = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.i);
    private static final DataWatcherObject<Boolean> f = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.i);
    private static final DataWatcherObject<Integer> g = DataWatcher.a(EntityBoat.class, DataWatcherRegistry.b);
    private final float[] h;
    private float aw;
    private float ax;
    private float ay;
    private int az;
    private double aA;
    private double aB;
    private double aC;
    private double aD;
    private double aE;
    private boolean aF;
    private boolean aG;
    private boolean aH;
    private boolean aI;
    private double aJ;
    private float aK;
    private EntityBoat.EnumStatus aL;
    private EntityBoat.EnumStatus aM;
    private double aN;
    private boolean aO;
    private boolean aP;
    private float aQ;
    private float aR;
    private float aS;

    public EntityBoat(World world) {
        super(EntityTypes.BOAT, world);
        this.h = new float[2];
        this.j = true;
        this.setSize(1.375F, 0.5625F);
    }

    public EntityBoat(World world, double d0, double d1, double d2) {
        this(world);
        this.setPosition(d0, d1, d2);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
    }

    protected boolean playStepSound() {
        return false;
    }

    protected void x_() {
        this.datawatcher.register(a, 0);
        this.datawatcher.register(b, 1);
        this.datawatcher.register(c, 0.0F);
        this.datawatcher.register(d, EntityBoat.EnumBoatType.OAK.ordinal());
        this.datawatcher.register(e, false);
        this.datawatcher.register(f, false);
        this.datawatcher.register(g, 0);
    }

    @Nullable
    public AxisAlignedBB j(Entity entity) {
        return entity.isCollidable() ? entity.getBoundingBox() : null;
    }

    @Nullable
    public AxisAlignedBB al() {
        return this.getBoundingBox();
    }

    public boolean isCollidable() {
        return true;
    }

    public double aJ() {
        return -0.1D;
    }

    public boolean damageEntity(DamageSource damagesource, float fx) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        } else if (!this.world.isClientSide && !this.dead) {
            if (damagesource instanceof EntityDamageSourceIndirect && damagesource.getEntity() != null && this.w(damagesource.getEntity())) {
                return false;
            } else {
                this.c(-this.o());
                this.b(10);
                this.setDamage(this.m() + fx * 10.0F);
                this.aA();
                boolean flag = damagesource.getEntity() instanceof EntityHuman && ((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild;
                if (flag || this.m() > 40.0F) {
                    if (!flag && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.a(this.f());
                    }

                    this.die();
                }

                return true;
            }
        } else {
            return true;
        }
    }

    public void j(boolean flag) {
        if (!this.world.isClientSide) {
            this.aO = true;
            this.aP = flag;
            if (this.z() == 0) {
                this.d(60);
            }
        }

        this.world.addParticle(Particles.R, this.locX + (double)this.random.nextFloat(), this.locY + 0.7D, this.locZ + (double)this.random.nextFloat(), 0.0D, 0.0D, 0.0D);
        if (this.random.nextInt(20) == 0) {
            this.world.a(this.locX, this.locY, this.locZ, this.ae(), this.bV(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false);
        }

    }

    public void collide(Entity entity) {
        if (entity instanceof EntityBoat) {
            if (entity.getBoundingBox().b < this.getBoundingBox().e) {
                super.collide(entity);
            }
        } else if (entity.getBoundingBox().b <= this.getBoundingBox().b) {
            super.collide(entity);
        }

    }

    public Item f() {
        switch(this.getType()) {
        case OAK:
        default:
            return Items.OAK_BOAT;
        case SPRUCE:
            return Items.SPRUCE_BOAT;
        case BIRCH:
            return Items.BIRCH_BOAT;
        case JUNGLE:
            return Items.JUNGLE_BOAT;
        case ACACIA:
            return Items.ACACIA_BOAT;
        case DARK_OAK:
            return Items.DARK_OAK_BOAT;
        }
    }

    public boolean isInteractable() {
        return !this.dead;
    }

    public EnumDirection getAdjustedDirection() {
        return this.getDirection().e();
    }

    public void tick() {
        this.aM = this.aL;
        this.aL = this.s();
        if (this.aL != EntityBoat.EnumStatus.UNDER_WATER && this.aL != EntityBoat.EnumStatus.UNDER_FLOWING_WATER) {
            this.ax = 0.0F;
        } else {
            ++this.ax;
        }

        if (!this.world.isClientSide && this.ax >= 60.0F) {
            this.ejectPassengers();
        }

        if (this.n() > 0) {
            this.b(this.n() - 1);
        }

        if (this.m() > 0.0F) {
            this.setDamage(this.m() - 1.0F);
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        super.tick();
        this.r();
        if (this.bT()) {
            if (this.bP().isEmpty() || !(this.bP().get(0) instanceof EntityHuman)) {
                this.a(false, false);
            }

            this.v();
            if (this.world.isClientSide) {
                this.x();
                this.world.a(new PacketPlayInBoatMove(this.a(0), this.a(1)));
            }

            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
        } else {
            this.motX = 0.0D;
            this.motY = 0.0D;
            this.motZ = 0.0D;
        }

        this.q();

        for(int i = 0; i <= 1; ++i) {
            if (this.a(i)) {
                if (!this.isSilent() && (double)(this.h[i] % ((float)Math.PI * 2F)) <= (double)((float)Math.PI / 4F) && ((double)this.h[i] + (double)((float)Math.PI / 8F)) % (double)((float)Math.PI * 2F) >= (double)((float)Math.PI / 4F)) {
                    SoundEffect soundeffect = this.i();
                    if (soundeffect != null) {
                        Vec3D vec3d = this.f(1.0F);
                        double d0 = i == 1 ? -vec3d.z : vec3d.z;
                        double d1 = i == 1 ? vec3d.x : -vec3d.x;
                        this.world.a((EntityHuman)null, this.locX + d0, this.locY, this.locZ + d1, soundeffect, this.bV(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
                    }
                }

                this.h[i] = (float)((double)this.h[i] + (double)((float)Math.PI / 8F));
            } else {
                this.h[i] = 0.0F;
            }
        }

        this.checkBlockCollisions();
        List list = this.world.getEntities(this, this.getBoundingBox().grow((double)0.2F, (double)-0.01F, (double)0.2F), IEntitySelector.a(this));
        if (!list.isEmpty()) {
            boolean flag = !this.world.isClientSide && !(this.bO() instanceof EntityHuman);

            for(int j = 0; j < list.size(); ++j) {
                Entity entity = (Entity)list.get(j);
                if (!entity.w(this)) {
                    if (flag && this.bP().size() < 2 && !entity.isPassenger() && entity.width < this.width && entity instanceof EntityLiving && !(entity instanceof EntityWaterAnimal) && !(entity instanceof EntityHuman)) {
                        entity.startRiding(this);
                    } else {
                        this.collide(entity);
                    }
                }
            }
        }

    }

    private void q() {
        if (this.world.isClientSide) {
            int i = this.z();
            if (i > 0) {
                this.aQ += 0.05F;
            } else {
                this.aQ -= 0.1F;
            }

            this.aQ = MathHelper.a(this.aQ, 0.0F, 1.0F);
            this.aS = this.aR;
            this.aR = 10.0F * (float)Math.sin((double)(0.5F * (float)this.world.getTime())) * this.aQ;
        } else {
            if (!this.aO) {
                this.d(0);
            }

            int k = this.z();
            if (k > 0) {
                --k;
                this.d(k);
                int j = 60 - k - 1;
                if (j > 0 && k == 0) {
                    this.d(0);
                    if (this.aP) {
                        this.motY -= 0.7D;
                        this.ejectPassengers();
                    } else {
                        this.motY = this.a(EntityHuman.class) ? 2.7D : 0.6D;
                    }
                }

                this.aO = false;
            }
        }

    }

    @Nullable
    protected SoundEffect i() {
        switch(this.s()) {
        case IN_WATER:
        case UNDER_WATER:
        case UNDER_FLOWING_WATER:
            return SoundEffects.ENTITY_BOAT_PADDLE_WATER;
        case ON_LAND:
            return SoundEffects.ENTITY_BOAT_PADDLE_LAND;
        case IN_AIR:
        default:
            return null;
        }
    }

    private void r() {
        if (this.az > 0 && !this.bT()) {
            double d0 = this.locX + (this.aA - this.locX) / (double)this.az;
            double d1 = this.locY + (this.aB - this.locY) / (double)this.az;
            double d2 = this.locZ + (this.aC - this.locZ) / (double)this.az;
            double d3 = MathHelper.g(this.aD - (double)this.yaw);
            this.yaw = (float)((double)this.yaw + d3 / (double)this.az);
            this.pitch = (float)((double)this.pitch + (this.aE - (double)this.pitch) / (double)this.az);
            --this.az;
            this.setPosition(d0, d1, d2);
            this.setYawPitch(this.yaw, this.pitch);
        }
    }

    public void a(boolean flag, boolean flag1) {
        this.datawatcher.set(e, flag);
        this.datawatcher.set(f, flag1);
    }

    private EntityBoat.EnumStatus s() {
        EntityBoat.EnumStatus entityboat$enumstatus = this.u();
        if (entityboat$enumstatus != null) {
            this.aJ = this.getBoundingBox().e;
            return entityboat$enumstatus;
        } else if (this.t()) {
            return EntityBoat.EnumStatus.IN_WATER;
        } else {
            float fx = this.l();
            if (fx > 0.0F) {
                this.aK = fx;
                return EntityBoat.EnumStatus.ON_LAND;
            } else {
                return EntityBoat.EnumStatus.IN_AIR;
            }
        }
    }

    public float k() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int k = MathHelper.floor(axisalignedbb.e);
        int l = MathHelper.f(axisalignedbb.e - this.aN);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            label161:
            for(int k1 = k; k1 < l; ++k1) {
                float fx = 0.0F;

                for(int l1 = i; l1 < j; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        blockposition$b.f(l1, k1, i2);
                        Fluid fluid = this.world.b(blockposition$b);
                        if (fluid.a(TagsFluid.WATER)) {
                            fx = Math.max(fx, (float)k1 + fluid.f());
                        }

                        if (fx >= 1.0F) {
                            continue label161;
                        }
                    }
                }

                if (fx < 1.0F) {
                    float f2 = (float)blockposition$b.getY() + fx;
                    return f2;
                }
            }

            float f1 = (float)(l + 1);
            return f1;
        }
    }

    public float l() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.a, axisalignedbb.b - 0.001D, axisalignedbb.c, axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        int i = MathHelper.floor(axisalignedbb1.a) - 1;
        int j = MathHelper.f(axisalignedbb1.d) + 1;
        int k = MathHelper.floor(axisalignedbb1.b) - 1;
        int l = MathHelper.f(axisalignedbb1.e) + 1;
        int i1 = MathHelper.floor(axisalignedbb1.c) - 1;
        int j1 = MathHelper.f(axisalignedbb1.f) + 1;
        VoxelShape voxelshape = VoxelShapes.a(axisalignedbb1);
        float fx = 0.0F;
        int k1 = 0;

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(int l1 = i; l1 < j; ++l1) {
                for(int i2 = i1; i2 < j1; ++i2) {
                    int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
                    if (j2 != 2) {
                        for(int k2 = k; k2 < l; ++k2) {
                            if (j2 <= 0 || k2 != k && k2 != l - 1) {
                                blockposition$b.f(l1, k2, i2);
                                IBlockData iblockdata = this.world.getType(blockposition$b);
                                if (!(iblockdata.getBlock() instanceof BlockWaterLily) && VoxelShapes.c(iblockdata.h(this.world, blockposition$b).a((double)l1, (double)k2, (double)i2), voxelshape, OperatorBoolean.AND)) {
                                    fx += iblockdata.getBlock().n();
                                    ++k1;
                                }
                            }
                        }
                    }
                }
            }
        }

        return fx / (float)k1;
    }

    private boolean t() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int k = MathHelper.floor(axisalignedbb.b);
        int l = MathHelper.f(axisalignedbb.b + 0.001D);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);
        boolean flag = false;
        this.aJ = Double.MIN_VALUE;

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(int k1 = i; k1 < j; ++k1) {
                for(int l1 = k; l1 < l; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        blockposition$b.f(k1, l1, i2);
                        Fluid fluid = this.world.b(blockposition$b);
                        if (fluid.a(TagsFluid.WATER)) {
                            float fx = (float)l1 + fluid.f();
                            this.aJ = Math.max((double)fx, this.aJ);
                            flag |= axisalignedbb.b < (double)fx;
                        }
                    }
                }
            }
        }

        return flag;
    }

    @Nullable
    private EntityBoat.EnumStatus u() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        double d0 = axisalignedbb.e + 0.001D;
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int k = MathHelper.floor(axisalignedbb.e);
        int l = MathHelper.f(d0);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);
        boolean flag = false;

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(int k1 = i; k1 < j; ++k1) {
                for(int l1 = k; l1 < l; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        blockposition$b.f(k1, l1, i2);
                        Fluid fluid = this.world.b(blockposition$b);
                        if (fluid.a(TagsFluid.WATER) && d0 < (double)((float)blockposition$b.getY() + fluid.f())) {
                            if (!fluid.d()) {
                                EntityBoat.EnumStatus entityboat$enumstatus = EntityBoat.EnumStatus.UNDER_FLOWING_WATER;
                                return entityboat$enumstatus;
                            }

                            flag = true;
                        }
                    }
                }
            }
        }

        return flag ? EntityBoat.EnumStatus.UNDER_WATER : null;
    }

    private void v() {
        double d0 = (double)-0.04F;
        double d1 = this.isNoGravity() ? 0.0D : (double)-0.04F;
        double d2 = 0.0D;
        this.aw = 0.05F;
        if (this.aM == EntityBoat.EnumStatus.IN_AIR && this.aL != EntityBoat.EnumStatus.IN_AIR && this.aL != EntityBoat.EnumStatus.ON_LAND) {
            this.aJ = this.getBoundingBox().b + (double)this.length;
            this.setPosition(this.locX, (double)(this.k() - this.length) + 0.101D, this.locZ);
            this.motY = 0.0D;
            this.aN = 0.0D;
            this.aL = EntityBoat.EnumStatus.IN_WATER;
        } else {
            if (this.aL == EntityBoat.EnumStatus.IN_WATER) {
                d2 = (this.aJ - this.getBoundingBox().b) / (double)this.length;
                this.aw = 0.9F;
            } else if (this.aL == EntityBoat.EnumStatus.UNDER_FLOWING_WATER) {
                d1 = -7.0E-4D;
                this.aw = 0.9F;
            } else if (this.aL == EntityBoat.EnumStatus.UNDER_WATER) {
                d2 = (double)0.01F;
                this.aw = 0.45F;
            } else if (this.aL == EntityBoat.EnumStatus.IN_AIR) {
                this.aw = 0.9F;
            } else if (this.aL == EntityBoat.EnumStatus.ON_LAND) {
                this.aw = this.aK;
                if (this.bO() instanceof EntityHuman) {
                    this.aK /= 2.0F;
                }
            }

            this.motX *= (double)this.aw;
            this.motZ *= (double)this.aw;
            this.ay *= this.aw;
            this.motY += d1;
            if (d2 > 0.0D) {
                double d3 = 0.65D;
                this.motY += d2 * 0.06153846016296973D;
                double d4 = 0.75D;
                this.motY *= 0.75D;
            }
        }

    }

    private void x() {
        if (this.isVehicle()) {
            float fx = 0.0F;
            if (this.aF) {
                this.ay += -1.0F;
            }

            if (this.aG) {
                ++this.ay;
            }

            if (this.aG != this.aF && !this.aH && !this.aI) {
                fx += 0.005F;
            }

            this.yaw += this.ay;
            if (this.aH) {
                fx += 0.04F;
            }

            if (this.aI) {
                fx -= 0.005F;
            }

            this.motX += (double)(MathHelper.sin(-this.yaw * ((float)Math.PI / 180F)) * fx);
            this.motZ += (double)(MathHelper.cos(this.yaw * ((float)Math.PI / 180F)) * fx);
            this.a(this.aG && !this.aF || this.aH, this.aF && !this.aG || this.aH);
        }
    }

    public void k(Entity entity) {
        if (this.w(entity)) {
            float fx = 0.0F;
            float f1 = (float)((this.dead ? (double)0.01F : this.aJ()) + entity.aI());
            if (this.bP().size() > 1) {
                int i = this.bP().indexOf(entity);
                if (i == 0) {
                    fx = 0.2F;
                } else {
                    fx = -0.6F;
                }

                if (entity instanceof EntityAnimal) {
                    fx = (float)((double)fx + 0.2D);
                }
            }

            Vec3D vec3d = (new Vec3D((double)fx, 0.0D, 0.0D)).b(-this.yaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            entity.setPosition(this.locX + vec3d.x, this.locY + (double)f1, this.locZ + vec3d.z);
            entity.yaw += this.ay;
            entity.setHeadRotation(entity.getHeadRotation() + this.ay);
            this.a(entity);
            if (entity instanceof EntityAnimal && this.bP().size() > 1) {
                int j = entity.getId() % 2 == 0 ? 90 : 270;
                entity.k(((EntityAnimal)entity).aQ + (float)j);
                entity.setHeadRotation(entity.getHeadRotation() + (float)j);
            }

        }
    }

    protected void a(Entity entity) {
        entity.k(this.yaw);
        float fx = MathHelper.g(entity.yaw - this.yaw);
        float f1 = MathHelper.a(fx, -105.0F, 105.0F);
        entity.lastYaw += f1 - fx;
        entity.yaw += f1 - fx;
        entity.setHeadRotation(entity.yaw);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setString("Type", this.getType().a());
    }

    protected void a(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.hasKeyOfType("Type", 8)) {
            this.setType(EntityBoat.EnumBoatType.a(nbttagcompound.getString("Type")));
        }

    }

    public boolean b(EntityHuman entityhuman, EnumHand var2) {
        if (entityhuman.isSneaking()) {
            return false;
        } else {
            if (!this.world.isClientSide && this.ax < 60.0F) {
                entityhuman.startRiding(this);
            }

            return true;
        }
    }

    protected void a(double d0, boolean flag, IBlockData var4, BlockPosition var5) {
        this.aN = this.motY;
        if (!this.isPassenger()) {
            if (flag) {
                if (this.fallDistance > 3.0F) {
                    if (this.aL != EntityBoat.EnumStatus.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.c(this.fallDistance, 1.0F);
                    if (!this.world.isClientSide && !this.dead) {
                        this.die();
                        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
                            for(int i = 0; i < 3; ++i) {
                                this.a(this.getType().b());
                            }

                            for(int j = 0; j < 2; ++j) {
                                this.a(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.world.b((new BlockPosition(this)).down()).a(TagsFluid.WATER) && d0 < 0.0D) {
                this.fallDistance = (float)((double)this.fallDistance - d0);
            }

        }
    }

    public boolean a(int i) {
        return this.datawatcher.get(i == 0 ? e : f) && this.bO() != null;
    }

    public void setDamage(float fx) {
        this.datawatcher.set(c, fx);
    }

    public float m() {
        return this.datawatcher.get(c);
    }

    public void b(int i) {
        this.datawatcher.set(a, i);
    }

    public int n() {
        return this.datawatcher.get(a);
    }

    private void d(int i) {
        this.datawatcher.set(g, i);
    }

    private int z() {
        return this.datawatcher.get(g);
    }

    public void c(int i) {
        this.datawatcher.set(b, i);
    }

    public int o() {
        return this.datawatcher.get(b);
    }

    public void setType(EntityBoat.EnumBoatType entityboat$enumboattype) {
        this.datawatcher.set(d, entityboat$enumboattype.ordinal());
    }

    public EntityBoat.EnumBoatType getType() {
        return EntityBoat.EnumBoatType.a(this.datawatcher.get(d));
    }

    protected boolean q(Entity var1) {
        return this.bP().size() < 2 && !this.a(TagsFluid.WATER);
    }

    @Nullable
    public Entity bO() {
        List list = this.bP();
        return list.isEmpty() ? null : (Entity)list.get(0);
    }

    public static enum EnumBoatType {
        OAK(Blocks.OAK_PLANKS, "oak"),
        SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"),
        BIRCH(Blocks.BIRCH_PLANKS, "birch"),
        JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"),
        ACACIA(Blocks.ACACIA_PLANKS, "acacia"),
        DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak");

        private final String g;
        private final Block h;

        private EnumBoatType(Block block, String s1) {
            this.g = s1;
            this.h = block;
        }

        public String a() {
            return this.g;
        }

        public Block b() {
            return this.h;
        }

        public String toString() {
            return this.g;
        }

        public static EntityBoat.EnumBoatType a(int ix) {
            EntityBoat.EnumBoatType[] aentityboat$enumboattype = values();
            if (ix < 0 || ix >= aentityboat$enumboattype.length) {
                ix = 0;
            }

            return aentityboat$enumboattype[ix];
        }

        public static EntityBoat.EnumBoatType a(String s) {
            EntityBoat.EnumBoatType[] aentityboat$enumboattype = values();

            for(int ix = 0; ix < aentityboat$enumboattype.length; ++ix) {
                if (aentityboat$enumboattype[ix].a().equals(s)) {
                    return aentityboat$enumboattype[ix];
                }
            }

            return aentityboat$enumboattype[0];
        }
    }

    public static enum EnumStatus {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR;

        private EnumStatus() {
        }
    }
}
