package net.minecraft.server;

import java.util.Random;

public abstract class WorldGenFeatureCoral extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    public WorldGenFeatureCoral() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        IBlockData iblockdata = ((Block)TagsBlock.CORAL_BLOCKS.a(random)).getBlockData();
        return this.a(generatoraccess, random, blockposition, iblockdata);
    }

    protected abstract boolean a(GeneratorAccess var1, Random var2, BlockPosition var3, IBlockData var4);

    protected boolean b(GeneratorAccess generatoraccess, Random random, BlockPosition blockposition, IBlockData iblockdata) {
        BlockPosition blockposition1 = blockposition.up();
        IBlockData iblockdata1 = generatoraccess.getType(blockposition);
        if ((iblockdata1.getBlock() == Blocks.WATER || iblockdata1.a(TagsBlock.CORALS)) && generatoraccess.getType(blockposition1).getBlock() == Blocks.WATER) {
            generatoraccess.setTypeAndData(blockposition, iblockdata, 3);
            if (random.nextFloat() < 0.25F) {
                generatoraccess.setTypeAndData(blockposition1, ((Block)TagsBlock.CORALS.a(random)).getBlockData(), 2);
            } else if (random.nextFloat() < 0.05F) {
                generatoraccess.setTypeAndData(blockposition1, (IBlockData)Blocks.SEA_PICKLE.getBlockData().set(BlockSeaPickle.a, Integer.valueOf(random.nextInt(4) + 1)), 2);
            }

            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                if (random.nextFloat() < 0.2F) {
                    BlockPosition blockposition2 = blockposition.shift(enumdirection);
                    if (generatoraccess.getType(blockposition2).getBlock() == Blocks.WATER) {
                        IBlockData iblockdata2 = (IBlockData)((Block)TagsBlock.WALL_CORALS.a(random)).getBlockData().set(BlockCoralFanWallAbstract.a, enumdirection);
                        generatoraccess.setTypeAndData(blockposition2, iblockdata2, 2);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
