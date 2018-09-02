package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockStructure extends BlockTileEntity {
    public static final BlockStateEnum<BlockPropertyStructureMode> a = BlockProperties.aw;

    protected BlockStructure(Block.Info block$info) {
        super(block$info);
    }

    public TileEntity a(IBlockAccess var1) {
        return new TileEntityStructure();
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        return tileentity instanceof TileEntityStructure ? ((TileEntityStructure)tileentity).a(entityhuman) : false;
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, @Nullable EntityLiving entityliving, ItemStack var5) {
        if (!world.isClientSide) {
            if (entityliving != null) {
                TileEntity tileentity = world.getTileEntity(blockposition);
                if (tileentity instanceof TileEntityStructure) {
                    ((TileEntityStructure)tileentity).setAuthor(entityliving);
                }
            }

        }
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public IBlockData getPlacedState(BlockActionContext var1) {
        return (IBlockData)this.getBlockData().set(a, BlockPropertyStructureMode.DATA);
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }

    public void doPhysics(IBlockData var1, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (!world.isClientSide) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityStructure) {
                TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity;
                boolean flag = world.isBlockIndirectlyPowered(blockposition);
                boolean flag1 = tileentitystructure.E();
                if (flag && !flag1) {
                    tileentitystructure.d(true);
                    this.a(tileentitystructure);
                } else if (!flag && flag1) {
                    tileentitystructure.d(false);
                }

            }
        }
    }

    private void a(TileEntityStructure tileentitystructure) {
        switch(tileentitystructure.getUsageMode()) {
        case SAVE:
            tileentitystructure.b(false);
            break;
        case LOAD:
            tileentitystructure.c(false);
            break;
        case CORNER:
            tileentitystructure.s();
        case DATA:
        }

    }
}
