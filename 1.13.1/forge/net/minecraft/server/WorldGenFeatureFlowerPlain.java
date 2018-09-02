package net.minecraft.server;

import java.util.Random;

public class WorldGenFeatureFlowerPlain extends WorldGenFlowers {
    public WorldGenFeatureFlowerPlain() {
    }

    public IBlockData a(Random random, BlockPosition blockposition) {
        double d0 = BiomeBase.aJ.a((double)blockposition.getX() / 200.0D, (double)blockposition.getZ() / 200.0D);
        if (d0 < -0.8D) {
            int j = random.nextInt(4);
            switch(j) {
            case 0:
                return Blocks.ORANGE_TULIP.getBlockData();
            case 1:
                return Blocks.RED_TULIP.getBlockData();
            case 2:
                return Blocks.PINK_TULIP.getBlockData();
            case 3:
            default:
                return Blocks.WHITE_TULIP.getBlockData();
            }
        } else if (random.nextInt(3) > 0) {
            int i = random.nextInt(3);
            if (i == 0) {
                return Blocks.POPPY.getBlockData();
            } else {
                return i == 1 ? Blocks.AZURE_BLUET.getBlockData() : Blocks.OXEYE_DAISY.getBlockData();
            }
        } else {
            return Blocks.DANDELION.getBlockData();
        }
    }
}
