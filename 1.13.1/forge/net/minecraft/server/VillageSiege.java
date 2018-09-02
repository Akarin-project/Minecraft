package net.minecraft.server;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class VillageSiege {
    private final World a;
    private boolean b;
    private int c = -1;
    private int d;
    private int e;
    private Village f;
    private int g;
    private int h;
    private int i;

    public VillageSiege(World world) {
        this.a = world;
    }

    public void a() {
        if (this.a.L()) {
            this.c = 0;
        } else if (this.c != 2) {
            if (this.c == 0) {
                float fx = this.a.k(0.0F);
                if ((double)fx < 0.5D || (double)fx > 0.501D) {
                    return;
                }

                this.c = this.a.random.nextInt(10) == 0 ? 1 : 2;
                this.b = false;
                if (this.c == 2) {
                    return;
                }
            }

            if (this.c != -1) {
                if (!this.b) {
                    if (!this.b()) {
                        return;
                    }

                    this.b = true;
                }

                if (this.e > 0) {
                    --this.e;
                } else {
                    this.e = 2;
                    if (this.d > 0) {
                        this.c();
                        --this.d;
                    } else {
                        this.c = 2;
                    }

                }
            }
        }
    }

    private boolean b() {
        List list = this.a.players;
        Iterator iterator = list.iterator();

        while(true) {
            if (!iterator.hasNext()) {
                return false;
            }

            EntityHuman entityhuman = (EntityHuman)iterator.next();
            if (!entityhuman.isSpectator()) {
                this.f = this.a.af().getClosestVillage(new BlockPosition(entityhuman), 1);
                if (this.f != null && this.f.c() >= 10 && this.f.d() >= 20 && this.f.e() >= 20) {
                    BlockPosition blockposition = this.f.a();
                    float fx = (float)this.f.b();
                    boolean flag = false;

                    for(int ix = 0; ix < 10; ++ix) {
                        float f1 = this.a.random.nextFloat() * ((float)Math.PI * 2F);
                        this.g = blockposition.getX() + (int)((double)(MathHelper.cos(f1) * fx) * 0.9D);
                        this.h = blockposition.getY();
                        this.i = blockposition.getZ() + (int)((double)(MathHelper.sin(f1) * fx) * 0.9D);
                        flag = false;

                        for(Village village : this.a.af().getVillages()) {
                            if (village != this.f && village.a(new BlockPosition(this.g, this.h, this.i))) {
                                flag = true;
                                break;
                            }
                        }

                        if (!flag) {
                            break;
                        }
                    }

                    if (flag) {
                        return false;
                    }

                    Vec3D vec3d = this.a(new BlockPosition(this.g, this.h, this.i));
                    if (vec3d != null) {
                        break;
                    }
                }
            }
        }

        this.e = 0;
        this.d = 20;
        return true;
    }

    private boolean c() {
        Vec3D vec3d = this.a(new BlockPosition(this.g, this.h, this.i));
        if (vec3d == null) {
            return false;
        } else {
            EntityZombie entityzombie;
            try {
                entityzombie = new EntityZombie(this.a);
                entityzombie.prepare(this.a.getDamageScaler(new BlockPosition(entityzombie)), (GroupDataEntity)null, (NBTTagCompound)null);
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }

            entityzombie.setPositionRotation(vec3d.x, vec3d.y, vec3d.z, this.a.random.nextFloat() * 360.0F, 0.0F);
            this.a.addEntity(entityzombie);
            BlockPosition blockposition = this.f.a();
            entityzombie.a(blockposition, this.f.b());
            return true;
        }
    }

    @Nullable
    private Vec3D a(BlockPosition blockposition) {
        for(int ix = 0; ix < 10; ++ix) {
            BlockPosition blockposition1 = blockposition.a(this.a.random.nextInt(16) - 8, this.a.random.nextInt(6) - 3, this.a.random.nextInt(16) - 8);
            if (this.f.a(blockposition1) && SpawnerCreature.a(EntityPositionTypes.Surface.ON_GROUND, this.a, blockposition1, (EntityTypes)null)) {
                return new Vec3D((double)blockposition1.getX(), (double)blockposition1.getY(), (double)blockposition1.getZ());
            }
        }

        return null;
    }
}
