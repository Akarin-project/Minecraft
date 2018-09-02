package net.minecraft.server;

import com.google.common.cache.LoadingCache;
import java.util.Random;
import javax.annotation.Nullable;

public class BlockPortal extends Block {
    public static final BlockStateEnum<EnumDirection.EnumAxis> AXIS = BlockProperties.z;
    protected static final VoxelShape b = Block.a(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape c = Block.a(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public BlockPortal(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)(this.blockStateList.getBlockData()).set(AXIS, EnumDirection.EnumAxis.X));
    }

    public VoxelShape a(IBlockData iblockdata, IBlockAccess var2, BlockPosition var3) {
        switch((EnumDirection.EnumAxis)iblockdata.get(AXIS)) {
        case Z:
            return c;
        case X:
        default:
            return b;
        }
    }

    public void a(IBlockData var1, World world, BlockPosition blockposition, Random random) {
        if (world.worldProvider.o() && world.getGameRules().getBoolean("doMobSpawning") && random.nextInt(2000) < world.getDifficulty().a()) {
            int i = blockposition.getY();

            BlockPosition blockposition1;
            for(blockposition1 = blockposition; !world.getType(blockposition1).q() && blockposition1.getY() > 0; blockposition1 = blockposition1.down()) {
                ;
            }

            if (i > 0 && !world.getType(blockposition1.up()).isOccluding()) {
                Entity entity = EntityTypes.ZOMBIE_PIGMAN.a(world, (NBTTagCompound)null, (IChatBaseComponent)null, (EntityHuman)null, blockposition1.up(), false, false);
                if (entity != null) {
                    entity.portalCooldown = entity.aQ();
                }
            }
        }

    }

    public boolean a(IBlockData var1) {
        return false;
    }

