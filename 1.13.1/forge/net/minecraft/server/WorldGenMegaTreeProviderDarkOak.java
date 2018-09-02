package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class WorldGenMegaTreeProviderDarkOak extends WorldGenMegaTreeProvider {
    public WorldGenMegaTreeProviderDarkOak() {
    }

    @Nullable
    protected WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> b(Random var1) {
        return null;
    }

    @Nullable
    protected WorldGenTreeAbstract<WorldGenFeatureEmptyConfiguration> a(Random var1) {
        return new WorldGenForestTree(true);
    }
}
