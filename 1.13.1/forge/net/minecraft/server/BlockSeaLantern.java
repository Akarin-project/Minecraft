package net.minecraft.server;

import java.util.Random;

public class BlockSeaLantern extends Block {
    public BlockSeaLantern(Block.Info block$info) {
        super(block$info);
    }

    public int getDropCount(IBlockData iblockdata, int i, World var3, BlockPosition var4, Random random) {
        return MathHelper.clamp(this.a(iblockdata, random) + random.nextInt(i + 1), 1, 5);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.PRISMARINE_CRYSTALS;
    }

    public int a(IBlockData var1, Random random) {
        return 2 + random.nextInt(2);
    }

    protected boolean X_() {
        return true;
    }
}
