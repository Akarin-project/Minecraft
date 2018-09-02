package net.minecraft.server;

import java.util.Random;

public class WorldGenFeatureFlowerSwamp extends WorldGenFlowers {
    public WorldGenFeatureFlowerSwamp() {
    }

    public IBlockData a(Random var1, BlockPosition var2) {
        return Blocks.BLUE_ORCHID.getBlockData();
    }
}
