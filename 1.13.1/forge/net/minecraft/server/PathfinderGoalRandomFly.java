package net.minecraft.server;

import java.util.Iterator;
import javax.annotation.Nullable;

public class PathfinderGoalRandomFly extends PathfinderGoalRandomStrollLand {
    public PathfinderGoalRandomFly(EntityCreature entitycreature, double d0) {
        super(entitycreature, d0);
    }

    @Nullable
    protected Vec3D g() {
        Vec3D vec3d = null;
        if (this.a.isInWater()) {
            vec3d = RandomPositionGenerator.b(this.a, 15, 15);
        }

        if (this.a.getRandom().nextFloat() >= this.h) {
            vec3d = this.j();
        }

        return vec3d == null ? super.g() : vec3d;
    }

    @Nullable
    private Vec3D j() {
        BlockPosition blockposition = new BlockPosition(this.a);
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();
        Iterable iterable = BlockPosition.MutableBlockPosition.b(MathHelper.floor(this.a.locX - 3.0D), MathHelper.floor(this.a.locY - 6.0D), MathHelper.floor(this.a.locZ - 3.0D), MathHelper.floor(this.a.locX + 3.0D), MathHelper.floor(this.a.locY + 6.0D), MathHelper.floor(this.a.locZ + 3.0D));
        Iterator iterator = iterable.iterator();

        BlockPosition blockposition1;
        while(true) {
            if (!iterator.hasNext()) {
                return null;
            }

            blockposition1 = (BlockPosition)iterator.next();
            if (!blockposition.equals(blockposition1)) {
                Block block = this.a.world.getType(blockposition$mutableblockposition1.g(blockposition1).c(EnumDirection.DOWN)).getBlock();
                boolean flag = block instanceof BlockLeaves || block.a(TagsBlock.LOGS);
                if (flag && this.a.world.isEmpty(blockposition1) && this.a.world.isEmpty(blockposition$mutableblockposition.g(blockposition1).c(EnumDirection.UP))) {
                    break;
                }
            }
        }

        return new Vec3D((double)blockposition1.getX(), (double)blockposition1.getY(), (double)blockposition1.getZ());
    }
}
