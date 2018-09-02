package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenTreeProviderSpruce extends WorldGenMegaTreeProvider {
    public WorldGenTreeProviderSpruce() {
    }

    @Nullable
    protected WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> b(Random var1) {
        return new WorldGenTaiga2(true);
    }

    @Nullable
    protected WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> a(Random random) {
        return new WorldGenMegaTree(false, random.nextBoolean());
    }
}
