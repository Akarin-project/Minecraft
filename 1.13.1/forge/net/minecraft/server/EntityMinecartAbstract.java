package net.minecraft.server;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

public abstract class EntityMinecartAbstract extends Entity implements INamableTileEntity {
    private static final DataWatcherObject<Integer> a = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> b = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Float> c = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.c);
    private static final DataWatcherObject<Integer> d = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Integer> e = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Boolean> f = DataWatcher.a(EntityMinecartAbstract.class, DataWatcherRegistry.i);
    private boolean g;
    private static final int[][][] h = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
    private int aw;
    private double ax;
    private double ay;
    private double az;
    private double aA;
    private double aB;

    protected EntityMinecartAbstract(EntityTypes<?> entitytypes, World world) {
        super(entitytypes, world);
        this.j = true;
        this.setSize(0.98F, 0.7F);
    }

    protected EntityMinecartAbstract(EntityTypes<?> entitytypes, World world, double d0, double d1, double d2) {
        this(entitytypes, world);
        this.setPosition(d0, d1, d2);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = d0;
        this.lastY = d1;
        this.lastZ = d2;
    }

    public static EntityMinecartAbstract a(World world, double d0, double d1, double d2, EntityMinecartAbstract.EnumMinecartType entityminecartabstract$enumminecarttype) {
        switch(entityminecartabstract$enumminecarttype) {
        case CHEST:
            return new EntityMinecartChest(world, d0, d1, d2);
        case FURNACE:
            return new EntityMinecartFurnace(world, d0, d1, d2);
        case TNT:
            return new EntityMinecartTNT(world, d0, d1, d2);
        case SPAWNER:
            return new EntityMinecartMobSpawner(world, d0, d1, d2);
        case HOPPER:
            return new EntityMinecartHopper(world, d0, d1, d2);
        case COMMAND_BLOCK:
            return new EntityMinecartCommandBlock(world, d0, d1, d2);
        default:
            return new EntityMinecartRideable(world, d0, d1, d2);
        }
    }

    protected boolean playStepSound() {
        return false;
    }

    protected void x_() {
        this.datawatcher.register(a, 0);
        this.datawatcher.register(b, 1);
        this.datawatcher.register(c, 0.0F);
        this.datawatcher.register(d, Block.getCombinedId(Blocks.AIR.getBlockData()));
        this.datawatcher.register(e, 6);
        this.datawatcher.register(f, false);
    }

    @Nullable
    public AxisAlignedBB j(Entity entity) {
        return entity.isCollidable() ? entity.getBoundingBox() : null;
    }

    public boolean isCollidable() {
        return true;
    }

    public double aJ() {
        return 0.0D;
    }

    public boolean damageEntity(DamageSource damagesource, float fx) {
        if (!this.world.isClientSide && !this.dead) {
            if (this.isInvulnerable(damagesource)) {
                return false;
            } else {
                this.k(-this.u());
                this.d(10);
                this.aA();
                this.setDamage(this.getDamage() + fx * 10.0F);
                boolean flag = damagesource.getEntity() instanceof EntityHuman && ((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild;
                if (flag || this.getDamage() > 40.0F) {
                    this.ejectPassengers();
                    if (flag && !this.hasCustomName()) {
                        this.die();
                    } else {
                        this.a(damagesource);
                    }
                }

                return true;
            }
        } else {
            return true;
        }
    }

    public void a(DamageSource var1) {
        this.die();
        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
            ItemStack itemstack = new ItemStack(Items.MINECART);
            if (this.hasCustomName()) {
                itemstack.a(this.getCustomName());
            }

            this.a_(itemstack);
        }

    }

    public boolean isInteractable() {
        return !this.dead;
    }

    public EnumDirection getAdjustedDirection() {
        return this.g ? this.getDirection().opposite().e() : this.getDirection().e();
    }

    public void tick() {
        if (this.getType() > 0) {
            this.d(this.getType() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        if (this.locY < -64.0D) {
            this.aa();
        }

        if (!this.world.isClientSide && this.world instanceof WorldServer) {
            this.world.methodProfiler.a("portal");
            MinecraftServer minecraftserver = this.world.getMinecraftServer();
            int i = this.X();
            if (this.an) {
                if (minecraftserver.getAllowNether()) {
                    if (!this.isPassenger() && this.ao++ >= i) {
                        this.ao = i;
                        this.portalCooldown = this.aQ();
                        DimensionManager dimensionmanager;
                        if (this.world.worldProvider.getDimensionManager() == DimensionManager.NETHER) {
                            dimensionmanager = DimensionManager.OVERWORLD;
                        } else {
                            dimensionmanager = DimensionManager.NETHER;
                        }

                        this.a(dimensionmanager);
                    }

                    this.an = false;
                }
            } else {
                if (this.ao > 0) {
                    this.ao -= 4;
                }

                if (this.ao < 0) {
                    this.ao = 0;
                }
            }

            if (this.portalCooldown > 0) {
                --this.portalCooldown;
            }

            this.world.methodProfiler.e();
        }

        if (this.world.isClientSide) {
            if (this.aw > 0) {
                double d4 = this.locX + (this.ax - this.locX) / (double)this.aw;
                double d5 = this.locY + (this.ay - this.locY) / (double)this.aw;
                double d6 = this.locZ + (this.az - this.locZ) / (double)this.aw;
                double d1 = MathHelper.g(this.aA - (double)this.yaw);
                this.yaw = (float)((double)this.yaw + d1 / (double)this.aw);
                this.pitch = (float)((double)this.pitch + (this.aB - (double)this.pitch) / (double)this.aw);
                --this.aw;
                this.setPosition(d4, d5, d6);
                this.setYawPitch(this.yaw, this.pitch);
            } else {
                this.setPosition(this.locX, this.locY, this.locZ);
                this.setYawPitch(this.yaw, this.pitch);
            }

        } else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            if (!this.isNoGravity()) {
                this.motY -= (double)0.04F;
            }

            int j = MathHelper.floor(this.locX);
            int k = MathHelper.floor(this.locY);
            int l = MathHelper.floor(this.locZ);
            if (this.world.getType(new BlockPosition(j, k - 1, l)).a(TagsBlock.RAILS)) {
                --k;
            }

            BlockPosition blockposition = new BlockPosition(j, k, l);
            IBlockData iblockdata = this.world.getType(blockposition);
            if (iblockdata.a(TagsBlock.RAILS)) {
                this.b(blockposition, iblockdata);
                if (iblockdata.getBlock() == Blocks.ACTIVATOR_RAIL) {
                    this.a(j, k, l, iblockdata.get(BlockPoweredRail.POWERED));
                }
            } else {
                this.q();
            }

            this.checkBlockCollisions();
            this.pitch = 0.0F;
            double d0 = this.lastX - this.locX;
            double d2 = this.lastZ - this.locZ;
            if (d0 * d0 + d2 * d2 > 0.001D) {
                this.yaw = (float)(MathHelper.c(d2, d0) * 180.0D / Math.PI);
                if (this.g) {
                    this.yaw += 180.0F;
                }
            }

            double d3 = (double)MathHelper.g(this.yaw - this.lastYaw);
            if (d3 < -170.0D || d3 >= 170.0D) {
                this.yaw += 180.0F;
                this.g = !this.g;
            }

            this.setYawPitch(this.yaw, this.pitch);
            if (this.v() == EntityMinecartAbstract.EnumMinecartType.RIDEABLE && this.motX * this.motX + this.motZ * this.motZ > 0.01D) {
                List list = this.world.getEntities(this, this.getBoundingBox().grow((double)0.2F, 0.0D, (double)0.2F), IEntitySelector.a(this));
                if (!list.isEmpty()) {
                    for(int i1 = 0; i1 < list.size(); ++i1) {
                        Entity entity1 = (Entity)list.get(i1);
                        if (!(entity1 instanceof EntityHuman) && !(entity1 instanceof EntityIronGolem) && !(entity1 instanceof EntityMinecartAbstract) && !this.isVehicle() && !entity1.isPassenger()) {
                            entity1.startRiding(this);
                        } else {
                            entity1.collide(this);
                        }
                    }
                }
            } else {
                for(Entity entity : this.world.getEntities(this, this.getBoundingBox().grow((double)0.2F, 0.0D, (double)0.2F))) {
                    if (!this.w(entity) && entity.isCollidable() && entity instanceof EntityMinecartAbstract) {
                        entity.collide(this);
                    }
                }
            }

            this.at();
        }
    }

    protected double p() {
        return 0.4D;
    }

    public void a(int var1, int var2, int var3, boolean var4) {
    }

    protected void q() {
        double d0 = this.p();
        this.motX = MathHelper.a(this.motX, -d0, d0);
        this.motZ = MathHelper.a(this.motZ, -d0, d0);
        if (this.onGround) {
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
        }

        this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
        if (!this.onGround) {
            this.motX *= (double)0.95F;
            this.motY *= (double)0.95F;
            this.motZ *= (double)0.95F;
        }

    }

    protected void b(BlockPosition blockposition, IBlockData iblockdata) {
        this.fallDistance = 0.0F;
        Vec3D vec3d = this.j(this.locX, this.locY, this.locZ);
        this.locY = (double)blockposition.getY();
        boolean flag = false;
        boolean flag1 = false;
        BlockMinecartTrackAbstract blockminecarttrackabstract = (BlockMinecartTrackAbstract)iblockdata.getBlock();
        if (blockminecarttrackabstract == Blocks.POWERED_RAIL) {
            flag = iblockdata.get(BlockPoweredRail.POWERED);
            flag1 = !flag;
        }

        double d0 = 0.0078125D;
        BlockPropertyTrackPosition blockpropertytrackposition = (BlockPropertyTrackPosition)iblockdata.get(blockminecarttrackabstract.e());
        switch(blockpropertytrackposition) {
        case ASCENDING_EAST:
            this.motX -= 0.0078125D;
            ++this.locY;
            break;
        case ASCENDING_WEST:
            this.motX += 0.0078125D;
            ++this.locY;
            break;
        case ASCENDING_NORTH:
            this.motZ += 0.0078125D;
            ++this.locY;
            break;
        case ASCENDING_SOUTH:
            this.motZ -= 0.0078125D;
            ++this.locY;
        }

        int[][] aint = h[blockpropertytrackposition.a()];
        double d1 = (double)(aint[1][0] - aint[0][0]);
        double d2 = (double)(aint[1][2] - aint[0][2]);
        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        double d4 = this.motX * d1 + this.motZ * d2;
        if (d4 < 0.0D) {
            d1 = -d1;
            d2 = -d2;
        }

        double d5 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        if (d5 > 2.0D) {
            d5 = 2.0D;
        }

        this.motX = d5 * d1 / d3;
        this.motZ = d5 * d2 / d3;
        Entity entity = this.bP().isEmpty() ? null : (Entity)this.bP().get(0);
        if (entity instanceof EntityHuman) {
            double d6 = (double)((EntityHuman)entity).bj;
            if (d6 > 0.0D) {
                double d7 = -Math.sin((double)(entity.yaw * ((float)Math.PI / 180F)));
                double d8 = Math.cos((double)(entity.yaw * ((float)Math.PI / 180F)));
                double d9 = this.motX * this.motX + this.motZ * this.motZ;
                if (d9 < 0.01D) {
                    this.motX += d7 * 0.1D;
                    this.motZ += d8 * 0.1D;
                    flag1 = false;
                }
            }
        }

        if (flag1) {
            double d17 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d17 < 0.03D) {
                this.motX *= 0.0D;
                this.motY *= 0.0D;
                this.motZ *= 0.0D;
            } else {
                this.motX *= 0.5D;
                this.motY *= 0.0D;
                this.motZ *= 0.5D;
            }
        }

        double d18 = (double)blockposition.getX() + 0.5D + (double)aint[0][0] * 0.5D;
        double d19 = (double)blockposition.getZ() + 0.5D + (double)aint[0][2] * 0.5D;
        double d20 = (double)blockposition.getX() + 0.5D + (double)aint[1][0] * 0.5D;
        double d21 = (double)blockposition.getZ() + 0.5D + (double)aint[1][2] * 0.5D;
        d1 = d20 - d18;
        d2 = d21 - d19;
        double d10;
        if (d1 == 0.0D) {
            this.locX = (double)blockposition.getX() + 0.5D;
            d10 = this.locZ - (double)blockposition.getZ();
        } else if (d2 == 0.0D) {
            this.locZ = (double)blockposition.getZ() + 0.5D;
            d10 = this.locX - (double)blockposition.getX();
        } else {
            double d11 = this.locX - d18;
            double d12 = this.locZ - d19;
            d10 = (d11 * d1 + d12 * d2) * 2.0D;
        }

        this.locX = d18 + d1 * d10;
        this.locZ = d19 + d2 * d10;
        this.setPosition(this.locX, this.locY, this.locZ);
        double d22 = this.motX;
        double d23 = this.motZ;
        if (this.isVehicle()) {
            d22 *= 0.75D;
            d23 *= 0.75D;
        }

        double d13 = this.p();
        d22 = MathHelper.a(d22, -d13, d13);
        d23 = MathHelper.a(d23, -d13, d13);
        this.move(EnumMoveType.SELF, d22, 0.0D, d23);
        if (aint[0][1] != 0 && MathHelper.floor(this.locX) - blockposition.getX() == aint[0][0] && MathHelper.floor(this.locZ) - blockposition.getZ() == aint[0][2]) {
            this.setPosition(this.locX, this.locY + (double)aint[0][1], this.locZ);
        } else if (aint[1][1] != 0 && MathHelper.floor(this.locX) - blockposition.getX() == aint[1][0] && MathHelper.floor(this.locZ) - blockposition.getZ() == aint[1][2]) {
            this.setPosition(this.locX, this.locY + (double)aint[1][1], this.locZ);
        }

        this.r();
        Vec3D vec3d1 = this.j(this.locX, this.locY, this.locZ);
        if (vec3d1 != null && vec3d != null) {
            double d14 = (vec3d.y - vec3d1.y) * 0.05D;
            d5 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d5 > 0.0D) {
                this.motX = this.motX / d5 * (d5 + d14);
                this.motZ = this.motZ / d5 * (d5 + d14);
            }

            this.setPosition(this.locX, vec3d1.y, this.locZ);
        }

        int j = MathHelper.floor(this.locX);
        int i = MathHelper.floor(this.locZ);
        if (j != blockposition.getX() || i != blockposition.getZ()) {
            d5 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.motX = d5 * (double)(j - blockposition.getX());
            this.motZ = d5 * (double)(i - blockposition.getZ());
        }

        if (flag) {
            double d15 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            if (d15 > 0.01D) {
                double d16 = 0.06D;
                this.motX += this.motX / d15 * 0.06D;
                this.motZ += this.motZ / d15 * 0.06D;
            } else if (blockpropertytrackposition == BlockPropertyTrackPosition.EAST_WEST) {
                if (this.world.getType(blockposition.west()).isOccluding()) {
                    this.motX = 0.02D;
                } else if (this.world.getType(blockposition.east()).isOccluding()) {
                    this.motX = -0.02D;
                }
            } else if (blockpropertytrackposition == BlockPropertyTrackPosition.NORTH_SOUTH) {
                if (this.world.getType(blockposition.north()).isOccluding()) {
                    this.motZ = 0.02D;
                } else if (this.world.getType(blockposition.south()).isOccluding()) {
                    this.motZ = -0.02D;
                }
            }
        }

    }

    protected void r() {
        if (this.isVehicle()) {
            this.motX *= (double)0.997F;
            this.motY *= 0.0D;
            this.motZ *= (double)0.997F;
        } else {
            this.motX *= (double)0.96F;
            this.motY *= 0.0D;
            this.motZ *= (double)0.96F;
        }

    }

    public void setPosition(double d0, double d1, double d2) {
        this.locX = d0;
        this.locY = d1;
        this.locZ = d2;
        float fx = this.width / 2.0F;
        float f1 = this.length;
        this.a(new AxisAlignedBB(d0 - (double)fx, d1, d2 - (double)fx, d0 + (double)fx, d1 + (double)f1, d2 + (double)fx));
    }

    @Nullable
    public Vec3D j(double d0, double d1, double d2) {
        int i = MathHelper.floor(d0);
        int j = MathHelper.floor(d1);
        int k = MathHelper.floor(d2);
        if (this.world.getType(new BlockPosition(i, j - 1, k)).a(TagsBlock.RAILS)) {
            --j;
        }

        IBlockData iblockdata = this.world.getType(new BlockPosition(i, j, k));
        if (iblockdata.a(TagsBlock.RAILS)) {
            BlockPropertyTrackPosition blockpropertytrackposition = (BlockPropertyTrackPosition)iblockdata.get(((BlockMinecartTrackAbstract)iblockdata.getBlock()).e());
            int[][] aint = h[blockpropertytrackposition.a()];
            double d3 = (double)i + 0.5D + (double)aint[0][0] * 0.5D;
            double d4 = (double)j + 0.0625D + (double)aint[0][1] * 0.5D;
            double d5 = (double)k + 0.5D + (double)aint[0][2] * 0.5D;
            double d6 = (double)i + 0.5D + (double)aint[1][0] * 0.5D;
            double d7 = (double)j + 0.0625D + (double)aint[1][1] * 0.5D;
            double d8 = (double)k + 0.5D + (double)aint[1][2] * 0.5D;
            double d9 = d6 - d3;
            double d10 = (d7 - d4) * 2.0D;
            double d11 = d8 - d5;
            double d12;
            if (d9 == 0.0D) {
                d12 = d2 - (double)k;
            } else if (d11 == 0.0D) {
                d12 = d0 - (double)i;
            } else {
                double d13 = d0 - d3;
                double d14 = d2 - d5;
                d12 = (d13 * d9 + d14 * d11) * 2.0D;
            }

            d0 = d3 + d9 * d12;
            d1 = d4 + d10 * d12;
            d2 = d5 + d11 * d12;
            if (d10 < 0.0D) {
                ++d1;
            }

            if (d10 > 0.0D) {
                d1 += 0.5D;
            }

            return new Vec3D(d0, d1, d2);
        } else {
            return null;
        }
    }

    protected void a(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.getBoolean("CustomDisplayTile")) {
            this.setDisplayBlock(GameProfileSerializer.d(nbttagcompound.getCompound("DisplayState")));
            this.setDisplayBlockOffset(nbttagcompound.getInt("DisplayOffset"));
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        if (this.C()) {
            nbttagcompound.setBoolean("CustomDisplayTile", true);
            nbttagcompound.set("DisplayState", GameProfileSerializer.a(this.getDisplayBlock()));
            nbttagcompound.setInt("DisplayOffset", this.getDisplayBlockOffset());
        }

    }

    public void collide(Entity entity) {
        if (!this.world.isClientSide) {
            if (!entity.noclip && !this.noclip) {
                if (!this.w(entity)) {
                    double d0 = entity.locX - this.locX;
                    double d1 = entity.locZ - this.locZ;
                    double d2 = d0 * d0 + d1 * d1;
                    if (d2 >= (double)1.0E-4F) {
                        d2 = (double)MathHelper.sqrt(d2);
                        d0 = d0 / d2;
                        d1 = d1 / d2;
                        double d3 = 1.0D / d2;
                        if (d3 > 1.0D) {
                            d3 = 1.0D;
                        }

                        d0 = d0 * d3;
                        d1 = d1 * d3;
                        d0 = d0 * (double)0.1F;
                        d1 = d1 * (double)0.1F;
                        d0 = d0 * (double)(1.0F - this.S);
                        d1 = d1 * (double)(1.0F - this.S);
                        d0 = d0 * 0.5D;
                        d1 = d1 * 0.5D;
                        if (entity instanceof EntityMinecartAbstract) {
                            double d4 = entity.locX - this.locX;
                            double d5 = entity.locZ - this.locZ;
                            Vec3D vec3d = (new Vec3D(d4, 0.0D, d5)).a();
                            Vec3D vec3d1 = (new Vec3D((double)MathHelper.cos(this.yaw * ((float)Math.PI / 180F)), 0.0D, (double)MathHelper.sin(this.yaw * ((float)Math.PI / 180F)))).a();
                            double d6 = Math.abs(vec3d.b(vec3d1));
                            if (d6 < (double)0.8F) {
                                return;
                            }

                            double d7 = entity.motX + this.motX;
                            double d8 = entity.motZ + this.motZ;
                            if (((EntityMinecartAbstract)entity).v() == EntityMinecartAbstract.EnumMinecartType.FURNACE && this.v() != EntityMinecartAbstract.EnumMinecartType.FURNACE) {
                                this.motX *= (double)0.2F;
                                this.motZ *= (double)0.2F;
                                this.f(entity.motX - d0, 0.0D, entity.motZ - d1);
                                entity.motX *= (double)0.95F;
                                entity.motZ *= (double)0.95F;
                            } else if (((EntityMinecartAbstract)entity).v() != EntityMinecartAbstract.EnumMinecartType.FURNACE && this.v() == EntityMinecartAbstract.EnumMinecartType.FURNACE) {
                                entity.motX *= (double)0.2F;
                                entity.motZ *= (double)0.2F;
                                entity.f(this.motX + d0, 0.0D, this.motZ + d1);
                                this.motX *= (double)0.95F;
                                this.motZ *= (double)0.95F;
                            } else {
                                d7 = d7 / 2.0D;
                                d8 = d8 / 2.0D;
                                this.motX *= (double)0.2F;
                                this.motZ *= (double)0.2F;
                                this.f(d7 - d0, 0.0D, d8 - d1);
                                entity.motX *= (double)0.2F;
                                entity.motZ *= (double)0.2F;
                                entity.f(d7 + d0, 0.0D, d8 + d1);
                            }
                        } else {
                            this.f(-d0, 0.0D, -d1);
                            entity.f(d0 / 4.0D, 0.0D, d1 / 4.0D);
                        }
                    }

                }
            }
        }
    }

    public void setDamage(float fx) {
        this.datawatcher.set(c, fx);
    }

    public float getDamage() {
        return this.datawatcher.get(c);
    }

    public void d(int i) {
        this.datawatcher.set(a, i);
    }

    public int getType() {
        return this.datawatcher.get(a);
    }

    public void k(int i) {
        this.datawatcher.set(b, i);
    }

    public int u() {
        return this.datawatcher.get(b);
    }

    public abstract EntityMinecartAbstract.EnumMinecartType v();

    public IBlockData getDisplayBlock() {
        return !this.C() ? this.z() : Block.getByCombinedId(this.getDataWatcher().get(d));
    }

    public IBlockData z() {
        return Blocks.AIR.getBlockData();
    }

    public int getDisplayBlockOffset() {
        return !this.C() ? this.B() : this.getDataWatcher().get(e);
    }

    public int B() {
        return 6;
    }

    public void setDisplayBlock(IBlockData iblockdata) {
        this.getDataWatcher().set(d, Block.getCombinedId(iblockdata));
        this.a(true);
    }

    public void setDisplayBlockOffset(int i) {
        this.getDataWatcher().set(e, i);
        this.a(true);
    }

    public boolean C() {
        return this.getDataWatcher().get(f);
    }

    public void a(boolean flag) {
        this.getDataWatcher().set(f, flag);
    }

    public static enum EnumMinecartType {
        RIDEABLE(0),
        CHEST(1),
        FURNACE(2),
        TNT(3),
        SPAWNER(4),
        HOPPER(5),
        COMMAND_BLOCK(6);

        private static final EntityMinecartAbstract.EnumMinecartType[] h = (EntityMinecartAbstract.EnumMinecartType[])Arrays.stream(values()).sorted(Comparator.comparingInt(EntityMinecartAbstract.EnumMinecartType::a)).toArray((ix) -> {
            return new EntityMinecartAbstract.EnumMinecartType[ix];
        });
        private final int i;

        private EnumMinecartType(int jx) {
            this.i = jx;
        }

        public int a() {
            return this.i;
        }
    }
}
