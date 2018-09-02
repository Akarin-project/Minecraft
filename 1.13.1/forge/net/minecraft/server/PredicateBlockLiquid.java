package net.minecraft.server;

public class PredicateBlockLiquid implements PredicateBlock<IBlockData> {
    private static final PredicateBlockLiquid a = new PredicateBlockLiquid();

    public PredicateBlockLiquid() {
    }

    public boolean a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return !iblockdata.s().e();
    }

    public static PredicateBlockLiquid a() {
        return a;
    }

    // $FF: synthetic method
    public boolean test(Object object, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return this.a((IBlockData)object, iblockaccess, blockposition);
    }
}
