package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockFluids extends Block implements IFluidSource {
    public static final BlockStateInteger LEVEL = BlockProperties.ah;
    protected final FluidTypeFlowing b;
    private final List<Fluid> c;
    private final Map<IBlockData, VoxelShape> o = Maps.newIdentityHashMap();

    protected BlockFluids(FluidTypeFlowing fluidtypeflowing, Block.Info block$info) {
        super(block$info);
        this.b = fluidtypeflowing;
        this.c = Lists.newArrayList();
        this.c.add(fluidtypeflowing.a(false));

        for(int i = 1; i < 8; ++i) {
            this.c.add(fluidtypeflowing.a(8 - i, false));
        }

        this.c.add(fluidtypeflowing.a(8, true));
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(LEVEL, Integer.valueOf(0)));
    }

    public void b(IBlockData var1, World world, BlockPosition blockposition, Random random) {
        world.b(blockposition).b(world, blockposition, random);
    }

    public boolean a_(IBlockData var1, IBlockAccess var2, BlockPosition var3) {
        return false;
    }

    public boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, PathMode var4) {
        return !this.b.a(TagsFluid.LAVA);
    }

    public Fluid h(IBlockData iblockdata) {
        int i = iblockdata.get(LEVEL);
        return (Fluid)this.c.get(Math.min(i, 8));
    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean d(IBlockData var1) {
        return false;
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
        Fluid fluid = iblockaccess.b(blockposition.up());
        return fluid.c().a(this.b) ? VoxelShapes.b() : (VoxelShape)this.o.computeIfAbsent(iblockdata, (iblockdata1) -> {
            Fluid fluid1 = iblockdata1.s();
            return VoxelShapes.a(0.0D, 0.0D, 0.0D, 1.0D, (double)fluid1.f(), 1.0D);
        });
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.INVISIBLE;
    }

    public IMaterial getDropType(IBlockData var1, World var2, BlockPosition var3, int var4) {
        return Items.AIR;
    }

    public int a(IWorldReader iworldreader) {
        return this.b.a(iworldreader);
    }

    public void onPlace(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData var4) {
        if (this.a(world, blockposition, iblockdata)) {
            world.I().a(blockposition, iblockdata.s().c(), this.a(world));
        }

    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        if (iblockdata.s().d() || iblockdata1.s().d()) {
            generatoraccess.I().a(blockposition, iblockdata.s().c(), this.a(generatoraccess));
        }

        return super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public void doPhysics(IBlockData iblockdata, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (this.a(world, blockposition, iblockdata)) {
            world.I().a(blockposition, iblockdata.s().c(), this.a(world));
        }

    }

    public boolean a(World world, BlockPosition blockposition, IBlockData var3) {
        if (this.b.a(TagsFluid.LAVA)) {
            boolean flag = false;

            for(EnumDirection enumdirection : EnumDirection.values()) {
                if (enumdirection != EnumDirection.DOWN && world.b(blockposition.shift(enumdirection)).a(TagsFluid.WATER)) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                Fluid fluid = world.b(blockposition);
                if (fluid.d()) {
                    world.setTypeUpdate(blockposition, Blocks.OBSIDIAN.getBlockData());
                    this.fizz(world, blockposition);
                    return false;
                }

                if (fluid.f() >= 0.44444445F) {
                    world.setTypeUpdate(blockposition, Blocks.COBBLESTONE.getBlockData());
                    this.fizz(world, blockposition);
                    return false;
                }
            }
        }

        return true;
    }

    protected void fizz(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        double d0 = (double)blockposition.getX();
        double d1 = (double)blockposition.getY();
        double d2 = (double)blockposition.getZ();
        generatoraccess.a((EntityHuman)null, blockposition, SoundEffects.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (generatoraccess.m().nextFloat() - generatoraccess.m().nextFloat()) * 0.8F);

        for(int i = 0; i < 8; ++i) {
            generatoraccess.addParticle(Particles.F, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D);
        }

    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(LEVEL);
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public FluidType a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
        if (iblockdata.get(LEVEL) == 0) {
            generatoraccess.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 11);
            return this.b;
        } else {
            return FluidTypes.a;
        }
    }
}
