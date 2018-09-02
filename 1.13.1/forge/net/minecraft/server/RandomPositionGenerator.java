package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class RandomPositionGenerator {
    @Nullable
    public static Vec3D a(EntityCreature entitycreature, int i, int j) {
        return c(entitycreature, i, j, (Vec3D)null);
    }

    @Nullable
    public static Vec3D b(EntityCreature entitycreature, int i, int j) {
        return a(entitycreature, i, j, (Vec3D)null, false, 0.0D);
    }

    @Nullable
    public static Vec3D a(EntityCreature entitycreature, int i, int j, Vec3D vec3d) {
        Vec3D vec3d1 = vec3d.a(entitycreature.locX, entitycreature.locY, entitycreature.locZ);
        return c(entitycreature, i, j, vec3d1);
    }

    @Nullable
    public static Vec3D a(EntityCreature entitycreature, int i, int j, Vec3D vec3d, double d0) {
        Vec3D vec3d1 = vec3d.a(entitycreature.locX, entitycreature.locY, entitycreature.locZ);
        return a(entitycreature, i, j, vec3d1, true, d0);
    }

    @Nullable
    public static Vec3D b(EntityCreature entitycreature, int i, int j, Vec3D vec3d) {
        Vec3D vec3d1 = (new Vec3D(entitycreature.locX, entitycreature.locY, entitycreature.locZ)).d(vec3d);
        return c(entitycreature, i, j, vec3d1);
    }

    @Nullable
    private static Vec3D c(EntityCreature entitycreature, int i, int j, @Nullable Vec3D vec3d) {
        return a(entitycreature, i, j, vec3d, true, (double)((float)Math.PI / 2F));
    }

    @Nullable
    private static Vec3D a(EntityCreature entitycreature, int i, int j, @Nullable Vec3D vec3d, boolean flag, double d0) {
        NavigationAbstract navigationabstract = entitycreature.getNavigation();
        Random random = entitycreature.getRandom();
        boolean flag1;
        if (entitycreature.dw()) {
            double d1 = entitycreature.dt().distanceSquared((double)MathHelper.floor(entitycreature.locX), (double)MathHelper.floor(entitycreature.locY), (double)MathHelper.floor(entitycreature.locZ)) + 4.0D;
            double d2 = (double)(entitycreature.du() + (float)i);
            flag1 = d1 < d2 * d2;
        } else {
            flag1 = false;
        }

        boolean flag2 = false;
        float f = -99999.0F;
        int i2 = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < 10; ++i1) {
            BlockPosition blockposition = a(random, i, j, vec3d, d0);
            if (blockposition != null) {
                int j1 = blockposition.getX();
                int k1 = blockposition.getY();
                int l1 = blockposition.getZ();
                if (entitycreature.dw() && i > 1) {
                    BlockPosition blockposition1 = entitycreature.dt();
                    if (entitycreature.locX > (double)blockposition1.getX()) {
                        j1 -= random.nextInt(i / 2);
                    } else {
                        j1 += random.nextInt(i / 2);
                    }

                    if (entitycreature.locZ > (double)blockposition1.getZ()) {
                        l1 -= random.nextInt(i / 2);
                    } else {
                        l1 += random.nextInt(i / 2);
                    }
                }

                BlockPosition blockposition2 = new BlockPosition((double)j1 + entitycreature.locX, (double)k1 + entitycreature.locY, (double)l1 + entitycreature.locZ);
                if ((!flag1 || entitycreature.f(blockposition2)) && navigationabstract.a(blockposition2)) {
                    if (!flag) {
                        blockposition2 = a(blockposition2, entitycreature);
                        if (b(blockposition2, entitycreature)) {
                            continue;
                        }
                    }

                    float f1 = entitycreature.a(blockposition2);
                    if (f1 > f) {
                        f = f1;
                        i2 = j1;
                        k = k1;
                        l = l1;
                        flag2 = true;
                    }
                }
            }
        }

        if (flag2) {
            return new Vec3D((double)i2 + entitycreature.locX, (double)k + entitycreature.locY, (double)l + entitycreature.locZ);
        } else {
            return null;
        }
    }

    @Nullable
    private static BlockPosition a(Random random, int i, int j, @Nullable Vec3D vec3d, double d0) {
        if (vec3d != null && !(d0 >= Math.PI)) {
            double d4 = MathHelper.c(vec3d.z, vec3d.x) - (double)((float)Math.PI / 2F);
            double d5 = d4 + (double)(2.0F * random.nextFloat() - 1.0F) * d0;
            double d1 = Math.sqrt(random.nextDouble()) * (double)MathHelper.a * (double)i;
            double d2 = -d1 * Math.sin(d5);
            double d3 = d1 * Math.cos(d5);
            if (!(Math.abs(d2) > (double)i) && !(Math.abs(d3) > (double)i)) {
                int j1 = random.nextInt(2 * j + 1) - j;
                return new BlockPosition(d2, (double)j1, d3);
            } else {
                return null;
            }
        } else {
            int k = random.nextInt(2 * i + 1) - i;
            int l = random.nextInt(2 * j + 1) - j;
            int i1 = random.nextInt(2 * i + 1) - i;
            return new BlockPosition(k, l, i1);
        }
    }

    private static BlockPosition a(BlockPosition blockposition, EntityCreature entitycreature) {
        if (!entitycreature.world.getType(blockposition).getMaterial().isBuildable()) {
            return blockposition;
        } else {
            BlockPosition blockposition1;
            for(blockposition1 = blockposition.up(); blockposition1.getY() < entitycreature.world.getHeight() && entitycreature.world.getType(blockposition1).getMaterial().isBuildable(); blockposition1 = blockposition1.up()) {
                ;
            }

            return blockposition1;
        }
    }

    private static boolean b(BlockPosition blockposition, EntityCreature entitycreature) {
        return entitycreature.world.b(blockposition).a(TagsFluid.WATER);
    }
}
