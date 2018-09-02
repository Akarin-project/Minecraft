package net.minecraft.server;

public class PathfinderGoalJumpOnBlock extends PathfinderGoalGotoTarget {
    private final EntityOcelot f;

    public PathfinderGoalJumpOnBlock(EntityOcelot entityocelot, double d0) {
        super(entityocelot, d0, 8);
        this.f = entityocelot;
    }

    public boolean a() {
        return this.f.isTamed() && !this.f.isSitting() && super.a();
    }

    public void c() {
        super.c();
        this.f.getGoalSit().setSitting(false);
    }

    public void d() {
        super.d();
        this.f.setSitting(false);
    }

    public void e() {
        super.e();
        this.f.getGoalSit().setSitting(false);
        if (!this.k()) {
            this.f.setSitting(false);
        } else if (!this.f.isSitting()) {
            this.f.setSitting(true);
        }

    }

    protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
        if (!iworldreader.isEmpty(blockposition.up())) {
            return false;
        } else {
            IBlockData iblockdata = iworldreader.getType(blockposition);
            Block block = iblockdata.getBlock();
            if (block == Blocks.CHEST) {
                return TileEntityChest.a(iworldreader, blockposition) < 1;
            } else if (block == Blocks.FURNACE && iblockdata.get(BlockFurnace.LIT)) {
                return true;
            } else {
                return block instanceof BlockBed && iblockdata.get(BlockBed.PART) != BlockPropertyBedPart.HEAD;
            }
        }
    }
}
