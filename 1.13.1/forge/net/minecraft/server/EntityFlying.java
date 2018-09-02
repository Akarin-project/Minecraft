package net.minecraft.server;

public abstract class EntityFlying extends EntityInsentient {
    protected EntityFlying(EntityTypes<?> entitytypes, World world) {
        super(entitytypes, world);
    }

    public void c(float var1, float var2) {
    }

    protected void a(double var1, boolean var3, IBlockData var4, BlockPosition var5) {
    }

    public void a(float f, float f1, float f2) {
        if (this.isInWater()) {
            this.a(f, f1, f2, 0.02F);
            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
            this.motX *= (double)0.8F;
            this.motY *= (double)0.8F;
            this.motZ *= (double)0.8F;
        } else if (this.ax()) {
            this.a(f, f1, f2, 0.02F);
            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
        } else {
            float f3 = 0.91F;
            if (this.onGround) {
                f3 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().n() * 0.91F;
            }

            float f4 = 0.16277137F / (f3 * f3 * f3);
            this.a(f, f1, f2, this.onGround ? 0.1F * f4 : 0.02F);
            f3 = 0.91F;
            if (this.onGround) {
                f3 = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().n() * 0.91F;
            }

            this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
            this.motX *= (double)f3;
            this.motY *= (double)f3;
            this.motZ *= (double)f3;
        }

        this.aI = this.aJ;
        double d1 = this.locX - this.lastX;
        double d0 = this.locZ - this.lastZ;
        float f5 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
        if (f5 > 1.0F) {
            f5 = 1.0F;
        }

        this.aJ += (f5 - this.aJ) * 0.4F;
        this.aK += this.aJ;
    }

    public boolean z_() {
        return false;
    }
}
