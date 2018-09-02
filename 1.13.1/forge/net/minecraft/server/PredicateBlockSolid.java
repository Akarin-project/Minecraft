package net.minecraft.server;

import javax.annotation.Nullable;

public class PredicateBlockSolid implements PredicateBlock<IBlockData> {
    private static final PredicateBlockSolid a = new PredicateBlockSolid();

    public PredicateBlockSolid() {
    }

    public boolean a(@Nullable IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return iblockdata != null && !iblockdata.getMaterial().isSolid();
    }

    public static PredicateBlockSolid a() {
        return a;
    }

    // $FF: synthetic method
    public boolean test(@Nullable Object object, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return this.a((IBlockData)object, iblockaccess, blockposition);
    }
}
