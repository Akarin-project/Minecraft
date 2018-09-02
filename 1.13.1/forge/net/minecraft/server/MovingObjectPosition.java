package net.minecraft.server;

public class MovingObjectPosition {
    private BlockPosition e;
    public MovingObjectPosition.EnumMovingObjectType type;
    public EnumDirection direction;
    public Vec3D pos;
    public Entity entity;

    public MovingObjectPosition(Vec3D vec3d, EnumDirection enumdirection, BlockPosition blockposition) {
        this(MovingObjectPosition.EnumMovingObjectType.BLOCK, vec3d, enumdirection, blockposition);
    }

    public MovingObjectPosition(Entity entityx) {
        this(entityx, new Vec3D(entityx.locX, entityx.locY, entityx.locZ));
    }

    public MovingObjectPosition(MovingObjectPosition.EnumMovingObjectType movingobjectposition$enummovingobjecttype, Vec3D vec3d, EnumDirection enumdirection, BlockPosition blockposition) {
        this.type = movingobjectposition$enummovingobjecttype;
        this.e = blockposition;
        this.direction = enumdirection;
        this.pos = new Vec3D(vec3d.x, vec3d.y, vec3d.z);
    }

    public MovingObjectPosition(Entity entityx, Vec3D vec3d) {
        this.type = MovingObjectPosition.EnumMovingObjectType.ENTITY;
        this.entity = entityx;
        this.pos = vec3d;
    }

    public BlockPosition a() {
        return this.e;
    }

    public String toString() {
        return "HitResult{type=" + this.type + ", blockpos=" + this.e + ", f=" + this.direction + ", pos=" + this.pos + ", entity=" + this.entity + '}';
    }

    public static enum EnumMovingObjectType {
        MISS,
        BLOCK,
        ENTITY;

        private EnumMovingObjectType() {
        }
    }
}
