package net.minecraft.server;

import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockAnvil extends BlockFalling {
    private static final Logger c = LogManager.getLogger();
    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    private static final VoxelShape o = Block.a(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    private static final VoxelShape p = Block.a(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    private static final VoxelShape q = Block.a(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    private static final VoxelShape r = Block.a(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape s = Block.a(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
    private static final VoxelShape t = Block.a(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
    private static final VoxelShape u = Block.a(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    private static final VoxelShape v = VoxelShapes.a(o, VoxelShapes.a(p, VoxelShapes.a(q, r)));
    private static final VoxelShape w = VoxelShapes.a(o, VoxelShapes.a(s, VoxelShapes.a(t, u)));

    public BlockAnvil(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH));
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(FACING, blockactioncontext.f().e());
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (!world.isClientSide) {
            entityhuman.openTileEntity(new BlockAnvil.TileEntityContainerAnvil(world, blockposition));
        }

        return true;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
        return enumdirection.k() == EnumDirection.EnumAxis.X ? v : w;
    }

    protected void a(EntityFallingBlock entityfallingblock) {
        entityfallingblock.a(true);
    }

    public void a(World world, BlockPosition blockposition, IBlockData var3, IBlockData var4) {
        world.triggerEffect(1031, blockposition, 0);
    }

    public void a(World world, BlockPosition blockposition) {
        world.triggerEffect(1029, blockposition, 0);
    }

    @Nullable
    public static IBlockData a_(IBlockData iblockdata) {
        Block block = iblockdata.getBlock();
        if (block == Blocks.ANVIL) {
            return (IBlockData)Blocks.CHIPPED_ANVIL.getBlockData().set(FACING, iblockdata.get(FACING));
        } else {
            return block == Blocks.CHIPPED_ANVIL ? (IBlockData)Blocks.DAMAGED_ANVIL.getBlockData().set(FACING, iblockdata.get(FACING)) : null;
        }
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING);
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }

    public static class TileEntityContainerAnvil implements ITileEntityContainer {
        private final World a;
        private final BlockPosition b;

        public TileEntityContainerAnvil(World world, BlockPosition blockposition) {
            this.a = world;
            this.b = blockposition;
        }

        public IChatBaseComponent getDisplayName() {
            return new ChatMessage(Blocks.ANVIL.m(), new Object[0]);
        }

        public boolean hasCustomName() {
            return false;
        }

        @Nullable
        public IChatBaseComponent getCustomName() {
            return null;
        }

        public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
            return new ContainerAnvil(playerinventory, this.a, this.b, entityhuman);
        }

        public String getContainerName() {
            return "minecraft:anvil";
        }
    }
}
