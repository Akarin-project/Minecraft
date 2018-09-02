package net.minecraft.server;

import com.google.common.base.Predicates;

public class BlockEnderPortalFrame extends Block {
    public static final BlockStateDirection FACING = BlockFacingHorizontal.FACING;
    public static final BlockStateBoolean EYE = BlockProperties.g;
    protected static final VoxelShape c = Block.a(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    protected static final VoxelShape o = Block.a(4.0D, 13.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape p = VoxelShapes.a(c, o);
    private static ShapeDetector q;

    public BlockEnderPortalFrame(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(FACING, EnumDirection.NORTH)).set(EYE, Boolean.valueOf(false)));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        return iblockdata.get(EYE) ? p : c;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)((IBlockData)this.getBlockData().set(FACING, blockactioncontext.f().opposite())).set(EYE, Boolean.valueOf(false));
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData iblockdata, World var2, BlockPosition var3) {
        return iblockdata.get(EYE) ? 15 : 0;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(FACING, enumblockrotation.a((EnumDirection)iblockdata.get(FACING)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(FACING)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(FACING, EYE);
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public static ShapeDetector d() {
        if (q == null) {
            q = ShapeDetectorBuilder.a().a("?vvv?", ">???<", ">???<", ">???<", "?^^^?").a('?', ShapeDetectorBlock.a(BlockStatePredicate.a)).a('^', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.END_PORTAL_FRAME).a(EYE, Predicates.equalTo(true)).a(FACING, Predicates.equalTo(EnumDirection.SOUTH)))).a('>', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.END_PORTAL_FRAME).a(EYE, Predicates.equalTo(true)).a(FACING, Predicates.equalTo(EnumDirection.WEST)))).a('v', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.END_PORTAL_FRAME).a(EYE, Predicates.equalTo(true)).a(FACING, Predicates.equalTo(EnumDirection.NORTH)))).a('<', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.END_PORTAL_FRAME).a(EYE, Predicates.equalTo(true)).a(FACING, Predicates.equalTo(EnumDirection.EAST)))).b();
        }

        return q;
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection enumdirection) {
        return enumdirection == EnumDirection.DOWN ? EnumBlockFaceShape.SOLID : EnumBlockFaceShape.UNDEFINED;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return false;
    }
}
