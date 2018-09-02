package net.minecraft.server;

public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract {
    public static final BlockStateInteger POWER = BlockProperties.al;
    private final int weight;

    protected BlockPressurePlateWeighted(int i, Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(POWER, Integer.valueOf(0)));
        this.weight = i;
    }

    protected int b(World world, BlockPosition blockposition) {
        int i = Math.min(world.a(Entity.class, c.a(blockposition)).size(), this.weight);
        if (i > 0) {
            float f = (float)Math.min(this.weight, i) / (float)this.weight;
            return MathHelper.f(f * 15.0F);
        } else {
            return 0;
        }
    }

    protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        generatoraccess.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.90000004F);
    }

    protected void b(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        generatoraccess.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.75F);
    }

    protected int getPower(IBlockData iblockdata) {
        return iblockdata.get(POWER);
    }

    protected IBlockData a(IBlockData iblockdata, int i) {
        return (IBlockData)iblockdata.set(POWER, Integer.valueOf(i));
    }

    public int a(IWorldReader var1) {
        return 10;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(POWER);
    }
}