package net.minecraft.server;

import javax.annotation.Nullable;

public class PredicateBlockType implements PredicateBlock<IBlockData> {
    private final Block a;

    public PredicateBlockType(Block block) {
        this.a = block;
    }

    public static PredicateBlockType a(Block block) {
        return new PredicateBlockType(block);
    }

    public boolean a(@Nullable IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return iblockdata != null && iblockdata.getBlock() == this.a;
    }

    // $FF: synthetic method
    public boolean test(@Nullable Object object, IBlockAccess iblockaccess, BlockPosition blockposition) {
        return this.a((IBlockData)object, iblockaccess, blockposition);
    }
}
