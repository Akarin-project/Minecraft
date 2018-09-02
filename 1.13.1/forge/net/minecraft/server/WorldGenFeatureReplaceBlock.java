package net.minecraft.server;

import java.util.Random;

public class WorldGenFeatureReplaceBlock extends WorldGenerator<WorldGenFeatureReplaceBlockConfiguration> {
    public WorldGenFeatureReplaceBlock() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random var3, BlockPosition blockposition, WorldGenFeatureReplaceBlockConfiguration worldgenfeaturereplaceblockconfiguration) {
        if (worldgenfeaturereplaceblockconfiguration.a.test(generatoraccess.getType(blockposition))) {
            generatoraccess.setTypeAndData(blockposition, worldgenfeaturereplaceblockconfiguration.b, 2);
        }

        return true;
    }
}
