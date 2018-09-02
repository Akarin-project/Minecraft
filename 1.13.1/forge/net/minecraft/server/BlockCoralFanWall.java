package net.minecraft.server;

import java.util.Random;

public class BlockCoralFanWall extends BlockCoralFanWallAbstract {
    private final Block c;

    protected BlockCoralFanWall(Block block, Block.Info block$info) {
        super(block$info);
        this.c = block;
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData var4) {
        this.a(iblockdata, (GeneratorAccess)world, blockposition);
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!b_(iblockdata, world, blockposition)) {
            world.setTypeAndData(blockposition, (IBlockData)((IBlockData)this.c.getBlockData().set(b, Boolean.valueOf(false))).set(a, iblockdata.get(a)), 2);
        }

    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (enumdirection.opposite() == iblockdata.get(a) && !iblockdata.canPlace(generatoraccess, blockposition)) {
            return Blocks.AIR.getBlockData();
        } else {
            if (iblockdata.get(b)) {
                generatoraccess.I().a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
            }

            this.a(iblockdata, generatoraccess, blockposition);
            return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
        }
    }
}
