package net.minecraft.server;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface IEntityAccess {
    List<Entity> getEntities(@Nullable Entity var1, AxisAlignedBB var2, @Nullable Predicate<? super Entity> var3);

    default List<Entity> getEntities(@Nullable Entity entity, AxisAlignedBB axisalignedbb) {
        return this.getEntities(entity, axisalignedbb, IEntitySelector.f);
    }

    default Stream<VoxelShape> a(@Nullable Entity entity, VoxelShape voxelshape, Set<Entity> set) {
        if (voxelshape.b()) {
            return Stream.empty();
        } else {
            AxisAlignedBB axisalignedbb = voxelshape.a();
            return this.getEntities(entity, axisalignedbb.g(0.25D)).stream().filter((entity2) -> {
                return !set.contains(entity2) && (entity == null || !entity.x(entity2));
            }).flatMap((entity2) -> {
                return Stream.of(entity2.al(), entity == null ? null : entity.j(entity2)).filter(Objects::nonNull).filter((axisalignedbb3) -> {
                    return axisalignedbb3.c(axisalignedbb);
                }).map(VoxelShapes::a);
            });
        }
    }
}