    public boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        BlockPortal.Shape blockportal$shape = this.b(generatoraccess, blockposition);
        if (blockportal$shape != null) {
            blockportal$shape.createPortal();
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    public BlockPortal.Shape b(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        BlockPortal.Shape blockportal$shape = new BlockPortal.Shape(generatoraccess, blockposition, EnumDirection.EnumAxis.X);
        if (blockportal$shape.d() && blockportal$shape.e == 0) {
            return blockportal$shape;
        } else {
            BlockPortal.Shape blockportal$shape1 = new BlockPortal.Shape(generatoraccess, blockposition, EnumDirection.EnumAxis.Z);
            return blockportal$shape1.d() && blockportal$shape1.e == 0 ? blockportal$shape1 : null;
        }
    }

    public IBlockData updateState(IBlockData iblockdata, EnumDirection enumdirection, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1) {
        EnumDirection.EnumAxis enumdirection$enumaxis = enumdirection.k();
        EnumDirection.EnumAxis enumdirection$enumaxis1 = (EnumDirection.EnumAxis)iblockdata.get(AXIS);
        boolean flag = enumdirection$enumaxis1 != enumdirection$enumaxis && enumdirection$enumaxis.c();
        return !flag && iblockdata1.getBlock() != this && !(new BlockPortal.Shape(generatoraccess, blockposition, enumdirection$enumaxis1)).f() ? Blocks.AIR.getBlockData() : super.updateState(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public TextureType c() {
        return TextureType.TRANSLUCENT;
    }

    public void a(IBlockData var1, World var2, BlockPosition blockposition, Entity entity) {
        if (!entity.isPassenger() && !entity.isVehicle() && entity.bm()) {
            entity.e(blockposition);
        }

    }

    public ItemStack a(IBlockAccess var1, BlockPosition var2, IBlockData var3) {
        return ItemStack.a;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        switch(enumblockrotation) {
        case COUNTERCLOCKWISE_90:
        case CLOCKWISE_90:
            switch((EnumDirection.EnumAxis)iblockdata.get(AXIS)) {
            case Z:
                return (IBlockData)iblockdata.set(AXIS, EnumDirection.EnumAxis.X);
            case X:
                return (IBlockData)iblockdata.set(AXIS, EnumDirection.EnumAxis.Z);
            default:
                return iblockdata;
            }
        default:
            return iblockdata;
        }
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(AXIS);
    }

    public ShapeDetector.ShapeDetectorCollection c(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        EnumDirection.EnumAxis enumdirection$enumaxis = EnumDirection.EnumAxis.Z;
        BlockPortal.Shape blockportal$shape = new BlockPortal.Shape(generatoraccess, blockposition, EnumDirection.EnumAxis.X);
        LoadingCache loadingcache = ShapeDetector.a(generatoraccess, true);
        if (!blockportal$shape.d()) {
            enumdirection$enumaxis = EnumDirection.EnumAxis.X;
            blockportal$shape = new BlockPortal.Shape(generatoraccess, blockposition, EnumDirection.EnumAxis.Z);
        }

        if (!blockportal$shape.d()) {
            return new ShapeDetector.ShapeDetectorCollection(blockposition, EnumDirection.NORTH, EnumDirection.UP, loadingcache, 1, 1, 1);
        } else {
            int[] aint = new int[EnumDirection.EnumAxisDirection.values().length];
            EnumDirection enumdirection = blockportal$shape.c.f();
            BlockPosition blockposition1 = blockportal$shape.position.up(blockportal$shape.a() - 1);

            for(EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection : EnumDirection.EnumAxisDirection.values()) {
                ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = new ShapeDetector.ShapeDetectorCollection(enumdirection.c() == enumdirection$enumaxisdirection ? blockposition1 : blockposition1.shift(blockportal$shape.c, blockportal$shape.b() - 1), EnumDirection.a(enumdirection$enumaxisdirection, enumdirection$enumaxis), EnumDirection.UP, loadingcache, blockportal$shape.b(), blockportal$shape.a(), 1);

                for(int i = 0; i < blockportal$shape.b(); ++i) {
                    for(int j = 0; j < blockportal$shape.a(); ++j) {
                        ShapeDetectorBlock shapedetectorblock = shapedetector$shapedetectorcollection.a(i, j, 1);
                        if (!shapedetectorblock.a().isAir()) {
                            ++aint[enumdirection$enumaxisdirection.ordinal()];
                        }
                    }
                }
            }

            EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection1 = EnumDirection.EnumAxisDirection.POSITIVE;

            for(EnumDirection.EnumAxisDirection enumdirection$enumaxisdirection2 : EnumDirection.EnumAxisDirection.values()) {
                if (aint[enumdirection$enumaxisdirection2.ordinal()] < aint[enumdirection$enumaxisdirection1.ordinal()]) {
                    enumdirection$enumaxisdirection1 = enumdirection$enumaxisdirection2;
                }
            }

            return new ShapeDetector.ShapeDetectorCollection(enumdirection.c() == enumdirection$enumaxisdirection1 ? blockposition1 : blockposition1.shift(blockportal$shape.c, blockportal$shape.b() - 1), EnumDirection.a(enumdirection$enumaxisdirection1, enumdirection$enumaxis), EnumDirection.UP, loadingcache, blockportal$shape.b(), blockportal$shape.a(), 1);
        }
    }

    public EnumBlockFaceShape a(IBlockAccess var1, IBlockData var2, BlockPosition var3, EnumDirection var4) {
        return EnumBlockFaceShape.UNDEFINED;
    }

    public static class Shape {
        private final GeneratorAccess a;
        private final EnumDirection.EnumAxis b;
        private final EnumDirection c;
        private final EnumDirection d;
        private int e;
        private BlockPosition position;
        private int height;
        private int width;

        public Shape(GeneratorAccess generatoraccess, BlockPosition blockposition, EnumDirection.EnumAxis enumdirection$enumaxis) {
            this.a = generatoraccess;
            this.b = enumdirection$enumaxis;
            if (enumdirection$enumaxis == EnumDirection.EnumAxis.X) {
                this.d = EnumDirection.EAST;
                this.c = EnumDirection.WEST;
            } else {
                this.d = EnumDirection.NORTH;
                this.c = EnumDirection.SOUTH;
            }

            for(BlockPosition blockposition1 = blockposition; blockposition.getY() > blockposition1.getY() - 21 && blockposition.getY() > 0 && this.a(generatoraccess.getType(blockposition.down())); blockposition = blockposition.down()) {
                ;
            }

            int i = this.a(blockposition, this.d) - 1;
            if (i >= 0) {
                this.position = blockposition.shift(this.d, i);
                this.width = this.a(this.position, this.c);
                if (this.width < 2 || this.width > 21) {
                    this.position = null;
                    this.width = 0;
                }
            }

            if (this.position != null) {
                this.height = this.c();
            }

        }

        protected int a(BlockPosition blockposition, EnumDirection enumdirection) {
            int i;
            for(i = 0; i < 22; ++i) {
                BlockPosition blockposition1 = blockposition.shift(enumdirection, i);
                if (!this.a(this.a.getType(blockposition1)) || this.a.getType(blockposition1.down()).getBlock() != Blocks.OBSIDIAN) {
                    break;
                }
            }

            Block block = this.a.getType(blockposition.shift(enumdirection, i)).getBlock();
            return block == Blocks.OBSIDIAN ? i : 0;
        }

        public int a() {
            return this.height;
        }

        public int b() {
            return this.width;
        }

        protected int c() {
            label56:
            for(this.height = 0; this.height < 21; ++this.height) {
                for(int i = 0; i < this.width; ++i) {
                    BlockPosition blockposition = this.position.shift(this.c, i).up(this.height);
                    IBlockData iblockdata = this.a.getType(blockposition);
                    if (!this.a(iblockdata)) {
                        break label56;
                    }

                    Block block = iblockdata.getBlock();
                    if (block == Blocks.NETHER_PORTAL) {
                        ++this.e;
                    }

                    if (i == 0) {
                        block = this.a.getType(blockposition.shift(this.d)).getBlock();
                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    } else if (i == this.width - 1) {
                        block = this.a.getType(blockposition.shift(this.c)).getBlock();
                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    }
                }
            }

            for(int j = 0; j < this.width; ++j) {
                if (this.a.getType(this.position.shift(this.c, j).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
                    this.height = 0;
                    break;
                }
            }

            if (this.height <= 21 && this.height >= 3) {
                return this.height;
            } else {
                this.position = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        protected boolean a(IBlockData iblockdata) {
            Block block = iblockdata.getBlock();
            return iblockdata.isAir() || block == Blocks.FIRE || block == Blocks.NETHER_PORTAL;
        }

        public boolean d() {
            return this.position != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void createPortal() {
            for(int i = 0; i < this.width; ++i) {
                BlockPosition blockposition = this.position.shift(this.c, i);

                for(int j = 0; j < this.height; ++j) {
                    this.a.setTypeAndData(blockposition.up(j), (IBlockData)Blocks.NETHER_PORTAL.getBlockData().set(BlockPortal.AXIS, this.b), 18);
                }
            }

        }

        private boolean g() {
            return this.e >= this.width * this.height;
        }

        public boolean f() {
            return this.d() && this.g();
        }
    }
}
