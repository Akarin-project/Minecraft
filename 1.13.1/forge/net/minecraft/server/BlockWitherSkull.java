package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockWitherSkull extends BlockSkull {
    private static ShapeDetector c;
    private static ShapeDetector o;

    protected BlockWitherSkull(Block.Info block$info) {
        super(BlockSkull.Type.WITHER_SKELETON, block$info);
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
        super.postPlace(world, blockposition, iblockdata, entityliving, itemstack);
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (tileentity instanceof TileEntitySkull) {
            a(world, blockposition, (TileEntitySkull)tileentity);
        }

    }

    public static void a(World world, BlockPosition blockposition, TileEntitySkull tileentityskull) {
        Block block = tileentityskull.getBlock().getBlock();
        boolean flag = block == Blocks.WITHER_SKELETON_SKULL || block == Blocks.WITHER_SKELETON_WALL_SKULL;
        if (flag && blockposition.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isClientSide) {
            ShapeDetector shapedetector = d();
            ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = shapedetector.a(world, blockposition);
            if (shapedetector$shapedetectorcollection != null) {
                for(int i = 0; i < 3; ++i) {
                    TileEntitySkull.a(world, shapedetector$shapedetectorcollection.a(i, 0, 0).getPosition());
                }

                for(int k = 0; k < shapedetector.c(); ++k) {
                    for(int j = 0; j < shapedetector.b(); ++j) {
                        world.setTypeAndData(shapedetector$shapedetectorcollection.a(k, j, 0).getPosition(), Blocks.AIR.getBlockData(), 2);
                    }
                }

                BlockPosition blockposition2 = shapedetector$shapedetectorcollection.a(1, 0, 0).getPosition();
                EntityWither entitywither = new EntityWither(world);
                BlockPosition blockposition1 = shapedetector$shapedetectorcollection.a(1, 2, 0).getPosition();
                entitywither.setPositionRotation((double)blockposition1.getX() + 0.5D, (double)blockposition1.getY() + 0.55D, (double)blockposition1.getZ() + 0.5D, shapedetector$shapedetectorcollection.getFacing().k() == EnumDirection.EnumAxis.X ? 0.0F : 90.0F, 0.0F);
                entitywither.aQ = shapedetector$shapedetectorcollection.getFacing().k() == EnumDirection.EnumAxis.X ? 0.0F : 90.0F;
                entitywither.l();

                for(EntityPlayer entityplayer : world.a(EntityPlayer.class, entitywither.getBoundingBox().g(50.0D))) {
                    CriterionTriggers.n.a(entityplayer, entitywither);
                }

                world.addEntity(entitywither);

                for(int l = 0; l < 120; ++l) {
                    world.addParticle(Particles.E, (double)blockposition2.getX() + world.random.nextDouble(), (double)(blockposition2.getY() - 2) + world.random.nextDouble() * 3.9D, (double)blockposition2.getZ() + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                for(int i1 = 0; i1 < shapedetector.c(); ++i1) {
                    for(int j1 = 0; j1 < shapedetector.b(); ++j1) {
                        world.update(shapedetector$shapedetectorcollection.a(i1, j1, 0).getPosition(), Blocks.AIR);
                    }
                }

            }
        }
    }

    public static boolean b(World world, BlockPosition blockposition, ItemStack itemstack) {
        if (itemstack.getItem() == Items.WITHER_SKELETON_SKULL && blockposition.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isClientSide) {
            return e().a(world, blockposition) != null;
        } else {
            return false;
        }
    }

    protected static ShapeDetector d() {
        if (c == null) {
            c = ShapeDetectorBuilder.a().a("^^^", "###", "~#~").a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SOUL_SAND))).a('^', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.WITHER_SKELETON_SKULL).or(BlockStatePredicate.a(Blocks.WITHER_SKELETON_WALL_SKULL)))).a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.AIR))).b();
        }

        return c;
    }

    protected static ShapeDetector e() {
        if (o == null) {
            o = ShapeDetectorBuilder.a().a("   ", "###", "~#~").a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SOUL_SAND))).a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.AIR))).b();
        }

        return o;
    }
}
