package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class TileEntityConduit extends TileEntity implements ITickable {
    private static final Block[] e = new Block[]{Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE};
    public int a;
    private float f;
    private boolean g;
    private boolean h;
    private final List<BlockPosition> i;
    private EntityLiving j;
    private UUID k;
    private long l;

    public TileEntityConduit() {
        this(TileEntityTypes.CONDUIT);
    }

    public TileEntityConduit(TileEntityTypes<?> tileentitytypes) {
        super(tileentitytypes);
        this.i = Lists.newArrayList();
    }

    public void load(NBTTagCompound nbttagcompound) {
        super.load(nbttagcompound);
        if (nbttagcompound.hasKey("target_uuid")) {
            this.k = GameProfileSerializer.b(nbttagcompound.getCompound("target_uuid"));
        } else {
            this.k = null;
        }

    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        if (this.j != null) {
            nbttagcompound.set("target_uuid", GameProfileSerializer.a(this.j.getUniqueID()));
        }

        return nbttagcompound;
    }

    @Nullable
    public PacketPlayOutTileEntityData getUpdatePacket() {
        return new PacketPlayOutTileEntityData(this.position, 5, this.aa_());
    }

    public NBTTagCompound aa_() {
        return this.save(new NBTTagCompound());
    }

    public void Y_() {
        ++this.a;
        long ix = this.world.getTime();
        if (ix % 40L == 0L) {
            this.a(this.f());
            if (!this.world.isClientSide && this.c()) {
                this.h();
                this.i();
            }
        }

        if (ix % 80L == 0L && this.c()) {
            this.a(SoundEffects.BLOCK_CONDUIT_AMBIENT);
        }

        if (ix > this.l && this.c()) {
            this.l = ix + 60L + (long)this.world.m().nextInt(40);
            this.a(SoundEffects.BLOCK_CONDUIT_AMBIENT_SHORT);
        }

        if (this.world.isClientSide) {
            this.j();
            this.m();
            if (this.c()) {
                ++this.f;
            }
        }

    }

    private boolean f() {
        this.i.clear();

        for(int ix = -1; ix <= 1; ++ix) {
            for(int jx = -1; jx <= 1; ++jx) {
                for(int kx = -1; kx <= 1; ++kx) {
                    BlockPosition blockposition = this.position.a(ix, jx, kx);
                    if (!this.world.B(blockposition)) {
                        return false;
                    }
                }
            }
        }

        for(int j1 = -2; j1 <= 2; ++j1) {
            for(int k1 = -2; k1 <= 2; ++k1) {
                for(int l1 = -2; l1 <= 2; ++l1) {
                    int i2 = Math.abs(j1);
                    int lx = Math.abs(k1);
                    int i1 = Math.abs(l1);
                    if ((i2 > 1 || lx > 1 || i1 > 1) && (j1 == 0 && (lx == 2 || i1 == 2) || k1 == 0 && (i2 == 2 || i1 == 2) || l1 == 0 && (i2 == 2 || lx == 2))) {
                        BlockPosition blockposition1 = this.position.a(j1, k1, l1);
                        IBlockData iblockdata = this.world.getType(blockposition1);

                        for(Block block : e) {
                            if (iblockdata.getBlock() == block) {
                                this.i.add(blockposition1);
                            }
                        }
                    }
                }
            }
        }

        this.b(this.i.size() >= 42);
        return this.i.size() >= 16;
    }

    private void h() {
        int ix = this.i.size();
        int jx = ix / 7 * 16;
        int kx = this.position.getX();
        int lx = this.position.getY();
        int i1 = this.position.getZ();
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)kx, (double)lx, (double)i1, (double)(kx + 1), (double)(lx + 1), (double)(i1 + 1))).g((double)jx).b(0.0D, (double)this.world.getHeight(), 0.0D);
        List list = this.world.a(EntityHuman.class, axisalignedbb);
        if (!list.isEmpty()) {
            for(EntityHuman entityhuman : list) {
                if (this.position.m(new BlockPosition(entityhuman)) <= (double)jx && entityhuman.ao()) {
                    entityhuman.addEffect(new MobEffect(MobEffects.CONDUIT_POWER, 260, 0, true, true));
                }
            }

        }
    }

    private void i() {
        EntityLiving entityliving = this.j;
        int ix = this.i.size();
        if (ix < 42) {
            this.j = null;
        } else if (this.j == null && this.k != null) {
            this.j = this.l();
            this.k = null;
        } else if (this.j == null) {
            List list = this.world.a(EntityLiving.class, this.k(), (entityliving1) -> {
                return entityliving1 instanceof IMonster && entityliving1.ao();
            });
            if (!list.isEmpty()) {
                this.j = (EntityLiving)list.get(this.world.random.nextInt(list.size()));
            }
        } else if (!this.j.isAlive() || this.position.m(new BlockPosition(this.j)) > 8.0D) {
            this.j = null;
        }

        if (this.j != null) {
            this.world.a((EntityHuman)null, this.j.locX, this.j.locY, this.j.locZ, SoundEffects.BLOCK_CONDUIT_ATTACK_TARGET, SoundCategory.BLOCKS, 1.0F, 1.0F);
            this.j.damageEntity(DamageSource.MAGIC, 4.0F);
        }

        if (entityliving != this.j) {
            IBlockData iblockdata = this.getBlock();
            this.world.notify(this.position, iblockdata, iblockdata, 2);
        }

    }

    private void j() {
        if (this.k == null) {
            this.j = null;
        } else if (this.j == null || !this.j.getUniqueID().equals(this.k)) {
            this.j = this.l();
            if (this.j == null) {
                this.k = null;
            }
        }

    }

    private AxisAlignedBB k() {
        int ix = this.position.getX();
        int jx = this.position.getY();
        int kx = this.position.getZ();
        return (new AxisAlignedBB((double)ix, (double)jx, (double)kx, (double)(ix + 1), (double)(jx + 1), (double)(kx + 1))).g(8.0D);
    }

    @Nullable
    private EntityLiving l() {
        List list = this.world.a(EntityLiving.class, this.k(), (entityliving) -> {
            return entityliving.getUniqueID().equals(this.k);
        });
        return list.size() == 1 ? (EntityLiving)list.get(0) : null;
    }

    private void m() {
        Random random = this.world.random;
        float fx = MathHelper.sin((float)(this.a + 35) * 0.1F) / 2.0F + 0.5F;
        fx = (fx * fx + fx) * 0.3F;
        Vec3D vec3d = new Vec3D((double)((float)this.position.getX() + 0.5F), (double)((float)this.position.getY() + 1.5F + fx), (double)((float)this.position.getZ() + 0.5F));

        for(BlockPosition blockposition : this.i) {
            if (random.nextInt(50) == 0) {
                float f1 = -0.5F + random.nextFloat();
                float f2 = -2.0F + random.nextFloat();
                float f3 = -0.5F + random.nextFloat();
                BlockPosition blockposition1 = blockposition.b(this.position);
                Vec3D vec3d1 = (new Vec3D((double)f1, (double)f2, (double)f3)).add((double)blockposition1.getX(), (double)blockposition1.getY(), (double)blockposition1.getZ());
                this.world.addParticle(Particles.W, vec3d.x, vec3d.y, vec3d.z, vec3d1.x, vec3d1.y, vec3d1.z);
            }
        }

        if (this.j != null) {
            Vec3D vec3d2 = new Vec3D(this.j.locX, this.j.locY + (double)this.j.getHeadHeight(), this.j.locZ);
            float f4 = (-0.5F + random.nextFloat()) * (3.0F + this.j.width);
            float f5 = -1.0F + random.nextFloat() * this.j.length;
            float f6 = (-0.5F + random.nextFloat()) * (3.0F + this.j.width);
            Vec3D vec3d3 = new Vec3D((double)f4, (double)f5, (double)f6);
            this.world.addParticle(Particles.W, vec3d2.x, vec3d2.y, vec3d2.z, vec3d3.x, vec3d3.y, vec3d3.z);
        }

    }

    public boolean c() {
        return this.g;
    }

    private void a(boolean flag) {
        if (flag != this.g) {
            this.a(flag ? SoundEffects.BLOCK_CONDUIT_ACTIVATE : SoundEffects.BLOCK_CONDUIT_DEACTIVATE);
        }

        this.g = flag;
    }

    private void b(boolean flag) {
        this.h = flag;
    }

    public void a(SoundEffect soundeffect) {
        this.world.a((EntityHuman)null, this.position, soundeffect, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}
