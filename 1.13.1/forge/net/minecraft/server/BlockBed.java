package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockBed extends BlockFacingHorizontal implements ITileEntity {
    public static final BlockStateEnum<BlockPropertyBedPart> PART = BlockProperties.ao;
    public static final BlockStateBoolean OCCUPIED = BlockProperties.q;
    protected static final VoxelShape c = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    private final EnumColor color;

    public BlockBed(EnumColor enumcolor, Block.Info block$info) {
        super(block$info);
        this.color = enumcolor;
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(PART, BlockPropertyBedPart.FOOT)).set(OCCUPIED, Boolean.valueOf(false)));
    }

    public MaterialMapColor c(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return iblockdata.get(PART) == BlockPropertyBedPart.FOOT ? this.color.e() : MaterialMapColor.e;
    }

    public boolean interact(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            if (iblockdata.get(PART) != BlockPropertyBedPart.HEAD) {
                blockposition = blockposition.shift((EnumDirection)iblockdata.get(FACING));
                iblockdata = world.getType(blockposition);
                if (iblockdata.getBlock() != this) {
                    return true;
                }
            }

            if (world.worldProvider.p() && world.getBiome(blockposition) != Biomes.j) {
                if (iblockdata.get(OCCUPIED)) {
                    EntityHuman entityhuman1 = this.a(world, blockposition);
                    if (entityhuman1 != null) {
                        entityhuman.a(new ChatMessage("block.minecraft.bed.occupied", new Object[0]), true);
                        return true;
                    }

                    iblockdata = (IBlockData)iblockdata.set(OCCUPIED, Boolean.valueOf(false));
                    world.setTypeAndData(blockposition, iblockdata, 4);
                }

                EntityHuman.EnumBedResult entityhuman$enumbedresult = entityhuman.a(blockposition);
                if (entityhuman$enumbedresult == EntityHuman.EnumBedResult.OK) {
                    iblockdata = (IBlockData)iblockdata.set(OCCUPIED, Boolean.valueOf(true));
                    world.setTypeAndData(blockposition, iblockdata, 4);
                    return true;
                } else {
                    if (entityhuman$enumbedresult == EntityHuman.EnumBedResult.NOT_POSSIBLE_NOW) {
                        entityhuman.a(new ChatMessage("block.minecraft.bed.no_sleep", new Object[0]), true);
                    } else if (entityhuman$enumbedresult == EntityHuman.EnumBedResult.NOT_SAFE) {
                        entityhuman.a(new ChatMessage("block.minecraft.bed.not_safe", new Object[0]), true);
                    } else if (entityhuman$enumbedresult == EntityHuman.EnumBedResult.TOO_FAR_AWAY) {
                        entityhuman.a(new ChatMessage("block.minecraft.bed.too_far_away", new Object[0]), true);
                    }

                    return true;
                }
            } else {
                world.setAir(blockposition);
                BlockPosition blockposition1 = blockposition.shift(((EnumDirection)iblockdata.get(FACING)).opposite());
                if (world.getType(blockposition1).getBlock() == this) {
                    world.setAir(blockposition1);
                }

                world.createExplosion((Entity)null, DamageSource.a(), (double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D, 5.0F, true, true);
                return true;
            }
        }
    }

    @Nullable
    private EntityHuman a(World world, BlockPosition blockposition) {
        for(EntityHuman entityhuman : world.players) {
            if (entityhuman.isSleeping() && entityhuman.bedPosition.equals(blockposition)) {
                return entityhuman;
            }
        }

        return null;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public void fallOn(World world, BlockPosition blockposition, Entity entity, float f) {
        super.fallOn(world, blockposition, entity, f * 0.5F);
    }

    public void a(IBlockAccess iblockaccess, Entity entity) {
        if (entity.isSneaking()) {
            super.a(iblockaccess, entity);
        } else if (entity.motY < 0.0D) {
            entity.motY = -entity.motY * (double)0.66F;
            if (!(entity instanceof EntityLiving)) {
                entity.motY *= 0.8D;
            }
        }

    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection == a((BlockPropertyBedPart)iblockdata.get(PART), (EnumDirection)iblockdata.get(FACING))) {
            return iblockdata1.getBlock() == this && iblockdata1.get(PART) != iblockdata.get(PART) ? (IBlockData)iblockdata.set(OCCUPIED, iblockdata1.get(OCCUPIED)) : Blocks.AIR.getBlockData();
        } else {
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }

    private static EnumDirection a(BlockPropertyBedPart blockpropertybedpart, EnumDirection enumdirection) {
        return blockpropertybedpart == BlockPropertyBedPart.FOOT ? enumdirection : enumdirection.opposite();
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData var4, @Nullable TileEntity tileentity, ItemStack itemstack) {
        super.a(world, entityhuman, blockposition, Blocks.AIR.getBlockData(), tileentity, itemstack);
    }

    public void remove(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
        if (iblockdata.getBlock() != iblockdata1.getBlock()) {
            super.remove(iblockdata, world, blockposition, iblockdata1, flag);
            world.n(blockposition);
        }
    }

    public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
        BlockPropertyBedPart blockpropertybedpart = (BlockPropertyBedPart)iblockdata.get(PART);
        boolean flag = blockpropertybedpart == BlockPropertyBedPart.HEAD;
        BlockPosition blockposition1 = blockposition.shift(a(blockpropertybedpart, (EnumDirection)iblockdata.get(FACING)));
        IBlockData iblockdata1 = world.getType(blockposition1);
        if (iblockdata1.getBlock() == this && iblockdata1.get(PART) != blockpropertybedpart) {
            world.setTypeAndData(blockposition1, Blocks.AIR.getBlockData(), 35);
            world.a(entityhuman, 2001, blockposition1, Block.getCombinedId(iblockdata1));
            if (!world.isClientSide && !entityhuman.u()) {
                if (flag) {
                    iblockdata.a(world, blockposition, 0);
                } else {
                    iblockdata1.a(world, blockposition1, 0);
                }
            }

            entityhuman.b(StatisticList.BLOCK_MINED.b(this));
        }

        super.a(world, blockposition, iblockdata, entityhuman);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        EnumDirection enumdirection = blockactioncontext.f();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        BlockPosition blockposition1 = blockposition.shift(enumdirection);
        return blockactioncontext.getWorld().getType(blockposition1).a(blockactioncontext) ? (IBlockData)this.getBlockData().set(FACING, enumdirection) : null;
    }

    public IMaterial getDropType(IBlockData iblockdata, World world, BlockPosition blockposition, int i) {
        return (IMaterial)(iblockdata.get(PART) == BlockPropertyBedPart.FOOT ? Items.AIR : super.getDropType(iblockdata, world, blockposition, i));
    }

    public VoxelShape a(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return c;
    }

    @Nullable
    public static BlockPosition a(IBlockAccess iblockaccess, BlockPosition blockposition, int i) {
        EnumDirection enumdirection = (EnumDirection)iblockaccess.getType(blockposition).get(FACING);
        int j = blockposition.getX();
        int k = blockposition.getY();
        int l = blockposition.getZ();

        for(int i1 = 0; i1 <= 1; ++i1) {
            int j1 = j - enumdirection.getAdjacentX() * i1 - 1;
            int k1 = l - enumdirection.getAdjacentZ() * i1 - 1;
            int l1 = j1 + 2;
            int i2 = k1 + 2;

            for(int j2 = j1; j2 <= l1; ++j2) {
                for(int k2 = k1; k2 <= i2; ++k2) {
                    BlockPosition blockposition1 = new BlockPosition(j2, k, k2);
                    if (a(iblockaccess, blockposition1)) {
                        if (i <= 0) {
                            return blockposition1;
                        }

                        --i;
                    }
                }
            }
        }

        return null;
    }

    protected static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockaccess.getType(blockposition.down()).q() && !iblockaccess.getType(blockposition).getMaterial().isBuildable() && !iblockaccess.getType(blockposition.up()).getMaterial().isBuildable();
    }

    public EnumPistonReaction getPushReaction(IBlockData var1) {
        return EnumPistonReaction.DESTROY;
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.ENTITYBLOCK_ANIMATED;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, PART, OCCUPIED);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityBed(this.color);
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
        super.postPlace(world, blockposition, iblockdata, entityliving, itemstack);
        if (!world.isClientSide) {
            BlockPosition blockposition1 = blockposition.shift((EnumDirection)iblockdata.get(FACING));
            world.setTypeAndData(blockposition1, (IBlockData)iblockdata.set(PART, BlockPropertyBedPart.HEAD), 3);
            world.update(blockposition, Blocks.AIR);
            iblockdata.a((GeneratorAccess)world, blockposition, 3);
        }

    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
