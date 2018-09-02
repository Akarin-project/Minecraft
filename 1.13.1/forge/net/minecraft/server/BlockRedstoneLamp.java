package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockRedstoneLamp extends Block {
    public static final BlockStateBoolean a = BlockRedstoneTorch.LIT;

    public BlockRedstoneLamp(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)this.getBlockData().set(a, Boolean.valueOf(false)));
    }

    public int m(IBlockData iblockdata) {
        return iblockdata.get(a) ? super.m(iblockdata) : 0;
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1) {
        super.onPlace(iblockdata, world, blockposition, iblockdata1);
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(a, Boolean.valueOf(blockactioncontext.getWorld().isBlockIndirectlyPowered(blockactioncontext.getClickPosition())));
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (!world.isClientSide) {
            boolean flag = iblockdata.get(a);
            if (flag != world.isBlockIndirectlyPowered(blockposition)) {
                if (flag) {
                    world.J().a(blockposition, this, 4);
                } else {
                    world.setTypeAndData(blockposition, (IBlockData)iblockdata.a(a), 2);
                }
            }

        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!world.isClientSide) {
            if (iblockdata.get(a) && !world.isBlockIndirectlyPowered(blockposition)) {
                world.setTypeAndData(blockposition, (IBlockData)iblockdata.a(a), 2);
            }

        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a);
    }
}
