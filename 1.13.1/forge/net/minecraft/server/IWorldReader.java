package net.minecraft.server;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public interface IWorldReader extends IBlockAccess {
    boolean isEmpty(BlockPosition var1);

    BiomeBase getBiome(BlockPosition var1);

    int getBrightness(EnumSkyBlock var1, BlockPosition var2);

    default boolean z(BlockPosition blockposition) {
        if (blockposition.getY() >= this.getSeaLevel()) {
            return this.e(blockposition);
        } else {
            BlockPosition blockposition1 = new BlockPosition(blockposition.getX(), this.getSeaLevel(), blockposition.getZ());
            if (!this.e(blockposition1)) {
                return false;
            } else {
                for(BlockPosition blockposition2 = blockposition1.down(); blockposition2.getY() > blockposition.getY(); blockposition2 = blockposition2.down()) {
                    IBlockData iblockdata = this.getType(blockposition2);
                    if (iblockdata.b(this, blockposition2) > 0 && !iblockdata.getMaterial().isLiquid()) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    int getLightLevel(BlockPosition var1, int var2);

    boolean isChunkLoaded(int var1, int var2, boolean var3);

    boolean e(BlockPosition var1);

    default BlockPosition getHighestBlockYAt(HeightMap.Type heightmap$type, BlockPosition blockposition) {
        return new BlockPosition(blockposition.getX(), this.a(heightmap$type, blockposition.getX(), blockposition.getZ()), blockposition.getZ());
    }

    int a(HeightMap.Type var1, int var2, int var3);

    default float A(BlockPosition blockposition) {
        return this.o().i()[this.getLightLevel(blockposition)];
    }

    @Nullable
    default EntityHuman findNearbyPlayer(Entity entity, double d0) {
        return this.a(entity.locX, entity.locY, entity.locZ, d0, false);
    }

    @Nullable
    default EntityHuman b(Entity entity, double d0) {
        return this.a(entity.locX, entity.locY, entity.locZ, d0, true);
    }

    @Nullable
    default EntityHuman a(double d0, double d1, double d2, double d3, boolean flag) {
        Predicate predicate = flag ? IEntitySelector.e : IEntitySelector.f;
        return this.a(d0, d1, d2, d3, predicate);
    }

    @Nullable
    EntityHuman a(double var1, double var3, double var5, double var7, Predicate<Entity> var9);

    int c();

    WorldBorder getWorldBorder();

    boolean a(@Nullable Entity var1, VoxelShape var2);

    int a(BlockPosition var1, EnumDirection var2);

    boolean e();

    int getSeaLevel();

    default boolean a(IBlockData iblockdata, BlockPosition blockposition) {
        VoxelShape voxelshape = iblockdata.h(this, blockposition);
        return voxelshape.b() || this.a((Entity)null, voxelshape.a((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ()));
    }

    default boolean a_(@Nullable Entity entity, AxisAlignedBB axisalignedbb) {
        return this.a(entity, VoxelShapes.a(axisalignedbb));
    }

    default Stream<VoxelShape> a(VoxelShape voxelshape, VoxelShape voxelshape1, boolean flag) {
        int i = MathHelper.floor(voxelshape.b(EnumDirection.EnumAxis.X)) - 1;
        int j = MathHelper.f(voxelshape.c(EnumDirection.EnumAxis.X)) + 1;
        int k = MathHelper.floor(voxelshape.b(EnumDirection.EnumAxis.Y)) - 1;
        int l = MathHelper.f(voxelshape.c(EnumDirection.EnumAxis.Y)) + 1;
        int i1 = MathHelper.floor(voxelshape.b(EnumDirection.EnumAxis.Z)) - 1;
        int j1 = MathHelper.f(voxelshape.c(EnumDirection.EnumAxis.Z)) + 1;
        WorldBorder worldborder = this.getWorldBorder();
        boolean flag1 = worldborder.b() < (double)i && (double)j < worldborder.d() && worldborder.c() < (double)i1 && (double)j1 < worldborder.e();
        VoxelShapeBitSet voxelshapebitset = new VoxelShapeBitSet(j - i, l - k, j1 - i1);
        Predicate predicate = (voxelshape3) -> {
            return !voxelshape3.b() && VoxelShapes.c(voxelshape, voxelshape3, OperatorBoolean.AND);
        };
        Stream stream = StreamSupport.stream(BlockPosition.MutableBlockPosition.b(i, k, i1, j - 1, l - 1, j1 - 1).spliterator(), false).map((blockposition$mutableblockposition) -> {
            int i3 = blockposition$mutableblockposition.getX();
            int j3 = blockposition$mutableblockposition.getY();
            int k3 = blockposition$mutableblockposition.getZ();
            boolean flag4 = i3 == i || i3 == j - 1;
            boolean flag5 = j3 == k || j3 == l - 1;
            boolean flag6 = k3 == i1 || k3 == j1 - 1;
            if ((!flag4 || !flag5) && (!flag5 || !flag6) && (!flag6 || !flag4) && this.isLoaded(blockposition$mutableblockposition)) {
                VoxelShape voxelshape3;
                if (flag && !flag1 && !worldborder.a(blockposition$mutableblockposition)) {
                    voxelshape3 = VoxelShapes.b();
                } else {
                    voxelshape3 = this.getType(blockposition$mutableblockposition).h(this, blockposition$mutableblockposition);
                }

                VoxelShape voxelshape4 = voxelshape1.a((double)(-i3), (double)(-j3), (double)(-k3));
                if (VoxelShapes.c(voxelshape4, voxelshape3, OperatorBoolean.AND)) {
                    return VoxelShapes.a();
                } else if (voxelshape3 == VoxelShapes.b()) {
                    voxelshapebitset.a(i3 - i, j3 - k, k3 - i1, true, true);
                    return VoxelShapes.a();
                } else {
                    return voxelshape3.a((double)i3, (double)j3, (double)k3);
                }
            } else {
                return VoxelShapes.a();
            }
        }).filter(predicate);
        return Stream.concat(stream, Stream.generate(() -> {
            return new VoxelShapeWorldRegion(voxelshapebitset, i, k, i1);
        }).limit(1L).filter(predicate));
    }

    default Stream<VoxelShape> a(@Nullable Entity entity, AxisAlignedBB axisalignedbb, double d0, double d1, double d2) {
        return this.a(entity, axisalignedbb, Collections.emptySet(), d0, d1, d2);
    }

    default Stream<VoxelShape> a(@Nullable Entity entity, AxisAlignedBB axisalignedbb, Set<Entity> set, double d0, double d1, double d2) {
        double d3 = 1.0E-7D;
        VoxelShape voxelshape = VoxelShapes.a(axisalignedbb);
        VoxelShape voxelshape1 = VoxelShapes.a(axisalignedbb.d(d0 > 0.0D ? -1.0E-7D : 1.0E-7D, d1 > 0.0D ? -1.0E-7D : 1.0E-7D, d2 > 0.0D ? -1.0E-7D : 1.0E-7D));
        VoxelShape voxelshape2 = VoxelShapes.b(VoxelShapes.a(axisalignedbb.b(d0, d1, d2).g(1.0E-7D)), voxelshape1, OperatorBoolean.ONLY_FIRST);
        return this.a(entity, voxelshape2, voxelshape, set);
    }

    default Stream<VoxelShape> b(@Nullable Entity entity, AxisAlignedBB axisalignedbb) {
        return this.a(entity, VoxelShapes.a(axisalignedbb), VoxelShapes.a(), Collections.emptySet());
    }

    default Stream<VoxelShape> a(@Nullable Entity entity, VoxelShape voxelshape, VoxelShape voxelshape1, Set<Entity> var4) {
        boolean flag = entity != null && entity.bG();
        boolean flag1 = entity != null && this.i(entity);
        if (entity != null && flag == flag1) {
            entity.n(!flag1);
        }

        return this.a(voxelshape, voxelshape1, flag1);
    }

    default boolean i(Entity entity) {
        WorldBorder worldborder = this.getWorldBorder();
        double d0 = worldborder.b();
        double d1 = worldborder.c();
        double d2 = worldborder.d();
        double d3 = worldborder.e();
        if (entity.bG()) {
            ++d0;
            ++d1;
            --d2;
            --d3;
        } else {
            --d0;
            --d1;
            ++d2;
            ++d3;
        }

        return entity.locX > d0 && entity.locX < d2 && entity.locZ > d1 && entity.locZ < d3;
    }

    default boolean a(@Nullable Entity entity, AxisAlignedBB axisalignedbb, Set<Entity> set) {
        return this.a(entity, VoxelShapes.a(axisalignedbb), VoxelShapes.a(), set).allMatch(VoxelShape::b);
    }

    default boolean getCubes(@Nullable Entity entity, AxisAlignedBB axisalignedbb) {
        return this.a(entity, axisalignedbb, Collections.emptySet());
    }

    default boolean B(BlockPosition blockposition) {
        return this.b(blockposition).a(TagsFluid.WATER);
    }

    default boolean containsLiquid(AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.f(axisalignedbb.d);
        int k = MathHelper.floor(axisalignedbb.b);
        int l = MathHelper.f(axisalignedbb.e);
        int i1 = MathHelper.floor(axisalignedbb.c);
        int j1 = MathHelper.f(axisalignedbb.f);

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(int k1 = i; k1 < j; ++k1) {
                for(int l1 = k; l1 < l; ++l1) {
                    for(int i2 = i1; i2 < j1; ++i2) {
                        IBlockData iblockdata = this.getType(blockposition$b.f(k1, l1, i2));
                        if (!iblockdata.s().e()) {
                            boolean flag = true;
                            return flag;
                        }
                    }
                }
            }

            return false;
        }
    }

    default int getLightLevel(BlockPosition blockposition) {
        return this.d(blockposition, this.c());
    }

    default int d(BlockPosition blockposition, int i) {
        if (blockposition.getX() >= -30000000 && blockposition.getZ() >= -30000000 && blockposition.getX() < 30000000 && blockposition.getZ() < 30000000) {
            if (this.getType(blockposition).c(this, blockposition)) {
                int j = this.getLightLevel(blockposition.up(), i);
                int k = this.getLightLevel(blockposition.east(), i);
                int l = this.getLightLevel(blockposition.west(), i);
                int i1 = this.getLightLevel(blockposition.south(), i);
                int j1 = this.getLightLevel(blockposition.north(), i);
                if (k > j) {
                    j = k;
                }

                if (l > j) {
                    j = l;
                }

                if (i1 > j) {
                    j = i1;
                }

                if (j1 > j) {
                    j = j1;
                }

                return j;
            } else {
                return this.getLightLevel(blockposition, i);
            }
        } else {
            return 15;
        }
    }

    default boolean isLoaded(BlockPosition blockposition) {
        return this.b(blockposition, true);
    }

    default boolean b(BlockPosition blockposition, boolean flag) {
        return this.isChunkLoaded(blockposition.getX() >> 4, blockposition.getZ() >> 4, flag);
    }

    default boolean areChunksLoaded(BlockPosition blockposition, int i) {
        return this.areChunksLoaded(blockposition, i, true);
    }

    default boolean areChunksLoaded(BlockPosition blockposition, int i, boolean flag) {
        return this.isAreaLoaded(blockposition.getX() - i, blockposition.getY() - i, blockposition.getZ() - i, blockposition.getX() + i, blockposition.getY() + i, blockposition.getZ() + i, flag);
    }

    default boolean areChunksLoadedBetween(BlockPosition blockposition, BlockPosition blockposition1) {
        return this.areChunksLoadedBetween(blockposition, blockposition1, true);
    }

    default boolean areChunksLoadedBetween(BlockPosition blockposition, BlockPosition blockposition1, boolean flag) {
        return this.isAreaLoaded(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), flag);
    }

    default boolean a(StructureBoundingBox structureboundingbox) {
        return this.a(structureboundingbox, true);
    }

    default boolean a(StructureBoundingBox structureboundingbox, boolean flag) {
        return this.isAreaLoaded(structureboundingbox.a, structureboundingbox.b, structureboundingbox.c, structureboundingbox.d, structureboundingbox.e, structureboundingbox.f, flag);
    }

    default boolean isAreaLoaded(int i, int j, int k, int l, int i1, int j1, boolean flag) {
        if (i1 >= 0 && j < 256) {
            i = i >> 4;
            k = k >> 4;
            l = l >> 4;
            j1 = j1 >> 4;

            for(int k1 = i; k1 <= l; ++k1) {
                for(int l1 = k; l1 <= j1; ++l1) {
                    if (!this.isChunkLoaded(k1, l1, flag)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    WorldProvider o();
}
