package net.minecraft.server;

import java.util.function.Predicate;

public class BlockPumpkinCarved extends BlockFacingHorizontal {
    public static final BlockStateDirection a = BlockFacingHorizontal.FACING;
    private ShapeDetector b;
    private ShapeDetector c;
    private ShapeDetector o;
    private ShapeDetector p;
    private static final Predicate<IBlockData> q = (iblockdata) -> {
        return iblockdata != null && (iblockdata.getBlock() == Blocks.CARVED_PUMPKIN || iblockdata.getBlock() == Blocks.JACK_O_LANTERN);
    };

    protected BlockPumpkinCarved(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH));
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1) {
        if (iblockdata1.getBlock() != iblockdata.getBlock()) {
            this.a(world, blockposition);
        }
    }

    public boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
        return this.d().a(iworldreader, blockposition) != null || this.f().a(iworldreader, blockposition) != null;
    }

    private void a(World world, BlockPosition blockposition) {
        ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = this.e().a(world, blockposition);
        if (shapedetector$shapedetectorcollection != null) {
            for(int i = 0; i < this.e().b(); ++i) {
                ShapeDetectorBlock shapedetectorblock = shapedetector$shapedetectorcollection.a(0, i, 0);
                world.setTypeAndData(shapedetectorblock.getPosition(), Blocks.AIR.getBlockData(), 2);
            }

            EntitySnowman entitysnowman = new EntitySnowman(world);
            BlockPosition blockposition2 = shapedetector$shapedetectorcollection.a(0, 2, 0).getPosition();
            entitysnowman.setPositionRotation((double)blockposition2.getX() + 0.5D, (double)blockposition2.getY() + 0.05D, (double)blockposition2.getZ() + 0.5D, 0.0F, 0.0F);
            world.addEntity(entitysnowman);

            for(EntityPlayer entityplayer : world.a(EntityPlayer.class, entitysnowman.getBoundingBox().g(5.0D))) {
                CriterionTriggers.n.a(entityplayer, entitysnowman);
            }

            int l = Block.getCombinedId(Blocks.SNOW_BLOCK.getBlockData());
            world.triggerEffect(2001, blockposition2, l);
            world.triggerEffect(2001, blockposition2.up(), l);

            for(int k1 = 0; k1 < this.e().b(); ++k1) {
                ShapeDetectorBlock shapedetectorblock1 = shapedetector$shapedetectorcollection.a(0, k1, 0);
                world.update(shapedetectorblock1.getPosition(), Blocks.AIR);
            }
        } else {
            shapedetector$shapedetectorcollection = this.g().a(world, blockposition);
            if (shapedetector$shapedetectorcollection != null) {
                for(int j = 0; j < this.g().c(); ++j) {
                    for(int k = 0; k < this.g().b(); ++k) {
                        world.setTypeAndData(shapedetector$shapedetectorcollection.a(j, k, 0).getPosition(), Blocks.AIR.getBlockData(), 2);
                    }
                }

                BlockPosition blockposition1 = shapedetector$shapedetectorcollection.a(1, 2, 0).getPosition();
                EntityIronGolem entityirongolem = new EntityIronGolem(world);
                entityirongolem.setPlayerCreated(true);
                entityirongolem.setPositionRotation((double)blockposition1.getX() + 0.5D, (double)blockposition1.getY() + 0.05D, (double)blockposition1.getZ() + 0.5D, 0.0F, 0.0F);
                world.addEntity(entityirongolem);

                for(EntityPlayer entityplayer1 : world.a(EntityPlayer.class, entityirongolem.getBoundingBox().g(5.0D))) {
                    CriterionTriggers.n.a(entityplayer1, entityirongolem);
                }

                for(int i1 = 0; i1 < 120; ++i1) {
                    world.addParticle(Particles.E, (double)blockposition1.getX() + world.random.nextDouble(), (double)blockposition1.getY() + world.random.nextDouble() * 3.9D, (double)blockposition1.getZ() + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                for(int j1 = 0; j1 < this.g().c(); ++j1) {
                    for(int l1 = 0; l1 < this.g().b(); ++l1) {
                        ShapeDetectorBlock shapedetectorblock2 = shapedetector$shapedetectorcollection.a(j1, l1, 0);
                        world.update(shapedetectorblock2.getPosition(), Blocks.AIR);
                    }
                }
            }
        }

    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(a, blockactioncontext.f().opposite());
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    protected ShapeDetector d() {
        if (this.b == null) {
            this.b = ShapeDetectorBuilder.a().a(" ", "#", "#").a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SNOW_BLOCK))).b();
        }

        return this.b;
    }

    protected ShapeDetector e() {
        if (this.c == null) {
            this.c = ShapeDetectorBuilder.a().a("^", "#", "#").a('^', ShapeDetectorBlock.a(q)).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SNOW_BLOCK))).b();
        }

        return this.c;
    }

    protected ShapeDetector f() {
        if (this.o == null) {
            this.o = ShapeDetectorBuilder.a().a("~ ~", "###", "~#~").a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.IRON_BLOCK))).a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.AIR))).b();
        }

        return this.o;
    }

    protected ShapeDetector g() {
        if (this.p == null) {
            this.p = ShapeDetectorBuilder.a().a("~^~", "###", "~#~").a('^', ShapeDetectorBlock.a(q)).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.IRON_BLOCK))).a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.AIR))).b();
        }

        return this.p;
    }
}
