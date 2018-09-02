package net.minecraft.server;

import java.util.List;

public class BlockPressurePlateBinary extends BlockPressurePlateAbstract {
    public static final BlockStateBoolean POWERED = BlockProperties.t;
    private final BlockPressurePlateBinary.EnumMobType p;

    protected BlockPressurePlateBinary(BlockPressurePlateBinary.EnumMobType blockpressureplatebinary$enummobtype, Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(POWERED, Boolean.valueOf(false)));
        this.p = blockpressureplatebinary$enummobtype;
    }

    protected int getPower(IBlockData iblockdata) {
        return iblockdata.get(POWERED) ? 15 : 0;
    }

    protected IBlockData a(IBlockData iblockdata, int i) {
        return (IBlockData)iblockdata.set(POWERED, Boolean.valueOf(i > 0));
    }

    protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        if (this.material == Material.WOOD) {
            generatoraccess.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
        } else {
            generatoraccess.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        }

    }

    protected void b(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        if (this.material == Material.WOOD) {
            generatoraccess.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.7F);
        } else {
            generatoraccess.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
        }

    }

    protected int b(World world, BlockPosition blockposition) {
        AxisAlignedBB axisalignedbb = c.a(blockposition);
        List list;
        switch(this.p) {
        case EVERYTHING:
            list = world.getEntities((Entity)null, axisalignedbb);
            break;
        case MOBS:
            list = world.a(EntityLiving.class, axisalignedbb);
            break;
        default:
            return 0;
        }

        if (!list.isEmpty()) {
            for(Entity entity : list) {
                if (!entity.isIgnoreBlockTrigger()) {
                    return 15;
                }
            }
        }

        return 0;
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(POWERED);
    }

    public static enum EnumMobType {
        EVERYTHING,
        MOBS;

        private EnumMobType() {
        }
    }
}
