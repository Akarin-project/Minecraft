package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockTurtleEgg extends Block {
    private static final VoxelShape c = Block.a(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    private static final VoxelShape o = Block.a(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
    public static final BlockStateInteger a = BlockProperties.ad;
    public static final BlockStateInteger b = BlockProperties.ac;

    public BlockTurtleEgg(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(a, Integer.valueOf(0))).set(b, Integer.valueOf(1)));
    }

    public void stepOn(World world, BlockPosition blockposition, Entity entity) {
        this.a(world, blockposition, entity, 100);
        super.stepOn(world, blockposition, entity);
    }

    public void fallOn(World world, BlockPosition blockposition, Entity entity, float f) {
        if (!(entity instanceof EntityZombie)) {
            this.a(world, blockposition, entity, 3);
        }

        super.fallOn(world, blockposition, entity, f);
    }

    private void a(World world, BlockPosition blockposition, Entity entity, int i) {
        if (!this.a(world, entity)) {
            super.stepOn(world, blockposition, entity);
        } else {
            if (!world.isClientSide && world.random.nextInt(i) == 0) {
                this.a(world, blockposition, world.getType(blockposition));
            }

        }
    }

    private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
        world.a((EntityHuman)null, blockposition, SoundEffects.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        int i = iblockdata.get(b);
        if (i <= 1) {
            world.setAir(blockposition, false);
        } else {
            world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(b, Integer.valueOf(i - 1)), 2);
            world.triggerEffect(2001, blockposition, Block.getCombinedId(iblockdata));
        }

    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random random) {
        if (this.a(world) && this.a(world, blockposition)) {
            int i = iblockdata.get(a);
            if (i < 2) {
                world.a((EntityHuman)null, blockposition, SoundEffects.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(a, Integer.valueOf(i + 1)), 2);
            } else {
                world.a((EntityHuman)null, blockposition, SoundEffects.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setAir(blockposition);
                if (!world.isClientSide) {
                    for(int j = 0; j < iblockdata.get(b); ++j) {
                        world.triggerEffect(2001, blockposition, Block.getCombinedId(iblockdata));
                        EntityTurtle entityturtle = new EntityTurtle(world);
                        entityturtle.setAgeRaw(-24000);
                        entityturtle.g(blockposition);
                        entityturtle.setPositionRotation((double)blockposition.getX() + 0.3D + (double)j * 0.2D, (double)blockposition.getY(), (double)blockposition.getZ() + 0.3D, 0.0F, 0.0F);
                        world.addEntity(entityturtle);
                    }
                }
            }
        }

    }

    private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        return iblockaccess.getType(blockposition.down()).getBlock() == Blocks.SAND;
    }

    public void onPlace(IBlockData var1, World world, BlockPosition blockposition, IBlockData var4) {
        if (this.a(world, blockposition) && !world.isClientSide) {
            world.triggerEffect(2005, blockposition, 0);
        }

    }

    private boolean a(World world) {
        float f = world.k(1.0F);
        if ((double)f < 0.69D && (double)f > 0.65D) {
            return true;
        } else {
            return world.random.nextInt(500) == 0;
        }
    }

    protected boolean X_() {
        return true;
    }

    public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack) {
        super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
        this.a(world, blockposition, iblockdata);
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
        return blockactioncontext.getItemStack().getItem() == this.getItem() && iblockdata.get(b) < 4 ? true : super.a(iblockdata, blockactioncontext);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        IBlockData iblockdata = blockactioncontext.getWorld().getType(blockactioncontext.getClickPosition());
        return iblockdata.getBlock() == this ? (IBlockData)iblockdata.set(b, Integer.valueOf(Math.min(4, iblockdata.get(b) + 1))) : super.getPlacedState(blockactioncontext);
    }

    public TextureType c() {
        return TextureType.CUTOUT;
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return iblockdata.get(b) > 1 ? o : c;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a, b);
    }

    private boolean a(World world, Entity entity) {
        if (entity instanceof EntityTurtle) {
            return false;
        } else {
            return entity instanceof EntityLiving && !(entity instanceof EntityHuman) ? world.getGameRules().getBoolean("mobGriefing") : true;
        }
    }
}
