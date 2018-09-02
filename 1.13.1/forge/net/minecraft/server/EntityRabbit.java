package net.minecraft.server;

import javax.annotation.Nullable;

public class EntityRabbit extends EntityAnimal {
    private static final DataWatcherObject<Integer> bC = DataWatcher.a(EntityRabbit.class, DataWatcherRegistry.b);
    private static final MinecraftKey bD = new MinecraftKey("killer_bunny");
    private int bE;
    private int bG;
    private boolean bH;
    private int bI;
    private int bJ;

    public EntityRabbit(World world) {
        super(EntityTypes.RABBIT, world);
        this.setSize(0.4F, 0.5F);
        this.h = new EntityRabbit.ControllerJumpRabbit(this);
        this.moveController = new EntityRabbit.ControllerMoveRabbit(this);
        this.c(0.0D);
    }

    protected void n() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new EntityRabbit.PathfinderGoalRabbitPanic(this, 2.2D));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 0.8D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.0D, RecipeItemStack.a(Items.CARROT, Items.GOLDEN_CARROT, Blocks.DANDELION), false));
        this.goalSelector.a(4, new EntityRabbit.PathfinderGoalRabbitAvoidTarget(this, EntityHuman.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.a(4, new EntityRabbit.PathfinderGoalRabbitAvoidTarget(this, EntityWolf.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.a(4, new EntityRabbit.PathfinderGoalRabbitAvoidTarget(this, EntityMonster.class, 4.0F, 2.2D, 2.2D));
        this.goalSelector.a(5, new EntityRabbit.PathfinderGoalEatCarrots(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStrollLand(this, 0.6D));
        this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
    }

    protected float cG() {
        if (!this.positionChanged && (!this.moveController.b() || !(this.moveController.e() > this.locY + 0.5D))) {
            PathEntity pathentity = this.navigation.m();
            if (pathentity != null && pathentity.e() < pathentity.d()) {
                Vec3D vec3d = pathentity.a(this);
                if (vec3d.y > this.locY + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveController.c() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    protected void cH() {
        super.cH();
        double d0 = this.moveController.c();
        if (d0 > 0.0D) {
            double d1 = this.motX * this.motX + this.motZ * this.motZ;
            if (d1 < 0.010000000000000002D) {
                this.a(0.0F, 0.0F, 1.0F, 0.1F);
            }
        }

        if (!this.world.isClientSide) {
            this.world.broadcastEntityEffect(this, (byte)1);
        }

    }

    public void c(double d0) {
        this.getNavigation().a(d0);
        this.moveController.a(this.moveController.d(), this.moveController.e(), this.moveController.f(), d0);
    }

    public void o(boolean flag) {
        super.o(flag);
        if (flag) {
            this.a(this.dz(), this.cD(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    public void dy() {
        this.o(true);
        this.bG = 10;
        this.bE = 0;
    }

    protected void x_() {
        super.x_();
        this.datawatcher.register(bC, 0);
    }

    public void mobTick() {
        if (this.bI > 0) {
            --this.bI;
        }

        if (this.bJ > 0) {
            this.bJ -= this.random.nextInt(3);
            if (this.bJ < 0) {
                this.bJ = 0;
            }
        }

        if (this.onGround) {
            if (!this.bH) {
                this.o(false);
                this.dI();
            }

            if (this.getRabbitType() == 99 && this.bI == 0) {
                EntityLiving entityliving = this.getGoalTarget();
                if (entityliving != null && this.h(entityliving) < 16.0D) {
                    this.b(entityliving.locX, entityliving.locZ);
                    this.moveController.a(entityliving.locX, entityliving.locY, entityliving.locZ, this.moveController.c());
                    this.dy();
                    this.bH = true;
                }
            }

            EntityRabbit.ControllerJumpRabbit entityrabbit$controllerjumprabbit = (EntityRabbit.ControllerJumpRabbit)this.h;
            if (!entityrabbit$controllerjumprabbit.c()) {
                if (this.moveController.b() && this.bI == 0) {
                    PathEntity pathentity = this.navigation.m();
                    Vec3D vec3d = new Vec3D(this.moveController.d(), this.moveController.e(), this.moveController.f());
                    if (pathentity != null && pathentity.e() < pathentity.d()) {
                        vec3d = pathentity.a(this);
                    }

                    this.b(vec3d.x, vec3d.z);
                    this.dy();
                }
            } else if (!entityrabbit$controllerjumprabbit.d()) {
                this.dB();
            }
        }

        this.bH = this.onGround;
    }

    public void av() {
    }

    private void b(double d0, double d1) {
        this.yaw = (float)(MathHelper.c(d1 - this.locZ, d0 - this.locX) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    private void dB() {
        ((EntityRabbit.ControllerJumpRabbit)this.h).a(true);
    }

    private void dC() {
        ((EntityRabbit.ControllerJumpRabbit)this.h).a(false);
    }

    private void dH() {
        if (this.moveController.c() < 2.2D) {
            this.bI = 10;
        } else {
            this.bI = 1;
        }

    }

    private void dI() {
        this.dH();
        this.dC();
    }

    public void k() {
        super.k();
        if (this.bE != this.bG) {
            ++this.bE;
        } else if (this.bG != 0) {
            this.bE = 0;
            this.bG = 0;
            this.o(false);
        }

    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(3.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)0.3F);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("RabbitType", this.getRabbitType());
        nbttagcompound.setInt("MoreCarrotTicks", this.bJ);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setRabbitType(nbttagcompound.getInt("RabbitType"));
        this.bJ = nbttagcompound.getInt("MoreCarrotTicks");
    }

    protected SoundEffect dz() {
        return SoundEffects.ENTITY_RABBIT_JUMP;
    }

    protected SoundEffect D() {
        return SoundEffects.ENTITY_RABBIT_AMBIENT;
    }

    protected SoundEffect d(DamageSource var1) {
        return SoundEffects.ENTITY_RABBIT_HURT;
    }

    protected SoundEffect cs() {
        return SoundEffects.ENTITY_RABBIT_DEATH;
    }

    public boolean B(Entity entity) {
        if (this.getRabbitType() == 99) {
            this.a(SoundEffects.ENTITY_RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            return entity.damageEntity(DamageSource.mobAttack(this), 8.0F);
        } else {
            return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
        }
    }

    public SoundCategory bV() {
        return this.getRabbitType() == 99 ? SoundCategory.HOSTILE : SoundCategory.NEUTRAL;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        return this.isInvulnerable(damagesource) ? false : super.damageEntity(damagesource, f);
    }

    @Nullable
    protected MinecraftKey getDefaultLootTable() {
        return LootTables.I;
    }

    private boolean a(Item item) {
        return item == Items.CARROT || item == Items.GOLDEN_CARROT || item == Blocks.DANDELION.getItem();
    }

    public EntityRabbit b(EntityAgeable entityageable) {
        EntityRabbit entityrabbit1 = new EntityRabbit(this.world);
        int i = this.dJ();
        if (this.random.nextInt(20) != 0) {
            if (entityageable instanceof EntityRabbit && this.random.nextBoolean()) {
                i = ((EntityRabbit)entityageable).getRabbitType();
            } else {
                i = this.getRabbitType();
            }
        }

        entityrabbit1.setRabbitType(i);
        return entityrabbit1;
    }

    public boolean f(ItemStack itemstack) {
        return this.a(itemstack.getItem());
    }

    public int getRabbitType() {
        return this.datawatcher.get(bC);
    }

    public void setRabbitType(int i) {
        if (i == 99) {
            this.getAttributeInstance(GenericAttributes.h).setValue(8.0D);
            this.goalSelector.a(4, new EntityRabbit.PathfinderGoalKillerRabbitMeleeAttack(this));
            this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
            this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
            this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityWolf.class, true));
            if (!this.hasCustomName()) {
                this.setCustomName(new ChatMessage(SystemUtils.a("entity", bD), new Object[0]));
            }
        }

        this.datawatcher.set(bC, i);
    }

    @Nullable
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        groupdataentity = super.prepare(difficultydamagescaler, groupdataentity, nbttagcompound);
        int i = this.dJ();
        boolean flag = false;
        if (groupdataentity instanceof EntityRabbit.GroupDataRabbit) {
            i = ((EntityRabbit.GroupDataRabbit)groupdataentity).a;
            flag = true;
        } else {
            groupdataentity = new EntityRabbit.GroupDataRabbit(i);
        }

        this.setRabbitType(i);
        if (flag) {
            this.setAgeRaw(-24000);
        }

        return groupdataentity;
    }

    private int dJ() {
        BiomeBase biomebase = this.world.getBiome(new BlockPosition(this));
        int i = this.random.nextInt(100);
        if (biomebase.c() == BiomeBase.Precipitation.SNOW) {
            return i < 80 ? 1 : 3;
        } else if (biomebase.p() == BiomeBase.Geography.DESERT) {
            return 4;
        } else {
            return i < 50 ? 0 : (i < 90 ? 5 : 2);
        }
    }

    public boolean a(GeneratorAccess generatoraccess, boolean flag) {
        int i = MathHelper.floor(this.locX);
        int j = MathHelper.floor(this.getBoundingBox().b);
        int k = MathHelper.floor(this.locZ);
        BlockPosition blockposition = new BlockPosition(i, j, k);
        Block block = generatoraccess.getType(blockposition.down()).getBlock();
        return block != Blocks.GRASS && block != Blocks.SNOW && block != Blocks.SAND ? super.a(generatoraccess, flag) : true;
    }

    private boolean dK() {
        return this.bJ == 0;
    }

    // $FF: synthetic method
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    public class ControllerJumpRabbit extends ControllerJump {
        private final EntityRabbit c;
        private boolean d;

        public ControllerJumpRabbit(EntityRabbit entityrabbit1) {
            super(entityrabbit1);
            this.c = entityrabbit1;
        }

        public boolean c() {
            return this.a;
        }

        public boolean d() {
            return this.d;
        }

        public void a(boolean flag) {
            this.d = flag;
        }

        public void b() {
            if (this.a) {
                this.c.dy();
                this.a = false;
            }

        }
    }

    static class ControllerMoveRabbit extends ControllerMove {
        private final EntityRabbit i;
        private double j;

        public ControllerMoveRabbit(EntityRabbit entityrabbit) {
            super(entityrabbit);
            this.i = entityrabbit;
        }

        public void a() {
            if (this.i.onGround && !this.i.bg && !((EntityRabbit.ControllerJumpRabbit)this.i.h).c()) {
                this.i.c(0.0D);
            } else if (this.b()) {
                this.i.c(this.j);
            }

            super.a();
        }

        public void a(double d0, double d1, double d2, double d3) {
            if (this.i.isInWater()) {
                d3 = 1.5D;
            }

            super.a(d0, d1, d2, d3);
            if (d3 > 0.0D) {
                this.j = d3;
            }

        }
    }

    public static class GroupDataRabbit implements GroupDataEntity {
        public int a;

        public GroupDataRabbit(int i) {
            this.a = i;
        }
    }

    static class PathfinderGoalEatCarrots extends PathfinderGoalGotoTarget {
        private final EntityRabbit f;
        private boolean g;
        private boolean h;

        public PathfinderGoalEatCarrots(EntityRabbit entityrabbit) {
            super(entityrabbit, (double)0.7F, 16);
            this.f = entityrabbit;
        }

        public boolean a() {
            if (this.b <= 0) {
                if (!this.f.world.getGameRules().getBoolean("mobGriefing")) {
                    return false;
                }

                this.h = false;
                this.g = this.f.dK();
                this.g = true;
            }

            return super.a();
        }

        public boolean b() {
            return this.h && super.b();
        }

        public void e() {
            super.e();
            this.f.getControllerLook().a((double)this.d.getX() + 0.5D, (double)(this.d.getY() + 1), (double)this.d.getZ() + 0.5D, 10.0F, (float)this.f.K());
            if (this.k()) {
                World world = this.f.world;
                BlockPosition blockposition = this.d.up();
                IBlockData iblockdata = world.getType(blockposition);
                Block block = iblockdata.getBlock();
                if (this.h && block instanceof BlockCarrots) {
                    Integer integer = (Integer)iblockdata.get(BlockCarrots.AGE);
                    if (integer == 0) {
                        world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
                        world.setAir(blockposition, true);
                    } else {
                        world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(BlockCarrots.AGE, Integer.valueOf(integer - 1)), 2);
                        world.triggerEffect(2001, blockposition, Block.getCombinedId(iblockdata));
                    }

                    this.f.bJ = 40;
                }

                this.h = false;
                this.b = 10;
            }

        }

        protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
            Block block = iworldreader.getType(blockposition).getBlock();
            if (block == Blocks.FARMLAND && this.g && !this.h) {
                blockposition = blockposition.up();
                IBlockData iblockdata = iworldreader.getType(blockposition);
                block = iblockdata.getBlock();
                if (block instanceof BlockCarrots && ((BlockCarrots)block).w(iblockdata)) {
                    this.h = true;
                    return true;
                }
            }

            return false;
        }
    }

    static class PathfinderGoalKillerRabbitMeleeAttack extends PathfinderGoalMeleeAttack {
        public PathfinderGoalKillerRabbitMeleeAttack(EntityRabbit entityrabbit) {
            super(entityrabbit, 1.4D, true);
        }

        protected double a(EntityLiving entityliving) {
            return (double)(4.0F + entityliving.width);
        }
    }

    static class PathfinderGoalRabbitAvoidTarget<T extends Entity> extends PathfinderGoalAvoidTarget<T> {
        private final EntityRabbit c;

        public PathfinderGoalRabbitAvoidTarget(EntityRabbit entityrabbit, Class<T> oclass, float f, double d0, double d1) {
            super(entityrabbit, oclass, f, d0, d1);
            this.c = entityrabbit;
        }

        public boolean a() {
            return this.c.getRabbitType() != 99 && super.a();
        }
    }

    static class PathfinderGoalRabbitPanic extends PathfinderGoalPanic {
        private final EntityRabbit f;

        public PathfinderGoalRabbitPanic(EntityRabbit entityrabbit, double d0) {
            super(entityrabbit, d0);
            this.f = entityrabbit;
        }

        public void e() {
            super.e();
            this.f.c(this.b);
        }
    }
}
