package net.minecraft.server;

public class BlockConcretePowder extends BlockFalling {
    private final IBlockData a;

    public BlockConcretePowder(Block block, Block.Info block$info) {
        super(block$info);
        this.a = block.getBlockData();
    }

    public void a(World world, BlockPosition blockposition, IBlockData var3, IBlockData iblockdata) {
        if (x(iblockdata)) {
            world.setTypeAndData(blockposition, this.a, 3);
        }

    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        World world = blockactioncontext.getWorld();
        BlockPosition blockposition = blockactioncontext.getClickPosition();
        return !x(world.getType(blockposition)) && !a(world, blockposition) ? super.getPlacedState(blockactioncontext) : this.a;
    }

    private static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
        boolean flag = false;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(blockposition);

        for(EnumDirection enumdirection : EnumDirection.values()) {
            IBlockData iblockdata = iblockaccess.getType(blockposition$mutableblockposition);
            if (enumdirection != EnumDirection.DOWN || x(iblockdata)) {
                blockposition$mutableblockposition.g(blockposition).c(enumdirection);
                iblockdata = iblockaccess.getType(blockposition$mutableblockposition);
                if (x(iblockdata) && !Block.a(iblockdata.h(iblockaccess, blockposition), enumdirection.opposite())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    private static boolean x(IBlockData iblockdata) {
        return iblockdata.s().a(TagsFluid.WATER);
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        return a(generatoraccess, blockposition) ? this.a : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }
}
