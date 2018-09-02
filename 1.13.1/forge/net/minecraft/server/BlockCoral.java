package net.minecraft.server;

import java.util.Random;
import javax.annotation.Nullable;

public class BlockCoral extends Block {
    private final Block a;

    public BlockCoral(Block block, Block.Info block$info) {
        super(block$info);
        this.a = block;
    }

    public void a(IBlockData var1, World world, BlockPosition blockposition, Random var4) {
        if (!this.a(world, blockposition)) {
            world.setTypeAndData(blockposition, this.a.getBlockData(), 2);
        }

    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (!this.a(generatoraccess, blockposition)) {
            generatoraccess.J().a(blockposition, this, 60 + generatoraccess.m().nextInt(40));
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    protected boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        for(EnumDirection enumdirection : EnumDirection.values()) {
            Fluid fluid = iblockaccess.b(blockposition.shift(enumdirection));
            if (fluid.a(TagsFluid.WATER)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        if (!this.a(blockactioncontext.getWorld(), blockactioncontext.getClickPosition())) {
            blockactioncontext.getWorld().J().a(blockactioncontext.getClickPosition(), this, 60 + blockactioncontext.getWorld().m().nextInt(40));
        }

        return this.getBlockData();
    }

    protected boolean X_() {
        return true;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return this.a;
    }
}
