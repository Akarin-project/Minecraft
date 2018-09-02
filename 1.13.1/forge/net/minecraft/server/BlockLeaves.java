package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockLeaves extends Block {
    public static final BlockStateInteger DISTANCE = BlockProperties.ab;
    public static final BlockStateBoolean PERSISTENT = BlockProperties.s;
    protected static boolean c;

    public BlockLeaves(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(DISTANCE, Integer.valueOf(7))).set(PERSISTENT, Boolean.valueOf(false)));
    }

    public boolean isTicking(IBlockData iblockdata) {
        return iblockdata.get(DISTANCE) == 7 && !iblockdata.get(PERSISTENT);
    }

    public void b(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!iblockdata.get(PERSISTENT) && iblockdata.get(DISTANCE) == 7) {
            iblockdata.a(world, blockposition, 0);
            world.setAir(blockposition);
        }

    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        world.setTypeAndData(blockposition, a(iblockdata, world, blockposition), 3);
    }

    public int j(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return 1;
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection var2, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition var6) {
        int i = w(iblockdata1) + 1;
        if (i != 1 || iblockdata.get(DISTANCE) != i) {
            generatoraccess.J().a(blockposition, this, 1);
        }

        return iblockdata;
    }

    private static IBlockData a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
        int i = 7;

        try (BlockPosition.b blockposition$b = BlockPosition.b.r()) {
            for(EnumDirection enumdirection : EnumDirection.values()) {
                blockposition$b.j(blockposition).d(enumdirection);
                i = Math.min(i, w(generatoraccess.getType(blockposition$b)) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return (IBlockData)iblockdata.set(DISTANCE, Integer.valueOf(i));
    }

    private static int w(IBlockData iblockdata) {
        if (TagsBlock.LOGS.isTagged(iblockdata.getBlock())) {
            return 0;
        } else {
            return iblockdata.getBlock() instanceof BlockLeaves ? iblockdata.get(DISTANCE) : 7;
        }
    }

    public int a(IBlockData var1, Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public IMaterial getDropType(IBlockData iblockdata, World var2, BlockPosition var3, int var4) {
        Block block = iblockdata.getBlock();
        if (block == Blocks.OAK_LEAVES) {
            return Blocks.OAK_SAPLING;
        } else if (block == Blocks.SPRUCE_LEAVES) {
            return Blocks.SPRUCE_SAPLING;
        } else if (block == Blocks.BIRCH_LEAVES) {
            return Blocks.BIRCH_SAPLING;
        } else if (block == Blocks.JUNGLE_LEAVES) {
            return Blocks.JUNGLE_SAPLING;
        } else if (block == Blocks.ACACIA_LEAVES) {
            return Blocks.ACACIA_SAPLING;
        } else {
            return block == Blocks.DARK_OAK_LEAVES ? Blocks.DARK_OAK_SAPLING : Blocks.OAK_SAPLING;
        }
    }

    public void dropNaturally(IBlockData iblockdata, World world, BlockPosition blockposition, float var4, int i) {
        if (!world.isClientSide) {
            int j = this.k(iblockdata);
            if (i > 0) {
                j -= 2 << i;
                if (j < 10) {
                    j = 10;
                }
            }

            if (world.random.nextInt(j) == 0) {
                a(world, blockposition, new ItemStack(this.getDropType(iblockdata, world, blockposition, i)));
            }

            j = 200;
            if (i > 0) {
                j -= 10 << i;
                if (j < 40) {
                    j = 40;
                }
            }

            this.a(world, blockposition, iblockdata, j);
        }

    }

    protected void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
        if ((iblockdata.getBlock() == Blocks.OAK_LEAVES || iblockdata.getBlock() == Blocks.DARK_OAK_LEAVES) && world.random.nextInt(i) == 0) {
            a(world, blockposition, new ItemStack(Items.APPLE));
        }

    }

    protected int k(IBlockData iblockdata) {
        return iblockdata.getBlock() == Blocks.JUNGLE_LEAVES ? 40 : 20;
    }

    public TextureType c() {
        return c ? TextureType.CUTOUT_MIPPED : TextureType.SOLID;
    }

    public boolean q(IBlockData var1) {
        return false;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        if (!world.isClientSide && itemstack.getItem() == Items.SHEARS) {
            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
            entityhuman.applyExhaustion(0.005F);
            a(world, blockposition, new ItemStack(this));
        } else {
            super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(DISTANCE, PERSISTENT);
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return a((IBlockData)this.getBlockData().set(PERSISTENT, Boolean.valueOf(true)), blockactioncontext.getWorld(), blockactioncontext.getClickPosition());
    }
}
