package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class WorldGenTreeAbstract<T extends WorldGenFeatureConfiguration> extends WorldGenerator<T> {
    public WorldGenTreeAbstract(boolean flag) {
        super(flag);
    }

    protected boolean a(Block block) {
        IBlockData iblockdata = block.getBlockData();
        return iblockdata.isAir() || iblockdata.a(TagsBlock.LEAVES) || block == Blocks.GRASS_BLOCK || Block.d(block) || block.a(TagsBlock.LOGS) || block.a(TagsBlock.SAPLINGS) || block == Blocks.VINE;
    }

    protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        if (!Block.d(generatoraccess.getType(blockposition).getBlock())) {
            this.a(generatoraccess, blockposition, Blocks.DIRT.getBlockData());
        }

    }

    protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        this.b(generatoraccess, blockposition, iblockdata);
    }

    protected final void a(Set<BlockPosition> set, GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        this.b(generatoraccess, blockposition, iblockdata);
        if (TagsBlock.LOGS.isTagged(iblockdata.getBlock())) {
            set.add(blockposition.h());
        }

    }

    private void b(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (this.aG) {
            generatoraccess.setTypeAndData(blockposition, iblockdata, 19);
        } else {
            generatoraccess.setTypeAndData(blockposition, iblockdata, 18);
        }

    }

    public final boolean generate(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random random, BlockPosition blockposition, T var5) {
        HashSet hashset = Sets.newHashSet();
        boolean flag = this.a(hashset, generatoraccess, random, blockposition);
        ArrayList arraylist = Lists.newArrayList();
        boolean flag1 = true;

        for(int i = 0; i < 6; ++i) {
            arraylist.add(Sets.newHashSet());
        }

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            if (flag && !hashset.isEmpty()) {
                for(BlockPosition blockposition1 : Lists.newArrayList(hashset)) {
                    for(EnumDirection enumdirection : EnumDirection.values()) {
                        blockposition$b.j(blockposition1).d(enumdirection);
                        if (!hashset.contains(blockposition$b)) {
                            IBlockData iblockdata = generatoraccess.getType(blockposition$b);
                            if (iblockdata.b(BlockProperties.ab)) {
                                ((Set)arraylist.get(0)).add(blockposition$b.h());
                                this.b(generatoraccess, blockposition$b, (IBlockData)iblockdata.set(BlockProperties.ab, Integer.valueOf(1)));
                            }
                        }
                    }
                }
            }

            for(int k = 1; k < 6; ++k) {
                Set set = (Set)arraylist.get(k - 1);
                Set set1 = (Set)arraylist.get(k);

                for(BlockPosition blockposition2 : set) {
                    for(EnumDirection enumdirection1 : EnumDirection.values()) {
                        blockposition$b.j(blockposition2).d(enumdirection1);
                        if (!set.contains(blockposition$b) && !set1.contains(blockposition$b)) {
                            IBlockData iblockdata1 = generatoraccess.getType(blockposition$b);
                            if (iblockdata1.b(BlockProperties.ab)) {
                                int j = iblockdata1.get(BlockProperties.ab);
                                if (j > k + 1) {
                                    IBlockData iblockdata2 = (IBlockData)iblockdata1.set(BlockProperties.ab, Integer.valueOf(k + 1));
                                    this.b(generatoraccess, blockposition$b, iblockdata2);
                                    set1.add(blockposition$b.h());
                                }
                            }
                        }
                    }
                }
            }
        }

        return flag;
    }

    protected abstract boolean a(Set<BlockPosition> var1, GeneratorAccess var2, Random var3, BlockPosition var4);
}
