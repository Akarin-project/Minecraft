package net.minecraft.server;

public class TileEntityEnderChest extends TileEntity implements ITickable {
    public float a;
    public float e;
    public int f;
    private int g;

    public TileEntityEnderChest() {
        super(TileEntityTypes.ENDER_CHEST);
    }

    public void Y_() {
        if (++this.g % 20 * 4 == 0) {
            this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.f);
        }

        this.e = this.a;
        int i = this.position.getX();
        int j = this.position.getY();
        int k = this.position.getZ();
        float fx = 0.1F;
        if (this.f > 0 && this.a == 0.0F) {
            double d0 = (double)i + 0.5D;
            double d1 = (double)k + 0.5D;
            this.world.a((EntityHuman)null, d0, (double)j + 0.5D, d1, SoundEffects.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }

        if (this.f == 0 && this.a > 0.0F || this.f > 0 && this.a < 1.0F) {
            float f2 = this.a;
            if (this.f > 0) {
                this.a += 0.1F;
            } else {
                this.a -= 0.1F;
            }

            if (this.a > 1.0F) {
                this.a = 1.0F;
            }

            float f1 = 0.5F;
            if (this.a < 0.5F && f2 >= 0.5F) {
                double d3 = (double)i + 0.5D;
                double d2 = (double)k + 0.5D;
                this.world.a((EntityHuman)null, d3, (double)j + 0.5D, d2, SoundEffects.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
            }

            if (this.a < 0.0F) {
                this.a = 0.0F;
            }
        }

    }

    public boolean c(int i, int j) {
        if (i == 1) {
            this.f = j;
            return true;
        } else {
            return super.c(i, j);
        }
    }

    public void y() {
        this.invalidateBlockCache();
        super.y();
    }

    public void c() {
        ++this.f;
        this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.f);
    }

    public void d() {
        --this.f;
        this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.f);
    }

    public boolean a(EntityHuman entityhuman) {
        if (this.world.getTileEntity(this.position) != this) {
            return false;
        } else {
            return !(entityhuman.d((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) > 64.0D);
        }
    }
}
