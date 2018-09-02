package net.minecraft.server;

public class BlockNote extends Block {
    public static final BlockStateEnum<BlockPropertyInstrument> INSTRUMENT = BlockProperties.as;
    public static final BlockStateBoolean POWERED = BlockProperties.t;
    public static final BlockStateInteger NOTE = BlockProperties.aj;

    public BlockNote(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(INSTRUMENT, BlockPropertyInstrument.HARP)).set(NOTE, Integer.valueOf(0))).set(POWERED, Boolean.valueOf(false)));
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(INSTRUMENT, BlockPropertyInstrument.a(blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition().down())));
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return enumdirection == EnumDirection.DOWN ? (IBlockData)iblockdata.set(INSTRUMENT, BlockPropertyInstrument.a(iblockdata1)) : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        boolean flag = world.isBlockIndirectlyPowered(blockposition);
        if (flag != iblockdata.get(POWERED)) {
            if (flag) {
                this.play(world, blockposition);
            }

            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(POWERED, Boolean.valueOf(flag)), 3);
        }

    }

    private void play(World world, BlockPosition blockposition) {
        if (world.getType(blockposition.up()).isAir()) {
            world.playBlockAction(blockposition, this, 0, 0);
        }

    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            iblockdata = (IBlockData)iblockdata.a(NOTE);
            world.setTypeAndData(blockposition, iblockdata, 3);
            this.play(world, blockposition);
            entityhuman.a(StatisticList.TUNE_NOTEBLOCK);
            return true;
        }
    }

    public void attack(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman) {
        if (!world.isClientSide) {
            this.play(world, blockposition);
            entityhuman.a(StatisticList.PLAY_NOTEBLOCK);
        }
    }

    public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, int var4, int var5) {
        int i = iblockdata.get(NOTE);
        float f = (float)Math.pow(2.0D, (double)(i - 12) / 12.0D);
        world.a((EntityHuman)null, blockposition, ((BlockPropertyInstrument)iblockdata.get(INSTRUMENT)).a(), SoundCategory.RECORDS, 3.0F, f);
        world.addParticle(Particles.I, (double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 1.2D, (double)blockposition.getZ() + 0.5D, (double)i / 24.0D, 0.0D, 0.0D);
        return true;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(INSTRUMENT, POWERED, NOTE);
    }
}
