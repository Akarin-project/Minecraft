package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenTreeProviderOak extends WorldGenTreeProvider {
    public WorldGenTreeProviderOak() {
    }

    @Nullable
    protected WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> b(Random random) {
        return (WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration>)(random.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true));
    }
}
