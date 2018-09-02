package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class NavigationListener implements IWorldAccess {
    private final List<NavigationAbstract> a = Lists.newArrayList();

    public NavigationListener() {
    }

    public void a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1, int var5) {
        if (this.a(iblockaccess, blockposition, iblockdata, iblockdata1)) {
            int i = 0;

            for(int j = this.a.size(); i < j; ++i) {
                NavigationAbstract navigationabstract = (NavigationAbstract)this.a.get(i);
                if (navigationabstract != null && !navigationabstract.k()) {
                    PathEntity pathentity = navigationabstract.m();
                    if (pathentity != null && !pathentity.b() && pathentity.d() != 0) {
                        PathPoint pathpoint = navigationabstract.c.c();
                        double d0 = blockposition.distanceSquared(((double)pathpoint.a + navigationabstract.a.locX) / 2.0D, ((double)pathpoint.b + navigationabstract.a.locY) / 2.0D, ((double)pathpoint.c + navigationabstract.a.locZ) / 2.0D);
                        int k = (pathentity.d() - pathentity.e()) * (pathentity.d() - pathentity.e());
                        if (d0 < (double)k) {
                            navigationabstract.l();
                        }
                    }
                }
            }

        }
    }

    protected boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1) {
        VoxelShape voxelshape = iblockdata.h(iblockaccess, blockposition);
        VoxelShape voxelshape1 = iblockdata1.h(iblockaccess, blockposition);
        return VoxelShapes.c(voxelshape, voxelshape1, OperatorBoolean.NOT_SAME);
    }

    public void a(BlockPosition var1) {
    }

    public void a(int var1, int var2, int var3, int var4, int var5, int var6) {
    }

    public void a(@Nullable EntityHuman var1, SoundEffect var2, SoundCategory var3, double var4, double var6, double var8, float var10, float var11) {
    }

    public void a(ParticleParam var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
    }

    public void a(ParticleParam var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14) {
    }

    public void a(Entity entity) {
        if (entity instanceof EntityInsentient) {
            this.a.add(((EntityInsentient)entity).getNavigation());
        }

    }

    public void b(Entity entity) {
        if (entity instanceof EntityInsentient) {
            this.a.remove(((EntityInsentient)entity).getNavigation());
        }

    }

    public void a(SoundEffect var1, BlockPosition var2) {
    }

    public void a(int var1, BlockPosition var2, int var3) {
    }

    public void a(EntityHuman var1, int var2, BlockPosition var3, int var4) {
    }

    public void b(int var1, BlockPosition var2, int var3) {
    }
}
